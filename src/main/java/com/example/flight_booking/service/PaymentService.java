package com.example.flight_booking.service;

import com.example.flight_booking.dto.Payment.PaymentCreateRequestDto;
import com.example.flight_booking.dto.Payment.PaymentFilterResponseDto;
import com.example.flight_booking.dto.Payment.PaymentUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Payment;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.enums.PaymentStatus;
import com.example.flight_booking.mapper.PaymentMapper;
import com.example.flight_booking.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentService {

  private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
  private final PaymentRepository paymentRepository;
  private final BookingService bookingService;
  private final PaymentMapper paymentMapper;

  public PaymentService(
      PaymentRepository paymentRepository,
      BookingService bookingService,
      PaymentMapper paymentMapper) {
    this.paymentRepository = paymentRepository;
    this.bookingService = bookingService;
    this.paymentMapper = paymentMapper;
  }

  private Payment getPaymentEntityById(Long id) {
    logger.debug("Ödeme aranıyor. paymentId={}", id);
    return paymentRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Ödeme bulunamadı. paymentId={}", id);
          return new ResponseStatusException(HttpStatus.NOT_FOUND,
              "Payment not found with id " + id);
        });
  }

  private Booking getBookingEntityById(Long id) {
    return bookingService.getBookingEntityById(id);
  }


  public PaymentFilterResponseDto getPaymentById(Long id) {
    Payment payment = getPaymentEntityById(id);
    return paymentMapper.toFilterResponseDto(payment);
  }

  public Payment createPayment(PaymentCreateRequestDto create) {
    logger.info("Ödeme oluşturuluyor. bookingId={}, status={}",
        create.getBookingId(), create.getStatus());

    Booking booking = getBookingEntityById(create.getBookingId());
    validateBookingAcceptsPayment(booking);
    Payment payment = paymentMapper.toEntity(create, booking);
    Payment savedPayment = paymentRepository.save(payment);

    logger.info("Ödeme oluşturuldu. paymentId={}, bookingId={}, status={}",
        savedPayment.getPaymentId(), booking.getBookingId(), savedPayment.getStatus());
    return savedPayment;
  }

  // iptal edilmiş rezervasyona ödeme alınmaz
  private void validateBookingAcceptsPayment(Booking booking) {
    if (booking.getStatus() == BookingStatus.CANCELLED) {
      logger.warn("İptal edilmiş rezervasyon için ödeme oluşturulamadı. bookingId={}",
          booking.getBookingId());
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Payment cannot be created for cancelled booking id " + booking.getBookingId());
    }
  }

  public Payment updatePayment(Long id, PaymentUpdateRequestDto update) {
    logger.info("Ödeme güncelleniyor. paymentId={}, status={}", id, update.getStatus());

    Payment payment = getPaymentEntityById(id);
    paymentMapper.updateEntity(payment, update);
    Payment savedPayment = paymentRepository.save(payment);

    logger.info("Ödeme güncellendi. paymentId={}, status={}",
        savedPayment.getPaymentId(), savedPayment.getStatus());
    return savedPayment;
  }

  public boolean hasCompletedPaymentForBooking(Long bookingId) {
    logger.debug("Tamamlanmış ödeme kontrol ediliyor. bookingId={}", bookingId);
    boolean hasCompletedPayment = paymentRepository.existsByBooking_BookingIdAndStatus(
        bookingId, PaymentStatus.COMPLETED);
    logger.debug("Tamamlanmış ödeme kontrolü tamamlandı. bookingId={}, exists={}",
        bookingId, hasCompletedPayment);
    return hasCompletedPayment;
  }

  public void deletePayment(Long id) {
    logger.info("Ödeme siliniyor. paymentId={}", id);

    paymentRepository.delete(getPaymentEntityById(id));

    logger.info("Ödeme silindi. paymentId={}", id);
  }

}
