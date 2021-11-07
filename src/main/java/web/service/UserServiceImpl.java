package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id).orElse(null);
    }

    @Override
    public Optional<User> getUserByUsername(String name) {
        return userDao.getUserByUsername(name);
    }

    @Transactional
    @Override
    public void createUser(User user) {
        Optional<User> userOptional = userDao
                .getUserByUsername(user.getUsername());
        if (!userOptional.isPresent()) {
                //>>>------------------------- Refactor it!
                Set<Role> roles = new HashSet<>();
                roles.add(new Role("ROLE_USER"));
                user.setRoles(roles);
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                //<<<-------------------------
                userDao.createUser(user);
        }
    }


    @Transactional
    @Override
    public void updateUser(Long userId, User user) {
        final Optional<User> userOptional1 = userDao.getUserById(userId);
        final Optional<User> userOptional2 = userDao.
                getUserByUsername(user.getUsername());
        if (userOptional1.isPresent()) {
            String username1 = userOptional1.get().getUsername();
            String username2 = user.getUsername();
            if (username1.equals(username2) || !userOptional2.isPresent()) {
                userDao.updateUser(userId, user);
            }
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        final Optional<User> userOptional = userDao.getUserById(userId);
        userOptional.ifPresent(userDao::deleteUser);
    }

    @Override
    public Set<String> getRolesFromUser(Long userId) {
        final Optional<User> userOptional = userDao.getUserById(userId);
        if (userOptional.isPresent()) {
            User u = userOptional.get();
            return AuthorityUtils.authorityListToSet(u.getAuthorities());
        }
        return null;
    }
}

