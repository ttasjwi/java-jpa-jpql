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
            Member member1 = new Member();
            member1.setName("ttasjwi");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("honux");
            member2.setAge(20);
            em.persist(member2);

            em.flush();
            em.clear();

            String jpql = "SELECT new jpql.MemberDTO(m.name, m.age) FROM Member as m";
            List<MemberDTO> memberDTOs = em.createQuery(jpql, MemberDTO.class)
                    .getResultList();
            for (MemberDTO memberDTO : memberDTOs) {
                System.out.println("memberDTO.name = " + memberDTO.getName());
                System.out.println("memberDTO.age = " + memberDTO.getAge());
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
