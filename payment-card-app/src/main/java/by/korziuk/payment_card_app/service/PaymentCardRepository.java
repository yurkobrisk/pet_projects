package by.korziuk.payment_card_app.service;

import by.korziuk.payment_card_app.domain.Currency;
import by.korziuk.payment_card_app.domain.PaymentCard;
import by.korziuk.payment_card_app.domain.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {

    List<PaymentCard> findByClientIdNotNullAndTypeAndCurrency(
            Type type, Currency currency, Pageable pageable);
}
