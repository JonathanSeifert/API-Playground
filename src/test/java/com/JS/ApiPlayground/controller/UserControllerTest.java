package com.JS.ApiPlayground.controller;

import com.JS.ApiPlayground.model.User;
import com.JS.ApiPlayground.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserRepository userRepository;

    User user1 = new User(0, "Tom Meier");
    User user2 = new User(1, "Max Mustermann");
    User user3 = new User(2, "Erika Mustermann");


    @Test
    public void getAllUsers_success() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3));

        Mockito.when(userRepository.findAll()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[0].name").value("Tom Meier"))
                .andExpect(jsonPath("[1].name").value("Max Mustermann"))
                .andExpect(jsonPath("[2].name").value("Erika Mustermann"));
    }

    @Test
    public void getAllUsers_isEmpty() throws Exception {
        MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        String response = mvc.getResponse().getContentAsString();
        String expected = "No users found.";

        assertEquals(expected, response);
    }

    @Test
    public void getUserById_success() throws Exception {
        Mockito.when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Tom Meier")));
    }

    @Test
    public void getUserById_notFound() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3));
        String id = "22";

        Mockito.when(userRepository.findAll()).thenReturn(users);
        MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvc.getResponse().getContentAsString();
        String expected = "User with id " + id + " does not exist.";

        assertEquals(expected, response);
    }

    @Test
    public void getUserById_badRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/ad")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUser_success() throws Exception {
        String name = "Manuel Meier";
        User user = new User(3, name);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void updateUserById_success() throws Exception {
        User user = new User("Tom Meier");

        Mockito.when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/user/" + user1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("Tom Meier"));
    }

    @Test
    public void updateUserById_null() throws Exception {
        User user = new User();

        Mockito.when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/user/" + user1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        MvcResult mvc = mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andReturn();


        String response = mvc.getResponse().getContentAsString();
        String expected = "User data cannot be null!";

        assertEquals(expected, response);
    }

    @Test
    public void updateUserById_idNotFound() throws Exception {
        long id = 400;
        User user = new User("Tom Meier");

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/user/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserById_success() throws Exception {
        Mockito.when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUserById_idNotFound() throws Exception {
        long id = 400;
        MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = mvc.getResponse().getContentAsString();
        String expected = "User with id " + id + " does not exist.";

        assertEquals(expected, response);
    }

    @Test void deleteAllUsers_success() throws Exception {
        MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/user"))
                .andExpect(status().isNoContent())
                .andReturn();

        String response = mvc.getResponse().getContentAsString();
        String expected = "Successfully deleted all users.";

        assertEquals(expected, response);
    }
}
