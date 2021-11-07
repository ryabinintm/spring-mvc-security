package web.dao;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    void createUser(User user);
    void updateUser(Long id, User user);
    void deleteUser(User user);
}
