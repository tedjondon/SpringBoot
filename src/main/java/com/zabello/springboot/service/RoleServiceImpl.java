package com.zabello.springboot.service;

import com.zabello.springboot.dao.RoleDao;
import com.zabello.springboot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    public Set<Role> arrayToSet(String [] roles) {
        Set<Role> result = new HashSet<>();
        for (String role : roles) {
            result.add(roleDao.getRoleByName(role));
        }
        return result;
    }

    @Transactional
    @Override
    public List<Role> listRoles() {
        return roleDao.listRoles();
    }
}
