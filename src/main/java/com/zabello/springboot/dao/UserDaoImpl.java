package com.zabello.springboot.dao;

import com.zabello.springboot.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(User user) {
        em.persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Query query = em.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserById(int id) {
        return em.find(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserByName(String name) {
        return em.createQuery(
                        "SELECT user FROM User user WHERE user.name =:username", User.class)
                .setParameter("username", name)
                .getSingleResult();
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        em.merge(updatedUser);
        em.flush();
    }

    @Override
    public void deleteUser(int id) {
        User user = getUserById(id);
        if (null == user) {
            throw new NullPointerException("User not found");
        }
        em.remove(user);
        em.flush();
    }
}
