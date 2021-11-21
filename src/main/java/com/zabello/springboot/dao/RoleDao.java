package com.zabello.springboot.dao;

import com.zabello.springboot.model.Role;
import java.util.List;

public interface RoleDao {
    void add(Role role);
    List<Role> listRoles();
    Role getRoleByName(String name);
}
