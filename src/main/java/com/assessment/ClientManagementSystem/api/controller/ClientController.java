package com.assessment.ClientManagementSystem.api.controller;

import com.assessment.ClientManagementSystem.api.controller.model.ClientCreateRequest;
import com.assessment.ClientManagementSystem.api.controller.model.ClientModel;
import com.assessment.ClientManagementSystem.api.exception.InvalidFieldException;
import com.assessment.ClientManagementSystem.api.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
