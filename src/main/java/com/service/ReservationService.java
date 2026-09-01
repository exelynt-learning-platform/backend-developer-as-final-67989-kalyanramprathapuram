package com.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dto.reservation.ReservationRequest;
import com.dto.reservation.ReservationResponse;
import com.entity.ReservationStatus;

public interface ReservationService {

    // CREATE RESERVATION
    ReservationResponse createReservation(
            ReservationRequest request,
            String username
    );

    // GET RESERVATIONS
    Page<ReservationResponse> getReservations(
            String username,
            boolean isAdmin,
            ReservationStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    // GET RESERVATION BY ID
    ReservationResponse getReservationById(
            Long id,
            String username,
            boolean isAdmin
    );

    // UPDATE RESERVATION
    ReservationResponse updateReservation(
            Long id,
            ReservationRequest request,
            String username,
            boolean isAdmin
    );

    // DELETE RESERVATION
    void deleteReservation(
            Long id,
            String username,
            boolean isAdmin
    );
}