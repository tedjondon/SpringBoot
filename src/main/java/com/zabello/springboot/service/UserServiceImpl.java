package com.zabello.springboot.service;

import com.zabello.springboot.dao.UserDao;
import com.zabello.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public boolean add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Transactional
    @Override
    public void updateUser(int id, User updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userDao.updateUser(id, updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
