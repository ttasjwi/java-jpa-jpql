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
            Member memberA = new Member();
            memberA.setName(null);
            memberA.setAge(20);
            em.persist(memberA);

            String memberNameA = em.createQuery("SELECT coalesce(m.name, '이름 없는 회원') from Member as m", String.class)
                    .getSingleResult();

            System.out.println("memberNameA = "+memberNameA);

            Member memberB = new Member();
            memberB.setName("관리자");
            memberB.setAge(70);
            em.persist(memberB);

            String memberNameB = em.createQuery("SELECT nullif(m.name, '관리자') from Member as m", String.class)
                    .getSingleResult();
            System.out.println("memberNameB = "+memberNameB);

            List<String> ageResults = em.createQuery(
                    "SELECT "+
                            "case " +
                                "when m.age <= 10 then '학생요금' "+
                                "when m.age >= 60 then '경로요금' "+
                                "else '일반요금' "+
                            "end "+
                        "FROM Member as m"
                    , String.class).getResultList();

            System.out.println("memberA age : "+ageResults.get(0));
            System.out.println("memberB age : "+ageResults.get(1));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
