package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //EntityManagerFactory는 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello2");
        //EntityManager는 쓰레드간에 공유X(사용하고 버리자!)
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setUsername("tazdev");
            member.setAge(10);
            em.persist(member);

            //query.getResultList();
            //결과가 하나 이상일 때, 리스트 반환(결과가 없으면 빈 리스트 반환)
            //query.getSingleResult();
            // 결과가 정확히 하나, 단일 객체 반환

            Member singleResult = em.createQuery("select m from Member as m where m.username = :username", Member.class)
                .setParameter("username", "tazdev")
                .getSingleResult();
            System.out.println("singleResult : " + singleResult.getUsername());

            tx.commit();
        } catch (Exception e){
            tx.rollback();

        } finally {
            em.close();
        }
        emf.close();
    }
}
