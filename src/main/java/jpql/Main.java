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

            for (int i = 1; i <= 100; i++) {
                Member member = new Member();
                member.setName("member" + i);
                member.setAge(i);
                em.persist(member);
            }
            List<Member> members = em.createQuery("SELECT m FROM Member as m ORDER BY m.age desc")
                    .setFirstResult(10)
                    .setMaxResults(10)
                    .getResultList();
            for (Member member : members) {
                System.out.println(member);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
