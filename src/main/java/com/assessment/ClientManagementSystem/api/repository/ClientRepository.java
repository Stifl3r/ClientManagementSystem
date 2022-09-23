package com.assessment.ClientManagementSystem.api.repository;

import com.assessment.ClientManagementSystem.api.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

  @Query("select c" +
         "  from Client c" +
         " where c.firstName like :key " +
         "    or c.mobileNumber like :key" +
         "    or c.idNumber like :key")
  List<Client> findByKey(@Param("key") String key);
}
