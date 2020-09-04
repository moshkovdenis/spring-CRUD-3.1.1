package ru.denis.dao;

import ru.denis.model.Role;
import ru.denis.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);

    List<User> allUsers();

    void updateUser(User user);

    void deleteUser(Long id);

    User findById(Long id);

    User findUserByName(String name);

    Role findRoleByName(String roleName);
}
