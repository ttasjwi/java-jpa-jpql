package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Book book = new Book();
            book.setName("자바 ORM 표준 JPA 프로그래밍");
            book.setAuthor("kim");
            book.setPrice(10000);
            book.setStockAmount(10000);
            book.setIsbn("9788960777330");
            em.persist(book);

            em.flush();
            em.clear();

            Product findProduct =
                    em.createQuery("SELECT p From Product as p " +
                                    "WHERE treat(p as Book).author = 'kim'", Product.class)
                    .getSingleResult();

            System.out.println("findProduct= "+findProduct);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
