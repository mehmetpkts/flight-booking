package com.example.flight_booking.service;

import com.example.flight_booking.dto.Ticket.TicketCreateRequestDto;
import com.example.flight_booking.dto.Ticket.TicketFilterResponseDto;
import com.example.flight_booking.dto.Ticket.TicketUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Ticket;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.mapper.TicketMapper;
import com.example.flight_booking.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

  private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
  private final TicketRepository ticketRepository;
  private final BookingService bookingService;
  private final PaymentService paymentService;
  private final TicketMapper ticketMapper;

  public TicketService(
      TicketRepository ticketRepository,
      BookingService bookingService,
      PaymentService paymentService,
      TicketMapper ticketMapper) {
    this.ticketRepository = ticketRepository;
    this.bookingService = bookingService;
    this.paymentService = paymentService;
    this.ticketMapper = ticketMapper;
  }

  private Ticket getTicketEntityById(Long id) {
    logger.debug("Bilet aranıyor. ticketId={}", id);
    return ticketRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Bilet bulunamadı. ticketId={}", id);
          return new ResponseStatusException(HttpStatus.NOT_FOUND,
              "Ticket not found with id " + id);
        });
  }

  private Booking getBookingEntityById(Long id) {
    return bookingService.getBookingEntityById(id);
  }

  private void validateBookingEligibilityForTicket(Booking booking) {
    if (booking.getStatus() == BookingStatus.CANCELLED) {
      logger.warn("İptal edilmiş rezervasyon için bilet oluşturulamadı. bookingId={}",
          booking.getBookingId());
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Ticket cannot be issued for cancelled booking id " + booking.getBookingId());
    }

    if (ticketRepository.existsByBooking_BookingId(booking.getBookingId())) {
      logger.warn("Rezervasyon için zaten bilet var. bookingId={}", booking.getBookingId());
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Ticket already issued for booking id " + booking.getBookingId());
    }

    if (!paymentService.hasCompletedPaymentForBooking(booking.getBookingId())) {
      logger.warn("Tamamlanmış ödemesi olmayan rezervasyon için bilet oluşturulamadı. bookingId={}",
          booking.getBookingId());
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Ticket cannot be issued before completed payment for booking id "
              + booking.getBookingId());
    }
  }


  public TicketFilterResponseDto getTicketById(Long id) {
    Ticket ticket = getTicketEntityById(id);
    return ticketMapper.toFilterResponseDto(ticket);
  }

  public Ticket createTicket(TicketCreateRequestDto create) {
    logger.info("Bilet oluşturuluyor. bookingId={}", create.getBookingId());

    Booking booking = getBookingEntityById(create.getBookingId());
    validateBookingEligibilityForTicket(booking);
    Ticket ticket = ticketMapper.toEntity(create, booking);
    Ticket savedTicket = ticketRepository.save(ticket);

    logger.info("Bilet oluşturuldu. ticketId={}, bookingId={}",
        savedTicket.getTicketId(), booking.getBookingId());
    return savedTicket;
  }

  public Ticket updateTicket(Long id, TicketUpdateRequestDto update) {
    logger.info("Bilet güncelleniyor. ticketId={}, bookingId={}", id, update.getBookingId());

    Ticket ticket = getTicketEntityById(id);
    ticketMapper.updateEntity(ticket, update);
    Ticket savedTicket = ticketRepository.save(ticket);

    logger.info("Bilet güncellendi. ticketId={}", savedTicket.getTicketId());
    return savedTicket;
  }

  public void deleteTicket(Long id) {
    logger.info("Bilet siliniyor. ticketId={}", id);

    ticketRepository.delete(getTicketEntityById(id));

    logger.info("Bilet silindi. ticketId={}", id);
  }

}
