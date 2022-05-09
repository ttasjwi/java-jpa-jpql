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

            em.flush();
            em.clear();

            String jpql = "SELECT t FROM Team as t";

            List<Team> teams = em.createQuery(jpql, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Team team : teams) {
                List<Member> members = team.getMembers();
                System.out.printf("team = %s | team.members.size = %d\n",team.getName(), members.size());
                for (Member member : members) {
                    System.out.printf("--------> member = %s\n",member.getName());
                }
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
