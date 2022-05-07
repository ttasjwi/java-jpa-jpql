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
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            String jpql = "SELECT m FROM Member as m WHERE m.name = :memberName";
            Member findMember = em.createQuery(jpql, Member.class)
                    .setParameter("memberName", member.getName())
                    .getSingleResult();

            System.out.println("findMember.getName() = " + findMember.getName());

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
