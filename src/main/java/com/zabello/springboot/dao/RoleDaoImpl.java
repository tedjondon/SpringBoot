package com.zabello.springboot.dao;

import com.zabello.springboot.model.Role;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Role role) {
        em.persist(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Role getRoleByName(String name) {
        return em.createQuery(
                        "SELECT role FROM Role role WHERE role.role =:roleName", Role.class)
                .setParameter("roleName", name)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> listRoles() {
        Query query = em.createQuery("from Role", Role.class);
        return query.getResultList();
    }
}
