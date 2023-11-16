package com.eshop.UserSphere.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String userId;

    @NotBlank
    @Column(unique = true)
    private String userName;

    @NotBlank
    @Size(min = 8)
    private String password;

    @Email
    @Column(unique = true)
    private String userEmail;

    @NotBlank
    private String fullName;

    private String address;

    @NotBlank
    @Column(unique = true,length = 10)
    private String userPhone;
}
