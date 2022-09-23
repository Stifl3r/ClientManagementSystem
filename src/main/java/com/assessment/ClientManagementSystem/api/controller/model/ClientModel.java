package com.assessment.ClientManagementSystem.api.controller.model;

import com.assessment.ClientManagementSystem.api.repository.entity.Client;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class ClientModel extends RepresentationModel<ClientModel> {
  private Integer clientId;
  private String firstName;
  private String lastName;
  private String idNumber;
  private String mobileNumber;
  private String physicalAddress;

  public ClientModel(final Client client) {
    this.clientId = client.getClientId();
    this.firstName = client.getFirstName();
    this.lastName = client.getLastName();
    this.idNumber = client.getIdNumber();
    this.mobileNumber = client.getMobileNumber();
    this.physicalAddress = client.getPhysicalAddress();
  }
}
