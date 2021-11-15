package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
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
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Transactional
    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createUser(user);
    }


    @Transactional
    @Override
    public void updateUser(User user) {

        userDao.updateUser(user);
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

