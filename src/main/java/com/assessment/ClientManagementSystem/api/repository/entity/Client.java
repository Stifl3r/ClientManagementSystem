package com.assessment.ClientManagementSystem.api.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer clientId;
  private String firstName;
  private String lastName;
  private String idNumber;
  private String mobileNumber;
  private String physicalAddress;
}
