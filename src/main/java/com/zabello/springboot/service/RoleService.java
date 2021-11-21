package com.zabello.springboot.service;

import com.zabello.springboot.model.Role;
import java.util.List;
import java.util.Set;

public interface RoleService {
    void add(Role role);
    List<Role> listRoles();
    Set<Role> arrayToSet(String [] roles);
}
