package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Role> getAllRoleS() {
		return entityManager.createQuery("SELECT r FROM Role r ORDER BY r.name", Role.class).getResultList();
	}

	@Override
	public void add(Role role) {
		entityManager.persist(role);
	}

	@Override
	public void updateRole(Role role) {
		entityManager.merge(role);
	}

	@Override
	public Role getRoleByName(String name) {
		return entityManager.createQuery("SELECT u FROM Role u WHERE u.name = :role",Role.class)
				.setParameter("role",name).getSingleResult();
	}

}
