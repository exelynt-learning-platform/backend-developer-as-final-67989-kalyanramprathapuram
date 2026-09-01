package com.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.reservation.ReservationRequest;
import com.dto.reservation.ReservationResponse;
import com.entity.ReservationStatus;
import com.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // =========================================================
    // CREATE RESERVATION
    // POST /api/reservations?username=kalyan
    // =========================================================

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestParam String username,
            @RequestBody ReservationRequest request) {

        ReservationResponse response =
                reservationService.createReservation(
                        request,
                        username
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // =========================================================
    // GET ALL RESERVATIONS
    //
    // USER:
    // GET /api/reservations?username=kalyan&isAdmin=false
    //
    // ADMIN:
    // GET /api/reservations?isAdmin=true
    //
    // FILTER:
    // status=PENDING
    // minPrice=100
    // maxPrice=1000
    // =========================================================

    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> getReservations(

            @RequestParam(required = false)
            String username,

            @RequestParam(defaultValue = "false")
            boolean isAdmin,

            @RequestParam(required = false)
            ReservationStatus status,

            @RequestParam(required = false)
            BigDecimal minPrice,

            @RequestParam(required = false)
            BigDecimal maxPrice,

            Pageable pageable) {

        Page<ReservationResponse> reservations =
                reservationService.getReservations(
                        username,
                        isAdmin,
                        status,
                        minPrice,
                        maxPrice,
                        pageable
                );

        return ResponseEntity.ok(reservations);
    }

    // =========================================================
    // GET RESERVATION BY ID
    //
    // GET /api/reservations/1?username=kalyan&isAdmin=false
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(

            @PathVariable Long id,

            @RequestParam String username,

            @RequestParam(defaultValue = "false")
            boolean isAdmin) {

        ReservationResponse response =
                reservationService.getReservationById(
                        id,
                        username,
                        isAdmin
                );

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // UPDATE RESERVATION
    //
    // PUT /api/reservations/1?username=kalyan&isAdmin=false
    //
    // IMPORTANT:
    // Service order:
    // updateReservation(
    //      id,
    //      request,
    //      username,
    //      isAdmin
    // )
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(

            @PathVariable Long id,

            @RequestParam String username,

            @RequestParam(defaultValue = "false")
            boolean isAdmin,

            @RequestBody ReservationRequest request) {

        ReservationResponse response =
                reservationService.updateReservation(
                        id,
                        request,
                        username,
                        isAdmin
                );

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // DELETE RESERVATION
    //
    // DELETE /api/reservations/1?username=kalyan&isAdmin=false
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(

            @PathVariable Long id,

            @RequestParam String username,

            @RequestParam(defaultValue = "false")
            boolean isAdmin) {

        reservationService.deleteReservation(
                id,
                username,
                isAdmin
        );

        return ResponseEntity.noContent().build();
    }
}