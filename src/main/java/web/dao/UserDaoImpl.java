package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.of(
                entityManager.find(User.class, id)
        );
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        String phql = "select u from User u where u.username = :username";
        return entityManager.createQuery(phql, User.class)
                .setParameter("username", username)
                .getResultList().stream().findAny();
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
        entityManager.flush();
    }
}