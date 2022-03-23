package org.Team1.technico.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true)
    private String vatNumber;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    @Column(unique=true)
    private String email;
    @Column(unique=true)
    private String username;
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Property> properties;

    public Owner(String vatNumber, String firstName, String lastName, String address, String phoneNumber, String email, String username, String password) {
        this.vatNumber = vatNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
