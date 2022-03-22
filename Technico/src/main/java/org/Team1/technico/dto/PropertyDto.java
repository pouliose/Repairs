package org.Team1.technico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Team1.technico.model.PropertyType;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PropertyDto {
    private int id;
    private String identityE9;
    private String address;
    private int constructionYear;
    private PropertyType propertyType;
}
