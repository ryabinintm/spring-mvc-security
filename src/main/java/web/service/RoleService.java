package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void add(Role role);

    void updateRole(Role role);

    Role getRoleByName(String name);

}
