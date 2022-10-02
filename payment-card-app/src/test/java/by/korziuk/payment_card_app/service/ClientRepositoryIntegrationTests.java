package by.korziuk.payment_card_app.service;

import by.korziuk.payment_card_app.PaymentCardApplication;
import by.korziuk.payment_card_app.domain.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PaymentCardApplication.class)
class ClientRepositoryIntegrationTests {

    @Autowired
    ClientRepository clientRepository;

    @Test
    void find_first_page_of_clients (){
        List<Client> clients = this.clientRepository.findAll();
        assertThat(clients.size(), is(greaterThan(2)));
        assertThat(clients.get(0).getEmail(), is("i.ivanov@gmail.com"));
    }

    @Test
    void should_return_two_clients_after_delete_first (){
        clientRepository.deleteById(1L);
        List<Client> clients = this.clientRepository.findAll();
        assertThat(clients.size(), is(2));
    }
}