package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao {

    List<Role> getAllRoleS();

    void add(Role role);

    void updateRole(Role role);

    Role getRoleByName(String name);

}
