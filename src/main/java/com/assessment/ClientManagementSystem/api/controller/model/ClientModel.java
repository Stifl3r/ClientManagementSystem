package com.assessment.ClientManagementSystem.api.controller.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientModel {
  private String firstName;
  private String lastName;
  private String idNumber;
  private String mobileNumber;
  private String physicalAddress;

//  public ClientModel(String firstName, String lastName, String idNumber, String mobileNumber, String physicalAddress) {
//    this.firstName = firstName;
//    this.lastName = lastName;
//    this.idNumber = idNumber;
//    this.mobileNumber = mobileNumber;
//    this.physicalAddress = physicalAddress;
//  }
}
