package by.korziuk.payment_card_app.rest;

import by.korziuk.payment_card_app.domain.Client;
import by.korziuk.payment_card_app.domain.Currency;
import by.korziuk.payment_card_app.domain.PaymentCard;
import by.korziuk.payment_card_app.domain.Type;
import by.korziuk.payment_card_app.service.ClientRepository;
import by.korziuk.payment_card_app.service.PaymentCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    ClientRepository clientRepository;
    @MockBean
    PaymentCardRepository paymentCardRepository;
    static Client client1;
    static Client client2;
    static Client client3;

    @BeforeAll
    static void setup() {
        client1 = new Client("Ivan Ivanov", "80291111111", "i.ivanov@gmail.com", "silver");
        client1.setId(1L);
        client2 = new Client("Petr Petrov", "80293333333", "p.petrov@gmail.com", "platinum");
        client2.setId(2L);
        client3 = new Client("Egor Egorov", "80297777777", "e.egorov@gmail.com", "new");
        client3.setId(3L);
    }

    @AfterAll
    static void cleanup() {
        client1 = null;
        client2 = null;
        client3 = null;
    }

    @Test
    void should_get_all_clients() throws Exception {
        //Given
        List<Client> records = new ArrayList<>(Arrays.asList(client1, client2, client3));
        //When
        Mockito.when(clientRepository.findAll()).thenReturn(records);
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].fio", is("Egor Egorov")));
    }

    @Test
    void should_return_client() throws Exception {
        //Given
        long id = 1L;
        //When
        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.of(client1));
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/clients/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.fio", is("Ivan Ivanov")))
                .andExpect(jsonPath("$.phoneNumber", is("80291111111")));
    }

    @Test
    void should_return_not_found_client() throws Exception {
        //Given
        long id = 1L;
        //When
        Mockito.when(clientRepository.findById(id)).thenReturn(Optional.empty());
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/clients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void should_add_client() throws Exception {
        //Given
        //When
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(client1));
        //Then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.fio", is("Ivan Ivanov")));
    }

    @Test
    void should_delete_client() throws Exception {
        //Given
        long id = 2L;
        //When
        Mockito.doNothing().when(clientRepository).deleteById(id);
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/clients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_clients_phones() throws Exception {
        //Given
        Type type = Type.GOLD;
        Currency currency = Currency.BYN;

        PaymentCard card1 = new PaymentCard();
        card1.setId(1L);
        card1.setClientId(client1.getId());
        card1.setCurrency(Currency.BYN);
        card1.setType(Type.GOLD);
        card1.setNumber("1234567890123456");

        PaymentCard card2 = new PaymentCard();
        card2.setId(2L);
        card2.setClientId(client2.getId());
        card2.setCurrency(Currency.BYN);
        card2.setType(Type.GOLD);
        card2.setNumber("7890123487654321");

        PaymentCard card3 = new PaymentCard();
        card3.setId(3L);
        card3.setClientId(client3.getId());
        card3.setCurrency(Currency.BYN);
        card3.setType(Type.GOLD);
        card3.setNumber("0011223455667799");

        List<PaymentCard> cards = new ArrayList<>(Arrays.asList(card1, card2, card3));
        String[] phones = new String[]{"80291111111", "80293333333", "80297777777"};
        //When
        Mockito.when(paymentCardRepository.findByClientIdNotNullAndTypeAndCurrency(
                type, currency, Pageable.ofSize(10)))
                .thenReturn(cards);

        Mockito.when(clientRepository.findById(client1.getId())).thenReturn(Optional.ofNullable(client1));
        Mockito.when(clientRepository.findById(client2.getId())).thenReturn(Optional.ofNullable(client2));
        Mockito.when(clientRepository.findById(client3.getId())).thenReturn(Optional.ofNullable(client3));
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/clients/phones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[1]", is("80293333333")))
                .andExpect(jsonPath("$", containsInAnyOrder(phones)));
    }
}