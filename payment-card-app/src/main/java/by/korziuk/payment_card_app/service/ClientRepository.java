package by.korziuk.payment_card_app.service;

import by.korziuk.payment_card_app.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
}
