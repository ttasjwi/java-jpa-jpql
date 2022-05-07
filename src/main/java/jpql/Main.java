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

            String jpql = "SELECT m FROM Member as m";
            TypedQuery<Member> query = em.createQuery(jpql, Member.class);
            List<Member> findMembers = query.getResultList();
            for (Member findMember : findMembers) {
                System.out.println("findMember = " + findMember.getName());
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
