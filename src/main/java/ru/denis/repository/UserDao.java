package ru.denis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denis.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findUserByName(String name);
}
