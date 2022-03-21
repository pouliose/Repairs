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

public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String identityE9;
    private String address;
    private int constructionYear;
    private PropertyType propertyType;
    private String ownerVatNumber;
    @JsonIgnore
    @ManyToOne
    private Owner owner;
    @OneToMany(mappedBy = "property")
    private List<Repair> repairs;

}
