package com.eshop.UserSphere.services;

import com.eshop.UserSphere.entities.User;
import com.eshop.UserSphere.exceptionHandler.BadCredentialsException;
import com.eshop.UserSphere.exceptionHandler.UnauthorizedAccessException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    /**
     * Registers a new user.
     *
     * @param user The user to be registered.
     * @return The registered user.
     */
    User registerUser(User user) throws BadCredentialsException;

    /**
     * Logs in a user using credentials.
     *
     * @param userName The username of the user.
     * @param password The password of the user.
     * @return The logged-in user.
     */
    User loginUser(String userName,String password) throws BadCredentialsException;

    /**
     * Retrieves a user by their user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the specified ID.
     */
    User getUserById(String userId);

    /**
     * Updates user information.
     *
     * @param user The user with updated information.
     * @return The updated user.
     */
    User updateUser(User user) throws UnauthorizedAccessException;

    /**
     * Deletes a user by their user ID.
     *
     * @param userId The ID of the user to delete.
     */
    User deleteUser(String userId);


}
