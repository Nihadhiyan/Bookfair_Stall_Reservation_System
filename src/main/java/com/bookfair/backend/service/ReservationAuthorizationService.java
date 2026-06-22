package com.bookfair.backend.service;

import org.springframework.stereotype.Service;

import com.bookfair.backend.model.Reservation;
import com.bookfair.backend.model.User;
import com.bookfair.backend.model.User.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationAuthorizationService {

    public boolean canViewReservation(User principal, Reservation reservation) {
        if (principal.getRole() == Role.SUPER_ADMIN) {
            return true;
        }

        if (principal.getRole() == Role.ORG_ADMIN || principal.getRole() == Role.ORG_EMPLOYEE) {
            // Can view if they own the reservation OR their organization organizes the
            // event
            boolean isOwner = reservation.getUser().getId().equals(principal.getId());
            boolean isEventOrganizer = principal.getOrganization() != null &&
                    principal.getOrganization().getId().equals(reservation.getEvent().getOrganizer().getId());

            return isOwner || isEventOrganizer;
        }

        // CUSTOMER
        return reservation.getUser().getId().equals(principal.getId());
    }

    public boolean canManageReservation(User principal, Reservation reservation) {
        if (principal.getRole() == Role.SUPER_ADMIN) {
            return true;
        }

        if (principal.getRole() == Role.ORG_ADMIN) {
            // Can cancel/manage if they own the reservation OR their organization organizes
            // the event
            boolean isOwner = reservation.getUser().getId().equals(principal.getId());
            boolean isEventOrganizer = principal.getOrganization() != null &&
                    principal.getOrganization().getId().equals(reservation.getEvent().getOrganizer().getId());

            return isOwner || isEventOrganizer;
        }

        if (principal.getRole() == Role.ORG_EMPLOYEE || principal.getRole() == Role.CUSTOMER) {
            // Only can manage their own reservations
            return reservation.getUser().getId().equals(principal.getId());
        }

        return false;
    }

    public boolean canConfirmReservation(User principal, Reservation reservation) {
        if (principal.getRole() == Role.SUPER_ADMIN) {
            return true;
        }

        if (principal.getRole() == Role.ORG_ADMIN || principal.getRole() == Role.ORG_EMPLOYEE) {
            // Only event organizers can confirm reservations (or we can restrict to
            // ORG_ADMIN only, but let's allow ORG_EMPLOYEE if it matches org)
            return principal.getOrganization() != null &&
                    principal.getOrganization().getId().equals(reservation.getEvent().getOrganizer().getId());
        }

        return false;
    }

    public boolean canApproveRefund(User principal, Reservation reservation) {
        if (principal.getRole() == Role.SUPER_ADMIN) {
            return true;
        }

        if (principal.getRole() == Role.ORG_ADMIN) {
            // Only ORG_ADMIN (and SUPER_ADMIN) can approve refunds for their events
            return principal.getOrganization() != null &&
                    principal.getOrganization().getId().equals(reservation.getEvent().getOrganizer().getId());
        }

        return false;
    }
}
