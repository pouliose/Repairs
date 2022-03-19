package org.Team1.technico.model;

import lombok.*;

import javax.persistence.*;
import java.time.Year;
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
    private int identityE9;
    private String address;
    private Year constructionYear;
    private PropertyType propertyType;
    @ManyToOne
    private Owner owner;
    @OneToMany(mappedBy = "property")
    private List<Repair> repairs;

}
