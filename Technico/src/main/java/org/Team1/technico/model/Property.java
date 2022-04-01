package org.Team1.technico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "Identity number is mandatory field.")
    private String identityE9;
    @NotBlank(message = "Address is mandatory field.")
    private String address;
    private int constructionYear;
    private PropertyType propertyType;
    @Size(min = 9, max = 9)
    private String ownerVatNumber;
    @JsonIgnore
    @ManyToOne
    private Owner owner;
    @JsonIgnore
    @OneToMany(mappedBy = "property")
    private List<Repair> repairs;

    public Property(String identityE9, String address, int constructionYear, PropertyType propertyType) {
        this.identityE9 = identityE9;
        this.address = address;
        this.constructionYear = constructionYear;
        this.propertyType = propertyType;
    }
}
