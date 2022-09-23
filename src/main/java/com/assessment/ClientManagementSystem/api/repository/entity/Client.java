package com.assessment.ClientManagementSystem.api.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer clientId;
  private String firstName;
  private String lastName;
  @Column(unique = true)
  private String idNumber;
  @Column(unique = true)
  private String mobileNumber;
  private String physicalAddress;
}
