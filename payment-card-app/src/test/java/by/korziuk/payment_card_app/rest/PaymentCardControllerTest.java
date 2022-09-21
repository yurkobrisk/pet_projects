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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentCardController.class)
class PaymentCardControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    PaymentCardRepository paymentCardRepository;
    @MockBean
    ClientRepository clientRepository;

    static PaymentCard paymentCard1;
    static PaymentCard paymentCard2;
    static PaymentCard paymentCard3;
    static Client client1;
    static Client updatedClient1;

    @BeforeAll
    static void setUp() {
        paymentCard1 = new PaymentCard();
        paymentCard1.setId(1L);
        paymentCard1.setClientId(1L);
        paymentCard1.setCurrency(Currency.PLZ);
        paymentCard1.setType(Type.CLASSIC);
        paymentCard1.setNumber("1234567890123456");

        paymentCard2 = new PaymentCard();
        paymentCard2.setId(2L);
        paymentCard2.setClientId(2L);
        paymentCard2.setCurrency(Currency.EUR);
        paymentCard2.setType(Type.BLACK);
        paymentCard2.setNumber("7890123487654321");

        paymentCard3 = new PaymentCard();
        paymentCard3.setId(3L);
        paymentCard3.setClientId(3L);
        paymentCard3.setCurrency(Currency.USD);
        paymentCard3.setType(Type.PLATINUM);
        paymentCard3.setNumber("0011223455667799");

        client1 = new Client("Sidor Sidorov", "80291212123", "s.sidorov@gmaol.com", "black");
        client1.setId(1L);
        updatedClient1 = new Client("Sidor Sidorov", "80291212123", "s.sidorov@gmaol.com", "classic");
        updatedClient1.setId(1L);
    }

    @AfterAll
    static void cleanUp() {
        paymentCard1 = null;
        paymentCard2 = null;
        paymentCard3 = null;
        client1 = null;
        updatedClient1 = null;
    }

    @Test
    void should_get_all_payment_cards() throws Exception {
        //Given
        List<PaymentCard> records = new ArrayList<>(Arrays.asList(paymentCard1, paymentCard2, paymentCard3));
        //When
        Mockito.when(paymentCardRepository.findAll()).thenReturn(records);
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].number", is("1234567890123456")))
                .andDo(print());
    }

    @Test
    void should_return_payment_card() throws Exception {
        //Given
        long id = 1L;
        //When
        Mockito.when(paymentCardRepository.findById(id)).thenReturn(Optional.of(paymentCard1));
        Mockito.when(paymentCardRepository.existsById(id)).thenReturn(true);
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cards/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.currency", is("PLZ")))
                .andExpect(jsonPath("$.number", is("1234567890123456")))
                .andDo(print());
    }

    @Test
    void should_return_not_found_payment_card() throws Exception {
        //Given
        long id = 1L;
        //When
        Mockito.when(paymentCardRepository.findById(id)).thenReturn(Optional.of(paymentCard1));
        Mockito.when(paymentCardRepository.existsById(id)).thenReturn(false);
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cards/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Payment Card is not found: id=" + id)))
                .andDo(print());
    }

    @Test
    void should_add_payment_card() throws Exception {
        //Given
        //When
        Mockito.when(paymentCardRepository.save(Mockito.any(PaymentCard.class))).thenReturn(paymentCard1);
        Mockito.when(clientRepository.existsById(paymentCard1.getClientId())).thenReturn(true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(paymentCard1));
        //Then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.number", is("1234567890123456")))
                .andDo(print());
    }

    @Test
    void should_return_client_not_found_when_add_payment_card() throws Exception {
        //Given
        //When
        Mockito.when(paymentCardRepository.save(Mockito.any(PaymentCard.class))).thenReturn(paymentCard1);
        Mockito.when(clientRepository.existsById(paymentCard1.getClientId())).thenReturn(false);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(paymentCard1));
        //Then
        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Client is not found: id=" + paymentCard1.getClientId())))
                .andDo(print());
    }

    @Test
    void should_delete_payment_card() throws Exception {
        //Given
        long id = paymentCard1.getId();
        //When
        Mockito.when(paymentCardRepository.existsById(id)).thenReturn(true);
        Mockito.when(paymentCardRepository.findById(id)).thenReturn(Optional.ofNullable(paymentCard1));
        Mockito.doNothing().when(paymentCardRepository).deleteById(id);
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cards/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}