package htwb.ai.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import htwb.ai.model.User;


public class DBUserDAO implements IUserDAO {

    // private static EMF emf;
    //private String persistenceUnit;
    EntityManagerFactory emf;
    EntityManager em;
    // public DBUserDAO(){}

//    public void setPersistenceUnit(String pUnit) {
//        System.out.println("I'm instanciated: " + pUnit);
//        
//        this.persistenceUnit = pUnit;
//    }

    public DBUserDAO(String pUnit) {
        this.emf = Persistence.createEntityManagerFactory(pUnit);
//        System.out.println("I am instantiated: " +pUnit);
    }
    //    public User getUserById(int id) {
//        if (id == 1) {
//            User fred = User.builder()
//                    .withId(1)
//                    .withFirstname("FRED")
//                    .withLastname("Schmidt")
//                    .withUserId("fschmidt")
//                    .withPassword("geheim").build();
//            return fred;
//        }
//        return null;
//    }
//
    public User getUserById(int id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(User.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public User getUserByUserId(String userId) {
        em = null;
        try {
            em = emf.createEntityManager();
            return em.find(User.class, userId);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public List<User> getAllUsers() {
        em = emf.createEntityManager();
        String strQuery = "SELECT u FROM User u WHERE u.id is NOT NULL";
        TypedQuery<User> tq = em.createQuery(strQuery, User.class);
        List<User> users;
        try {
            users = tq.getResultList();
            users.forEach(user -> System.out.println(user.toString()));
            return users;
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

//    public User getUserByUserId(String userId) {
//        if (userId != null && userId.equals("fschmidt")) {
//            return User.builder()
//                    .withId(1)
//                    .withFirstname("FRED")
//                    .withLastname("Schmidt")
//                    .withUserId("fschmidt")
//                    .withPassword("geheim").build();
//        }
//        return null;
//    }

//    public Integer addUser(User User) {
//        // TODO Auto-generated method stub
//        return null;
//    }

    public Integer save(User user) throws PersistenceException {
        EntityManager em;
        em = null;
        EntityTransaction transaction = null;

//        User sakhr =
//        User.builder().withId(1).withUserId("sakhr").withFirstname("sakhr").withLastname("nabil").withPassword("1234").build();
        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(user);
            // em.flush();
            transaction.commit();
            return user.getId();

        } catch (IllegalStateException | EntityExistsException | RollbackException ex) {
            System.out.println("#############################################");
            System.out.println("exception in tomcat log ->" + ex.getMessage());
            if (em != null) {
                em.getTransaction().rollback();
            }
            throw new PersistenceException(ex.getMessage());

        } finally {
            em.close();
            if (user != null)
                return user.getId();
            else return null;
        }
    }

    @Override
    public void updateUser(User User) {

    }

    /*public User updateUser(int id) {
        EntityManager em;
        em = null;
        EntityTransaction transaction = null;

        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            User foundUser = em.find(User.class, id);
            User editedUser = em.merge(foundUser);
            // em.flush();
            transaction.commit();
            return editedUser.getId();

        } catch (IllegalStateException | EntityExistsException | RollbackException ex) {
            System.out.println("#############################################");
            System.out.println("exception in tomcat log ->" + ex.getMessage());
            if (em != null) {
                em.getTransaction().rollback();
            }
            throw new PersistenceException(ex.getMessage());

        } finally {
            if (em != null)
                em.close();
        }
    }*/

    public void deleteUser(String userId) {
        // TODO Auto-generated method stub
    }


    public void closeEMF() {
        if (this.emf != null) {
            emf.close();
        }
    }
}