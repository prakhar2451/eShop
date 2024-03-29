package com.eshop.UserSphere.repositories;

import com.eshop.UserSphere.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByUserName(String userName);

    boolean existsByUserId(String userId);

    boolean existsByUserName(String userName);
}
