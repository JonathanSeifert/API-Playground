package com.JS.RESTPlayground.controller;

import com.JS.RESTPlayground.model.User;
import com.JS.RESTPlayground.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /*
     * 200 -> Users found.
     * 204 -> No users found.
     * 500 -> Internal Server error.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllUsers() {
        try {
            if (userRepository.findAll().isEmpty()) {
                return new ResponseEntity<>("No users found.", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 200 -> User found
     * 204 -> User with id does not exist
     * 500 -> Internal Server error
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable(value = "id") long id) {
        try {
            Optional<User> user = userRepository.findById(id);

            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User with id " + id + " does not exist.", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 201 -> User created
     * 500 -> internal Server error.
     */
    @PostMapping
    public ResponseEntity<?> saveUser(@Validated @RequestBody User user) {
        try {
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 200 -> User updated
     * 400 -> User with id __ does not exist
     * 500 -> internal server error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") long id, @RequestBody User user) {
        try {
            if (user.getName() == null) {
                return new ResponseEntity<>("User data cannot be null!", HttpStatus.BAD_REQUEST);
            }
            if (userRepository.findById(id).isEmpty()) {
                return new ResponseEntity<>("User with id " + id + " does not exist.", HttpStatus.BAD_REQUEST);
            } else {
                User _user = userRepository.findById(id).get();
                _user.setName(user.getName());
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 204 -> User with id deleted
     * 400 -> User with id does not exist
     * 500 -> internal server error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") long id) {
        try {
            if (userRepository.findById(id).isEmpty()) {
                return new ResponseEntity<>("User with id " + id + " does not exist.", HttpStatus.BAD_REQUEST);
            } else {
                userRepository.deleteById(id);
                return new ResponseEntity<>("Successfully deleted user with id " + id + ".", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * 204 -> all users deleted
     * 500 -> internal server error
     */
    @DeleteMapping
    public ResponseEntity<?> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>("Successfully deleted all users.", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}