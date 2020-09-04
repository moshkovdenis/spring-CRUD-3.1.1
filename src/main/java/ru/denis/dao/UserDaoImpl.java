package ru.denis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.denis.model.Role;
import ru.denis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> allUsers() {
        return (List<User>) entityManager.
                createQuery("SELECT u FROM User u ORDER BY u.id ASC")
                .getResultList();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findById(id);
        entityManager.remove(user);
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByName(String name) {
        List<User> users = allUsers();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User with name: " + name + "not found.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Role findRoleByName(String roleName) {
        List<Role> roleList = entityManager.createQuery("SELECT role from Role role").getResultList();
        for (Role role : roleList) {
            if (role.getRole().equals(roleName)) {
                return role;
            }
        }
        return null;
    }
}
