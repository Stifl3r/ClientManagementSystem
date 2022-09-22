package com.assessment.ClientManagementSystem.api.controller.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCreateRequest {
  private String firstName;
  private String lastName;
  private String idNumber;
  private String mobileNumber;
  private String physicalAddress;
}
