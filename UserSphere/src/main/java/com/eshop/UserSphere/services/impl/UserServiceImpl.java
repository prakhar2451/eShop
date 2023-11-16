package com.eshop.UserSphere.services.impl;

import com.eshop.UserSphere.entities.User;
import com.eshop.UserSphere.exceptionHandler.BadCredentialsException;
import com.eshop.UserSphere.exceptionHandler.ResourceNotFoundException;
import com.eshop.UserSphere.exceptionHandler.UnauthorizedAccessException;
import com.eshop.UserSphere.repositories.UserRepository;
import com.eshop.UserSphere.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Registers a new user.
     *
     * @param user The user to be registered.
     * @return The registered user.
     * @throws BadCredentialsException If username, password, or email is missing.
     */
    @Override
    public User registerUser(User user) throws BadCredentialsException {
        try {
            // Validate that required fields are present
            if (user.getUserName() == null || user.getPassword() == null || user.getUserEmail() == null) {
                throw new BadCredentialsException("Username, password,and email are required for registration.");
            }
            // Check if a user with the given username already exists
            if (userRepository.existsByUserName(user.getUserName())) {
                throw new RuntimeException("User with this username already exists.");
            }
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error during registration. ", e);
            throw e;
        }
    }

    /**
     * Logs in a user with the provided username and password.
     *
     * @param userName The username of the user.
     * @param password The password of the user.
     * @return The logged-in user.
     */
    @Override
    public User loginUser(String userName, String password) throws BadCredentialsException {
        // Validate that provided username and password are not null
        if(userName == null || password == null) {
            throw new IllegalArgumentException("Username and password are required for login.");
        }
        // Retrieve the user from the database based on the provided username
        User user = userRepository.findByUserName(userName);
        // Check if the user exists and if the provided password matches
        if(user != null && password.equals(user.getPassword())) {
            return user;
        } else {
            throw new BadCredentialsException("Invalid username or password.");
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The retrieved user.
     * @throws ResourceNotFoundException If no user is found with the given ID.
     */
    @Override
    public User getUserById(String userId) {
        // Validate that provided userId is not null
        if(userId == null) {
            throw new IllegalArgumentException("UserId is required.");
        }
        // Retrieve the user from the database based on the provided userId
        return userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    /**
     * Updates user information.
     *
     * @param user The user with updated information.
     * @return The updated user.
     * @throws UnauthorizedAccessException If the authenticated user is not authorized to update.
     */
    @PreAuthorize("#user.userId == authentication.principal.username")
    @Override
    public User updateUser(User user) throws UnauthorizedAccessException {
        // Validate that provided user is not null
        if(user == null) {
            throw new IllegalArgumentException("User object is required.");
        }
        // Validate that provided user has a non-null userId
        String userId = user.getUserId();
        if(userId == null) {
            throw new IllegalArgumentException("User ID is required for updating user information");
        }
        // Retrieve the authenticated user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new ResourceNotFoundException("User details not found in the security context.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Check if the authenticated user is the same as the user being updated
        if(!userId.equals(userDetails.getUsername())) {
            throw new UnauthorizedAccessException("You are not authorized to update this user's information.");
        }
        // Retrieve the existing user from the database
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID : " + userId));
        // Update user information
        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());
        existingUser.setUserEmail(user.getUserEmail());
        existingUser.setFullName(user.getFullName());
        existingUser.setAddress(user.getAddress());
        existingUser.setUserPhone(user.getUserPhone());
        return userRepository.save(existingUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     * @return The deleted user.
     * @throws ResourceNotFoundException If no user is found with the given ID.
     */
    @Override
    public User deleteUser(String userId) {
        // Check if the user exists
        if (!userRepository.existsByUserId(userId)) {
            throw new ResourceNotFoundException("User not found with Id: " + userId);
        }
        userRepository.deleteById(userId);
        return null;
    }
}
