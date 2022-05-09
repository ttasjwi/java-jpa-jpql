package jpql;

import javax.persistence.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("ttasjwi");
            member.setMemberType(MemberType.ADMIN);
            em.persist(member);

            Book book = new Book();
            book.setName("토끼책");
            book.setAuthor("조영호");
            book.setPrice(30000);
            em.persist(book);

            List<Member> members = em.createQuery("SELECT m FROM Member as m Where m.memberType = :memberType", Member.class)
                    .setParameter("memberType", MemberType.ADMIN)
                    .getResultList();
            System.out.println("members.size() = " + members.size());

            List<Product> products = em.createQuery("SELECT p from Product as p Where type(p) = Book", Product.class)
                    .getResultList();
            System.out.println("products.size() = "+ products.size());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
