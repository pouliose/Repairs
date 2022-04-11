package org.Team1.technico.service;

import org.Team1.technico.model.AppUser;
import org.Team1.technico.model.ERole;
import org.Team1.technico.model.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, ERole roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();

}
