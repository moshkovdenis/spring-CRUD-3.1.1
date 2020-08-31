package ru.denis.service;

import ru.denis.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> allUsers();

    void updateUser(User user);

    User findById(Long id);

    void deleteUser(Long id);
}
