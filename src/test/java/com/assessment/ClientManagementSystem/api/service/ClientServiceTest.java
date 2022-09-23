package com.assessment.ClientManagementSystem.api.service;

import com.assessment.ClientManagementSystem.api.controller.model.ClientCreateRequest;
import com.assessment.ClientManagementSystem.api.controller.model.ClientModel;
import com.assessment.ClientManagementSystem.api.exception.DatabaseException;
import com.assessment.ClientManagementSystem.api.exception.InvalidFieldException;
import com.assessment.ClientManagementSystem.api.exception.NotFoundException;
import com.assessment.ClientManagementSystem.api.repository.ClientRepository;
import com.assessment.ClientManagementSystem.api.repository.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import java.util.Optional;

import static java.util.List.of;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientService.class)
public class ClientServiceTest {

  @Autowired
  private ClientService clientService;

  @MockBean
  private ClientRepository clientRepository;

  @Test
  public void getAllClientsShouldReturnListOfClients() {
    //Given
    var client = getDefaultClient();
    var expected = of(new ClientModel(client));

    given(clientRepository.findAll())
        .willReturn(of(client));

    //When
    var actual = clientService.getAllClients();

    //Then
    assertEquals(expected.size(), actual.size());
    assertThat(actual.get(0))
        .usingRecursiveComparison()
        .isEqualTo(expected.get(0));

  }

  @Test
  public void getClientByIdWhenIdDoesNotExistShouldReturnNotFound() {

    //Given
    var client = getDefaultClient();

    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //when
    var thrown = catchThrowable(() -> {
      clientService.getClientById(2);
    });

    //Then
    assertThat(thrown).isInstanceOf(NotFoundException.class);
    assertEquals("Provided id does not exist", thrown.getMessage());
  }

  @Test
  public void getClientByIdWhenValidationFailsShouldReturnInvalidField() {

    //Given
    //when
    var thrown = catchThrowable(() -> {
      clientService.getClientById(null);
    });

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Id cannot be null", thrown.getMessage());
  }

  @Test
  public void createClientWhenFirstnameIsNullShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setFirstName(null);

    //When
    var thrown = catchThrowable(() -> clientService.createClient(request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Firstname cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void createClientWhenFirstnameIsEmptyShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setFirstName(" ");

    //When
    var thrown = catchThrowable(() -> clientService.createClient(request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Firstname cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void createClientWhenLastnameIsNullShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setLastName(null);

    //When
    var thrown = catchThrowable(() -> clientService.createClient(request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Lastname cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void createClientWhenLastnameIsEmptyShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setLastName(" ");

    //When
    var thrown = catchThrowable(() -> clientService.createClient(request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Lastname cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void createClientWhenIdNumberIsNullShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setIdNumber(null);

    //When
    var thrown = catchThrowable(() -> clientService.createClient(request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("ID Number cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void createClientWhenIdNumberIsEmptyShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setIdNumber(" ");

    //When
    var thrown = catchThrowable(() -> clientService.createClient(request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("ID Number cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void createClientWhenIdNumberIsNonNumericShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setIdNumber("111111111111A");

    //When
    var thrown = catchThrowable(() -> clientService.createClient(request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("ID Number cannot contain alpha numerics", thrown.getMessage());
  }

  @Test
  public void createClientWhenIdNumberLengthIsInvalidShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setIdNumber("111111111111");

    //When
    var thrown = catchThrowable(() -> clientService.createClient(request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("ID Number length should be 13", thrown.getMessage());
  }

  @Test
  public void editClientWhenFirstnameIsNullShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setFirstName(null);

    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() -> clientService.editClient(1, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Firstname cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void editClientWhenFirstnameIsEmptyShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setFirstName(" ");

    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() -> clientService.editClient(1, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Firstname cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void editClientWhenLastnameIsNullShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setLastName(null);

    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() -> clientService.editClient(1, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Lastname cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void editClientWhenLastnameIsEmptyShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setLastName(" ");

    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() -> clientService.editClient(1, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Lastname cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void editClientWhenIdNumberIsNullShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setIdNumber(null);

    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() -> clientService.editClient(1, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("ID Number cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void editClientWhenIdNumberIsEmptyShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setIdNumber(" ");

    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() -> clientService.editClient(1, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("ID Number cannot be null or empty", thrown.getMessage());
  }

  @Test
  public void editClientWhenIdNumberIsNonNumericShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setIdNumber("111111111111A");

    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() -> clientService.editClient(1, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("ID Number cannot contain alpha numerics", thrown.getMessage());
  }

  @Test
  public void editClientWhenIdNumberLengthIsInvalidShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();
    request.setIdNumber("111111111111");

    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() -> clientService.editClient(1, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("ID Number length should be 13", thrown.getMessage());
  }

  @Test
  public void editClientWhenIdIsNullShouldReturnInvalidField() {
    //Given
    var request = getDefaultClientCreateRequest();

    //When
    var thrown = catchThrowable(()-> clientService.editClient(null, request));

    //Then
    assertThat(thrown).isInstanceOf(InvalidFieldException.class);
    assertEquals("Id cannot be null", thrown.getMessage());
  }

  @Test
  public void editClientWhenIdDoesNotExistShouldReturnNotFound() {
    //Given
    var request = getDefaultClientCreateRequest();
    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));

    //When
    var thrown = catchThrowable(() ->
        clientService.editClient(999, request)
    );

    //Then
    assertThat(thrown).isInstanceOf(NotFoundException.class);
    assertEquals("Provided id does not exist", thrown.getMessage());
  }

  @Test
  public void editClientShouldReturnSuccess() throws NotFoundException, InvalidFieldException, DatabaseException {
    //Given
    var client = getDefaultClient();
    given(clientRepository.findById(client.getClientId()))
        .willReturn(Optional.of(client));
    var request = getDefaultClientCreateRequest();
    var expected = new ClientModel(client);

    //When
    var actual = clientService.editClient(client.getClientId(), request);

    //Then
    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringActualNullFields()
        .ignoringExpectedNullFields()
        .isEqualTo(expected);
  }

  private Client getDefaultClient() {
    var client = new Client();
    client.setClientId(1);
    client.setFirstName("John");
    client.setLastName("Snow");
    client.setMobileNumber("000000");
    client.setIdNumber("1111111111111");
    client.setPhysicalAddress("Home");
    return client;
  }

  private ClientCreateRequest getDefaultClientCreateRequest() {
    var client = new ClientCreateRequest();
    client.setFirstName("John");
    client.setLastName("Snow");
    client.setMobileNumber("000000");
    client.setIdNumber("1111111111111");
    client.setPhysicalAddress("Home");
    return client;
  }
}
