package com.eshop.UserSphere.services.impl;

import com.eshop.UserSphere.entities.User;
import com.eshop.UserSphere.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    /**
     * @param user The user to be registered.
     * @return
     */
    @Override
    public User registerUser(User user) {

        // Validate that required fields are present
        if (user.getUserName() == null || user.getPassword() == null || user.getUserEmail() ==null ) {
            throw new RuntimeException("User")
        }

        return null;
    }

    /**
     * @param userName The username of the user.
     * @param password The password of the user.
     * @return
     */
    @Override
    public User loginUser(String userName, String password) {
        return null;
    }

    /**
     * @param userId The ID of the user to retrieve.
     * @return
     */
    @Override
    public User getUserById(String userId) {
        return null;
    }

    /**
     * @param user The user with updated information.
     * @return
     */
    @Override
    public User updateUser(User user) {
        return null;
    }

    /**
     * @param userId The ID of the user to delete.
     * @return
     */
    @Override
    public User deleteUser(String userId) {
        return null;
    }
}
