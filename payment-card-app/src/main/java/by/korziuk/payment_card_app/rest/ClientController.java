package by.korziuk.payment_card_app.rest;

import by.korziuk.payment_card_app.domain.Client;
import by.korziuk.payment_card_app.domain.Currency;
import by.korziuk.payment_card_app.domain.PaymentCard;
import by.korziuk.payment_card_app.domain.Type;
import by.korziuk.payment_card_app.service.ClientRepository;
import by.korziuk.payment_card_app.service.PaymentCardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    private final ClientRepository clientRepository;
    private final PaymentCardRepository paymentCardRepository;

    public ClientController(ClientRepository clientRepository,
                            PaymentCardRepository paymentCardRepository) {
        this.clientRepository = clientRepository;
        this.paymentCardRepository = paymentCardRepository;
    }

    @GetMapping("/clients")
    public List<Client> allClients() {
        return clientRepository.findAll();
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> addClient(@Valid @RequestBody Client client) {
        Client addedClient;
        try {
            addedClient = clientRepository.save(client);
            return ResponseEntity.ok(addedClient);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        return optionalClient.map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Client not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/clients/phones")
    public List<String> getPhones(
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "0") Integer pageNum,
            @RequestParam(defaultValue = "GOLD") Type type,
            @RequestParam(defaultValue = "BYN") Currency currency
    ){
        return paymentCardRepository
                .findByClientIdNotNullAndTypeAndCurrency(
                        type,
                        currency,
                        PageRequest.of(pageNum, pageSize))
                .stream()
                .map(PaymentCard::getClientId)
                .map(id -> clientRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .map(Client::getPhoneNumber)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") long id) {
        try {
            clientRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
