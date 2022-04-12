package org.Team1.technico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Team1.technico.model.ERole;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private String username;
    private ERole roleName;
}
