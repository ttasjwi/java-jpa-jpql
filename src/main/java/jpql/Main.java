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
            memberB.changeTeam(teamB);
            em.persist(memberB);

            em.flush();
            em.clear();

            Member findMemberA = em.createQuery("SELECT m From Member as m WHERE m = :member", Member.class)
                    .setParameter("member", memberA)
                    .getSingleResult();
            System.out.println("findMemberA = "+findMemberA);

            Member findMemberB = em.createQuery("SELECT m From Member as m WHERE m.team = :team", Member.class)
                    .setParameter("team", teamB)
                    .getSingleResult();

            System.out.println("findMemberB = "+findMemberB);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
