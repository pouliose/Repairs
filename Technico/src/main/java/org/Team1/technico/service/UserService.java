package org.Team1.technico.service;

import org.Team1.technico.model.AppUser;
import org.Team1.technico.model.ERole;
import org.Team1.technico.model.Role;

import java.util.List;

public interface UserService {

    /**
     *
     * @param user
     * @return AppUser
     */
    AppUser saveUser(AppUser user);

    /**
     *
     * @param role
     * @return Role
     */
    Role saveRole(Role role);

    /**
     *
     * @param username
     * @param roleName
     */
    void addRoleToUser(String username, ERole roleName);

    /**
     *
     * @param username
     * @return AppUser
     */
    AppUser getUser(String username);

    /**
     *
     * @return List<AppUser>
     */
    List<AppUser> getUsers();
}
