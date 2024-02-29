package vladislavmaltsev.paymenttaskapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vladislavmaltsev.paymenttaskapi.dto.PaymentDTO;
import vladislavmaltsev.paymenttaskapi.entity.Payment;
import vladislavmaltsev.paymenttaskapi.repository.PaymentRepository;

import java.util.NoSuchElementException;

import static vladislavmaltsev.paymenttaskapi.util.MappingDTOClass.mapDTOAndClass;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    public PaymentDTO getPayment(String userName) {
        return mapDTOAndClass(
                paymentRepository.findLatestPaymentByUserName(userName)
                        .orElseThrow(() -> new NoSuchElementException(userName + " does not exists")),
                PaymentDTO.class)
                .orElseThrow();
    }

    public PaymentDTO savePayment(PaymentDTO paymentDTO) {
        return mapDTOAndClass(
                paymentRepository.save(
                mapDTOAndClass(
                        paymentDTO,
                        Payment.class).orElseThrow()
                ),
                PaymentDTO.class).orElseThrow();
    }

}
