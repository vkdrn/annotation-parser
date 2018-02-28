package bicycle.co.DAO;

import bicycle.co.model.Annotation;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class VersionDao {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public VersionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public void persist(Annotation annotation) {
        beginTransaction();
        entityManager.persist(annotation);
        commitTransaction();
    }

    private void beginTransaction() {
        try {
            entityTransaction.begin();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            rollbackTransaction();
        }
    }

    private void commitTransaction() {
        try {
            entityTransaction.commit();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    private void rollbackTransaction() {
        try {
            entityTransaction.rollback();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public List<Annotation> findAll() {
        beginTransaction();
        List<Annotation> listAnnotations = (List<Annotation>) entityManager.createQuery(
                "SELECT a FROM Annotation a").getResultList();
        commitTransaction();

        return listAnnotations;
    }
}
