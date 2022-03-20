package org.Team1.technico.model;


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
    private int id;
    @Column(unique=true)
    private int vatNumber;
    private String firstName;
    private String lastName;
    private String address;
    private int phoneNumber;
    @Column(unique=true)
    private String email;
    @Column(unique=true)
    private String username;
    private String password;
    @OneToMany(mappedBy = "owner")
    private List<Property> properties;
    @OneToMany(mappedBy = "owner")
    private List<Repair> repairs;
}
