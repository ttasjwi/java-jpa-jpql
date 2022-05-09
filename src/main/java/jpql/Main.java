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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member memberA = new Member();
            memberA.setName("memberA");
            memberA.changeTeam(team);
            em.persist(memberA);

            Member memberB = new Member();
            memberB.setName("memberB");
            memberB.changeTeam(team);
            em.persist(memberB);

            List<String> names = em.createQuery("SELECT m.name From Team as t JOIN t.members as m", String.class)
                    .getResultList();

            for (String name : names) {
                System.out.println("memberName = "+name);
            }

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
