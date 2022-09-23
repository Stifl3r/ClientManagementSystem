package com.assessment.ClientManagementSystem.api.controller;

import com.assessment.ClientManagementSystem.api.controller.model.ClientCreateRequest;
import com.assessment.ClientManagementSystem.api.controller.model.ClientModel;
import com.assessment.ClientManagementSystem.api.exception.InvalidFieldException;
import com.assessment.ClientManagementSystem.api.exception.NotFoundException;
import com.assessment.ClientManagementSystem.api.service.ClientService;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/clients")
public class ClientController {
  
  private final ClientService clientService;

  public ClientController(final ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  public ResponseEntity<ClientModel> createClient(@RequestBody final ClientCreateRequest request) throws InvalidFieldException {
    var result = clientService.createClient(request);
    return ResponseEntity
        .status(CREATED)
        .body(result);
  }

  @GetMapping()
  public List<ClientModel> getClients() {
    var clients = clientService.getAllClients();
    return addSelfLinks(clients);
  }

  @GetMapping("/{id}")
  public ClientModel getClientById(@PathVariable final Integer id) throws NotFoundException, InvalidFieldException {
    return clientService.getClientById(id);
  }

  @GetMapping("/search")
  public List<ClientModel> searchForClientsByKey(@RequestParam final String key) {
    var clients = clientService.searchForClientByKey(key);
    return addSelfLinks(clients);
  }

  private List<ClientModel> addSelfLinks(final List<ClientModel> clients) {
    for(ClientModel model: clients) {
      var clientId = model.getClientId().toString();
      var selfLink = linkTo(ClientController.class).slash(clientId).withSelfRel();
      model.add(selfLink);
    }
    return clients;
  }

}
