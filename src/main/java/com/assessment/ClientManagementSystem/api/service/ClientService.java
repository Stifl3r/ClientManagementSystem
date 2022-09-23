package com.assessment.ClientManagementSystem.api.service;

import com.assessment.ClientManagementSystem.api.controller.model.ClientCreateRequest;
import com.assessment.ClientManagementSystem.api.controller.model.ClientModel;
import com.assessment.ClientManagementSystem.api.exception.DatabaseException;
import com.assessment.ClientManagementSystem.api.exception.InvalidFieldException;
import com.assessment.ClientManagementSystem.api.exception.NotFoundException;
import com.assessment.ClientManagementSystem.api.repository.ClientRepository;
import com.assessment.ClientManagementSystem.api.repository.entity.Client;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(final ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public ClientModel createClient(final ClientCreateRequest request) throws InvalidFieldException, DatabaseException {
    validateClientCreateRequest(request);
    var client = new Client();
    client.setFirstName(request.getFirstName());
    client.setLastName(request.getLastName());
    client.setIdNumber(request.getIdNumber());
    client.setMobileNumber(request.getMobileNumber());
    client.setPhysicalAddress(request.getPhysicalAddress());

    try {
      clientRepository.save(client);
      return new ClientModel(client);
    } catch (DataIntegrityViolationException dive) {
      throw new InvalidFieldException("ID number or Mobile number that you have supplied already exists");
    } catch (Exception e) {
      throw new DatabaseException("Something went wrong! Please try again later");
    }
  }



  public List<ClientModel> getAllClients() {
    var results = clientRepository.findAll();
    return results.stream()
        .map(ClientModel::new)
        .collect(Collectors.toList());
  }

  public ClientModel getClientById(final Integer id) throws InvalidFieldException, NotFoundException {
    if (id == null) {
      throw new InvalidFieldException("Id cannot be null");
    }

    var client = clientRepository.findById(id)
        .orElseThrow(()-> new NotFoundException("Provided id does not exist"));

    return new ClientModel(client);
  }

  public List<ClientModel> searchForClientByKey(final String key) {
    var results = clientRepository.findByKey(key);
    return results.stream()
        .map(ClientModel::new)
        .collect(Collectors.toList());
  }

  public ClientModel editClient(final Integer id, final ClientCreateRequest request) throws InvalidFieldException, NotFoundException, DatabaseException {
    if (id == null) {
      throw new InvalidFieldException("Id cannot be null");
    }

    var client = clientRepository.findById(id)
        .orElseThrow(()-> new NotFoundException("Provided id does not exist"));

    validateClientCreateRequest(request);

    client.setFirstName(request.getFirstName());
    client.setLastName(request.getLastName());
    client.setIdNumber(request.getIdNumber());
    client.setMobileNumber(request.getMobileNumber());
    client.setPhysicalAddress(request.getPhysicalAddress());

    try {
      clientRepository.save(client);
      return new ClientModel(client);
    } catch (DataIntegrityViolationException dive) {
      throw new InvalidFieldException("ID number or Mobile number that you have supplied already exists");
    } catch (Exception e) {
      throw new DatabaseException("Something went wrong! Please try again later");
    }

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

    if (request.getMobileNumber() == null || request.getMobileNumber().isBlank()) {
      throw new InvalidFieldException("Mobile Number cannot be null or empty");
    }

    if (!request.getMobileNumber().matches("\\d+")) {
      throw new InvalidFieldException("Mobile Number cannot contain alpha numerics");
    }
  }
}
