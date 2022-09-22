package com.assessment.ClientManagementSystem.api.service;

import com.assessment.ClientManagementSystem.api.controller.model.ClientCreateRequest;
import com.assessment.ClientManagementSystem.api.controller.model.ClientModel;
import com.assessment.ClientManagementSystem.api.exception.InvalidFieldException;
import com.assessment.ClientManagementSystem.api.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(final ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public ClientModel createClient(final ClientCreateRequest request) throws InvalidFieldException {
    validateClientCreateRequest(request);
    

    return null;
  }

  private void validateClientCreateRequest(final ClientCreateRequest request) throws InvalidFieldException {
    if (request.getFirstName() == null || request.getFirstName().isBlank()) {
      throw new InvalidFieldException("Firstname cannot be null or empty");
    }

    if (request.getLastName() == null || request.getLastName().isBlank()) {
      throw new InvalidFieldException("Lastname cannot be null or empty");
    }

    if (request.getIdNumber() == null || request.getIdNumber().isBlank()) {
      throw new InvalidFieldException("ID Number cannot be null or empty");
    }

    if (!request.getIdNumber().matches("\\d+")) {
      throw new InvalidFieldException("ID Number cannot contain alpha numerics");
    }

    if (request.getIdNumber().length() != 13) {
      throw new InvalidFieldException("ID Number length should be 13");
    }
  }
}
