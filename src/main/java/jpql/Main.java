package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Product p1 = new Book();
            p1.setName("Book1");
            em.persist(p1);

            Product p2 = new Book();
            p2.setName("Book2");
            em.persist(p2);

            String result = em.createQuery("SELECT function('group_concat', p.name) FROM Product as p", String.class)
                    .getSingleResult();

            System.out.println("result = "+result);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
