package com.zabello.springboot.service;

import com.zabello.springboot.model.User;
import java.util.List;

public interface UserService {
    boolean add(User user);
    List<User> listUsers();
    User getUserById(int id);
    User getUserByName(String name);
    void updateUser(int id, User updatedUser);
    void deleteUser(int id);
}
