package web.service;

import web.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    Set<String> getRolesFromUser(Long id);
}
