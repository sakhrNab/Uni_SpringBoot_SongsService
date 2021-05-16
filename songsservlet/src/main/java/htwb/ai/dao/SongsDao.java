package htwb.ai.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import htwb.ai.model.Song;

public class SongsDao {
    EntityManagerFactory emf;

    public SongsDao(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public Integer save(Song song) throws PersistenceException {

        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(song);

            transaction.commit();

        } catch (IllegalStateException | EntityExistsException | RollbackException ex) {
            System.out.println("#############################################");
            System.out.println("exception in tomcat log ->" + ex.getMessage());
            if (em != null) {
                em.getTransaction().rollback();
            }
            throw new PersistenceException(ex.getMessage());
        } finally {
            em.close();
            return song.getId();
        }

    }

    public Song find(int id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(Song.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    @SuppressWarnings("unchecked")
    public List<Song> findAll() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.createQuery("select a from Songs a", Song.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
