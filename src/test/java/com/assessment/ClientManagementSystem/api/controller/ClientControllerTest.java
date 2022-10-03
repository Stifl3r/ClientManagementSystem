package com.assessment.ClientManagementSystem.api.controller;

import com.assessment.ClientManagementSystem.api.controller.model.ClientCreateRequest;
import com.assessment.ClientManagementSystem.api.controller.model.ClientModel;
import com.assessment.ClientManagementSystem.api.exception.InvalidFieldException;
import com.assessment.ClientManagementSystem.api.exception.NotFoundException;
import com.assessment.ClientManagementSystem.api.repository.entity.Client;
import com.assessment.ClientManagementSystem.api.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.List.of;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ClientService clientService;

  private static final String API_PATH = "/api/clients";

  @Test
  public void getListOfClientsShouldReturnClientList() throws Exception {
    var expected = getDefaultClient();

    given(clientService.getAllClients())
        .willReturn(of(new ClientModel(expected)));

    this.mockMvc.perform(get(API_PATH))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void getClientByIdShouldReturnClientModel() throws Exception {
    var expected = getDefaultClient();

    given(clientService.getClientById(1))
        .willReturn(new ClientModel(expected));

    var actual = this.mockMvc.perform(get(API_PATH + "/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    var actualResponseBody = actual.getResponse().getContentAsString();
    assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
        objectMapper.writeValueAsString(expected));
  }

  @Test
  public void getClientByIdWhenIdDoesNotExistShouldReturnNotFound() throws Exception {
    this.mockMvc = MockMvcBuilders
        .standaloneSetup()
        .setControllerAdvice(new GenericControllerAdvice())
        .build();

    given(clientService.getClientById(9999))
        .willThrow(new NotFoundException("Provided id does not exist"));

    var response = this.mockMvc.perform(get(API_PATH + "/9999")
            .accept(APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenFirstnameIsNullShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setFirstName(null);

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("Firstname cannot be null or empty"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenFirstnameIsEmptyShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setFirstName(" ");

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("Firstname cannot be null or empty"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenLastnameIsNullShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setLastName(null);

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("Lastname cannot be null or empty"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenLastnameIsEmptyShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setLastName(" ");

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("Lastname cannot be null or empty"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenIdNumberIsNullShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setIdNumber(null);

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("ID Number cannot be null or empty"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenIdNumberIsEmptyShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setIdNumber(" ");

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("ID Number cannot be null or empty"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenIdNumberContainsCharsShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setIdNumber("111111111111A");

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("ID Number cannot contain alpha numerics"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenIdNumberLengthIsNot13ShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setIdNumber("111111111111");

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("ID Number length should be 13"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenMobileNumberIsNullShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setMobileNumber(null);

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("Mobile Number cannot be null or empty"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenMobileNumberIsEmptyShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setMobileNumber(" ");

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("Mobile Number cannot be null or empty"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  public void createClientWhenMobileNumberContainsCharsShouldReturnBadRequest() throws Exception {
    var expected = getDefaultClientCreateRequest();
    expected.setMobileNumber("00000000A");

    given(clientService.createClient(expected))
        .willThrow(new InvalidFieldException("Mobile Number cannot contain alpha numerics"));

    var response = this.mockMvc.perform(post(API_PATH).accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getContentAsString()).isEmpty();
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
