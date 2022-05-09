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
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member memberA = new Member();
            memberA.setName("memberA");
            memberA.changeTeam(teamA);
            em.persist(memberA);

            Member memberB = new Member();
            memberB.setName("memberB");
            memberB.changeTeam(teamA);
            em.persist(memberB);

            Member memberC = new Member();
            memberC.setName("memberC");
            memberC.changeTeam(teamB);
            em.persist(memberC);

            Member memberD = new Member();
            memberD.setName("memberD");
            em.persist(memberD);

            String jpql = "SELECT m FROM Member as m join fetch m.team";

            List<Member> members = em.createQuery(jpql, Member.class).getResultList();
            for (Member member : members) {
                System.out.println("member.team.name = "+member.getTeam().getName());
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
