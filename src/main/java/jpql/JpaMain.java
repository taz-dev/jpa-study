package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select size(t.members) From Team t";

            List<Integer> result = em.createQuery(query, Integer.class)
                            .getResultList();

            for(Integer s : result){
                System.out.println("s = " + s);
            }

            tx.commit();
        } catch (Exception e){
            tx.rollback();

        } finally {
            em.close();
        }
        emf.close();
    }
}

//TypeQuery : 반환 타입이 명확할 때 사용
//Query : 반환 타입이 명확하지 않을 때 사용

//query.getResultList();
//결과가 하나 이상일 때, 리스트 반환(결과가 없으면 빈 리스트 반환)
//query.getSingleResult();
// 결과가 정확히 하나, 단일 객체 반환

//Member singleResult = em.createQuery("select m from Member as m where m.username = :username", Member.class)
//  .setParameter("username", "tazdev")
//  .getSingleResult();
//System.out.println("singleResult : " + singleResult.getUsername());

//프로젝션
//List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member as m", MemberDTO.class)
//                .getResultList();
//MemberDTO memberDTO = result.get(0);
//System.out.println("memberDTO = " + memberDTO.getUsername());
//System.out.println("memberDTO = " + memberDTO.getAge());

//페이징 API
//List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
//        .setFirstResult(0)
//        .setMaxResults(10)
//        .getResultList();

//조인
//String query = "select m from Member m join Team t on m.username = t.name";
//List<Member> result = em.createQuery(query, Member.class)
//        .getResultList();
//System.out.println("result: " + result);

//JPQL 타입 표현과 기타식
//String query = "select m.username, 'HELLO', true from Member m "
//        + "where m.type = :userType";
//List<Object[]> result = em.createQuery(query)
//        .setParameter("userType", MemberType.ADMIN)
//        .getResultList();

//조건식 - CASE 식
//String query =
//        "select " +
//                "case when m.age <= 10 then '학생요금' " +
//                "     when m.age >= 60 then '경로요금' " +
//                "     else '일반요금' end " +
//        "from Member m";
//List<String> result = em.createQuery(query, String.class).getResultList();

//경로 표현식
//상태 필드(state field) : 단순히 값을 저장하기 위한 필드 --> 경로 탐색의 끝, 탐색X
//연관 필드(association field) : 단일 값 연관 필드, 컬렉션 값 연관 필드
// --- 단일 값 연관 필드 : @ManyToOne, @OneToOne, 대상이 엔티티 --> 묵시적 내부 조인(inner join) 발생, 탐색O
// --- 컬렉션 값 연관 필드 : @OneToMany, @ManyToMany, 대상이 컬렉션 --> 묵시적 내부 조인(inner join) 발생, 탐색X

