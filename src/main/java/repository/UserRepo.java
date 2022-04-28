package repository;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserRepo {

    private static EntityManagerFactory emf;
    private static UserRepo instance;

    private UserRepo() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this repository.
     */
    public static UserRepo getUserRepo(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserRepo();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User registerUser(String username, String password) {
        EntityManager em = emf.createEntityManager();
        User user = new User(username, password);
        Role role = new Role("user");
        Role newRole = checkIfRoleExistThenCreateNew(role.getRoleName());

        user.addRole(newRole);
        try {
            User validate = em.find(User.class, username);
            if (validate != null) {
                throw new AuthenticationException("User already exists");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return user;
    }

    private boolean roleExists(Role role) {
        EntityManager em = emf.createEntityManager();
        Role validate;
        try {
            validate = em.find(Role.class, role.getRoleName());

        } finally {
            em.close();
        }
        return validate != null;
    }

    private Role checkIfRoleExistThenCreateNew(String roleName) {
        EntityManager em = emf.createEntityManager();
        Role checkRole = new Role(roleName);

        if (roleExists(checkRole)) {
            return em.find(Role.class, checkRole.getRoleName());
        }

        try {
            em.getTransaction().begin();
            em.persist(checkRole);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return checkRole;
    }
}
