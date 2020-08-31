package ru.denis.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.denis.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
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
}
