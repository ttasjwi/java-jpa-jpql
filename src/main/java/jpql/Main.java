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
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Member memberA = new Member();
            memberA.setName("teamA");
            memberA.changeTeam(teamA);
            em.persist(memberA);
            
            String jpql = "SELECT m FROM Member as m LEFT JOIN Team as t ON m.name = t.name";
            List<Member> members = em.createQuery(jpql, Member.class).getResultList();
            System.out.println("members.size() = " + members.size());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
