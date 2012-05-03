package org.ad13.denarius.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ad13.denarius.model.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserRepositoryImpl implements UserRepository {
    private EntityManager entityManager;

    public User findByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        cq.where(cb.equal(user.get("username"), username));

        // Get a list of results, if there's no entry, return null, otherwise
        // return the first entry
        List<User> results = entityManager.createQuery(cq).getResultList();
        if (results.size() == 0)
            return null;
        return results.get(0);
    }

    public Long createUser(String username, String password, String email, String fullname) {
        User user = new User(null, username, fullname, email, password);

        return entityManager.merge(user).getUserId();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
