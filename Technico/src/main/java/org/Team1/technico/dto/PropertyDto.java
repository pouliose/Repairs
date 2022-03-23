package org.Team1.technico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.Team1.technico.model.PropertyType;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PropertyDto {
    @NonNull
    private int id;
    @NonNull
    private String identityE9;
    private String address;
    @NonNull
    private int constructionYear;
    private PropertyType propertyType;
    private List<PropertyDto> properties;

    public PropertyDto addList() {
        properties = new ArrayList<>();
        return this;
    }
}
