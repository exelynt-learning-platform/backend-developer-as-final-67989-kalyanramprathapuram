package com.serviceImpl;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dto.reservation.ReservationRequest;
import com.dto.reservation.ReservationResponse;
import com.entity.Reservation;
import com.entity.ReservationStatus;
import com.entity.Resource;
import com.entity.User;
import com.exception.ReservationNotFoundException;
import com.exception.UnauthorizedException;
import com.repository.ReservationRepository;
import com.repository.ResourceRepository;
import com.repository.UserRepository;
import com.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            ResourceRepository resourceRepository) {

        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ReservationResponse createReservation(
            ReservationRequest request,
            String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UnauthorizedException(
                                "User not found"
                        )
                );

        Resource resource = resourceRepository.findById(
                request.getResourceId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Resource not found with id: "
                                + request.getResourceId()
                )
        );

        if (!resource.isAvailable()) {
            throw new RuntimeException(
                    "Resource is currently unavailable"
            );
        }

        if (request.getStartTime()
                .isAfter(request.getEndTime())
                ||
                request.getStartTime()
                        .isEqual(request.getEndTime())) {

            throw new IllegalArgumentException(
                    "Start time must be before end time"
            );
        }

        if (request.getPrice() == null
                || request.getPrice()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Price must be zero or greater"
            );
        }

        Reservation reservation = new Reservation();

        reservation.setUser(user);
        reservation.setResource(resource);
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());
        reservation.setPrice(request.getPrice());

        // USER-created reservations start as PENDING
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation savedReservation =
                reservationRepository.save(reservation);

        return convertToResponse(savedReservation);
    }

    @Override
    public Page<ReservationResponse> getReservations(
            String username,
            boolean isAdmin,
            ReservationStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable) {

        Page<Reservation> reservations;

        if (isAdmin) {

            reservations = findForAdmin(
                    status,
                    minPrice,
                    maxPrice,
                    pageable
            );

        } else {

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() ->
                            new UnauthorizedException(
                                    "User not found"
                            )
                    );

            reservations = findForUser(
                    user.getId(),
                    status,
                    minPrice,
                    maxPrice,
                    pageable
            );
        }

        return reservations.map(this::convertToResponse);
    }

    @Override
    public ReservationResponse getReservationById(
            Long id,
            String username,
            boolean isAdmin) {

        Reservation reservation =
                reservationRepository.findById(id)
                        .orElseThrow(() ->
                                new ReservationNotFoundException(
                                        "Reservation not found with id: "
                                                + id
                                )
                        );

        if (!isAdmin &&
                !reservation.getUser()
                        .getUsername()
                        .equals(username)) {

            throw new UnauthorizedException(
                    "You are not authorized to access this reservation"
            );
        }

        return convertToResponse(reservation);
    }

    @Override
    public ReservationResponse updateReservation(
            Long id,
            ReservationRequest request,
            String username,
            boolean isAdmin) {

        Reservation reservation =
                reservationRepository.findById(id)
                        .orElseThrow(() ->
                                new ReservationNotFoundException(
                                        "Reservation not found with id: "
                                                + id
                                )
                        );

        if (!isAdmin &&
                !reservation.getUser()
                        .getUsername()
                        .equals(username)) {

            throw new UnauthorizedException(
                    "You are not authorized to update this reservation"
            );
        }

        if (request.getStartTime()
                .isAfter(request.getEndTime())
                ||
                request.getStartTime()
                        .isEqual(request.getEndTime())) {

            throw new IllegalArgumentException(
                    "Start time must be before end time"
            );
        }

        if (request.getPrice() == null
                || request.getPrice()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Price must be zero or greater"
            );
        }

        if (request.getResourceId() != null) {

            Resource resource =
                    resourceRepository.findById(
                            request.getResourceId()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Resource not found"
                            )
                    );

            reservation.setResource(resource);
        }

        reservation.setStartTime(
                request.getStartTime()
        );

        reservation.setEndTime(
                request.getEndTime()
        );

        reservation.setPrice(
                request.getPrice()
        );

        /*
         * Only ADMIN should normally change reservation status.
         */
        if (isAdmin && request.getStatus() != null) {

            reservation.setStatus(
                    request.getStatus()
            );
        }

        Reservation updatedReservation =
                reservationRepository.save(reservation);

        return convertToResponse(updatedReservation);
    }

    @Override
    public void deleteReservation(
            Long id,
            String username,
            boolean isAdmin) {

        Reservation reservation =
                reservationRepository.findById(id)
                        .orElseThrow(() ->
                                new ReservationNotFoundException(
                                        "Reservation not found with id: "
                                                + id
                                )
                        );

        if (!isAdmin &&
                !reservation.getUser()
                        .getUsername()
                        .equals(username)) {

            throw new UnauthorizedException(
                    "You are not authorized to delete this reservation"
            );
        }

        reservationRepository.delete(reservation);
    }

    // ============================
    // ADMIN FILTERING
    // ============================

    private Page<Reservation> findForAdmin(
            ReservationStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable) {

        if (status != null
                && minPrice != null
                && maxPrice != null) {

            return reservationRepository
                    .findByStatusAndPriceBetween(
                            status,
                            minPrice,
                            maxPrice,
                            pageable
                    );
        }

        if (status != null && minPrice != null) {

            return reservationRepository
                    .findByStatusAndPriceGreaterThanEqual(
                            status,
                            minPrice,
                            pageable
                    );
        }

        if (status != null && maxPrice != null) {

            return reservationRepository
                    .findByStatusAndPriceLessThanEqual(
                            status,
                            maxPrice,
                            pageable
                    );
        }

        if (status != null) {

            return reservationRepository
                    .findByStatus(
                            status,
                            pageable
                    );
        }

        if (minPrice != null && maxPrice != null) {

            return reservationRepository
                    .findByPriceBetween(
                            minPrice,
                            maxPrice,
                            pageable
                    );
        }

        if (minPrice != null) {

            return reservationRepository
                    .findByPriceGreaterThanEqual(
                            minPrice,
                            pageable
                    );
        }

        if (maxPrice != null) {

            return reservationRepository
                    .findByPriceLessThanEqual(
                            maxPrice,
                            pageable
                    );
        }

        return reservationRepository.findAll(pageable);
    }

    // ============================
    // USER FILTERING
    // ============================

    private Page<Reservation> findForUser(
            Long userId,
            ReservationStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable) {

        /*
         * For now ownership filtering is performed first.
         * Additional combined repository queries can be added
         * later if needed for efficient database-side filtering.
         */

        Page<Reservation> result =
                reservationRepository.findByUserId(
                        userId,
                        pageable
                );

        return result;
    }

    // ============================
    // ENTITY -> RESPONSE
    // ============================

    private ReservationResponse convertToResponse(
            Reservation reservation) {

        ReservationResponse response =
                new ReservationResponse();

        response.setId(reservation.getId());

        response.setUsername(
                reservation.getUser()
                        .getUsername()
        );

        response.setResourceId(
                reservation.getResource()
                        .getId()
        );

        response.setResourceName(
                reservation.getResource()
                        .getName()
        );

        response.setStartTime(
                reservation.getStartTime()
        );

        response.setEndTime(
                reservation.getEndTime()
        );

        response.setPrice(
                reservation.getPrice()
        );

        response.setStatus(
                reservation.getStatus()
        );

        return response;
    }
}