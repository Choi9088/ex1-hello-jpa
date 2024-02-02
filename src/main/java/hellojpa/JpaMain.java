package hellojpa;import javax.persistence.EntityManager;import javax.persistence.EntityManagerFactory;import javax.persistence.EntityTransaction;import javax.persistence.Persistence;import java.util.List;public class JpaMain {    public static void main(String[] args) {        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");        EntityManager em = emf.createEntityManager();        EntityTransaction tx = em.getTransaction();        tx.begin();        try {            //3-1. 영속성 컨텍스트(1)            //[1] 비영속//            Member member1 = new Member();//            member1.setId(20L);//            member1.setName("20L");            //member객체 를 최초로 생성하고 입력까지만 해둠 = 비영속 상태            //[2] 영속//            //아래 코드에 의해 member는 영속상태가 됨//            System.out.println("=== BEFORE ===");//            em.persist(member1);//            //영속상태로 변하긴하지만, db에 저장이 되는것은 아님!//            //만약 이 단계에서 db에저장이되려면 sql의 전달이 필요한데, 이 코드 앞뒤에 sysout을 찍어서 sql이 언제 전달되는지 확인해볼 수 있음//            System.out.println("=== AFTER ===");//            //sout after가 끝날때까지 sql은 실행되지 않음 = persist에 의해 db에 저장되는것이 아님            //3-2. 영속성 컨텍스트(2) - 1차캐시 확인해보기(1)//            Member member0 = new Member();//            member0.setId(123L);//            member0.setName("123L");////            System.out.println("=== before ===");//            em.persist(member0);//            System.out.println("=== after ===");////            Member findMember = em.find(Member.class, 123L);////            System.out.println("=== find ===");//            System.out.println(findMember.getId());//            System.out.println(findMember.getName());//            System.out.println("=== ==== ===");            //3-2. 영속성 컨텍스트(2) - 1차캐시 확인해보기(2) : 이제 저장된 정보로 find를 2번 실행해볼게(데이터베이스에서 조회)//            Member findMember1 = em.find(Member.class, 123L); //처음에 불러올땐 sql을 불러야하고//            Member findMember2 = em.find(Member.class, 123L); //같은 내용을 두번째 불러올땐(트랜잭션 실행 전) sql을 불러오면 안됨            //3-2. 영속성 컨텍스트(2) - 영속 엔티티의 동일성 보장//            System.out.println("영속엔티티의 동일성 보장 : " + (findMember1==findMember2));            //3-2. 영속성 컨텍스트(2) - 트랜잭션을 지원하는 쓰기지연//            Member member111 = new Member(111L, "111");//            Member member222 = new Member(222L, "222");////            em.persist(member111);//            em.persist(member222);//            //위 단계까진 db에 전달을 하지 않으므로 sql이 발생하지 않음//            System.out.println("==== persist 이후 트랜잭션 커밋단계에서 db에 전달하기위한 sql이 생성됨 ===");            //3-2. 영속성 컨텍스트(2) - 변경감지//            Member member = em.find(Member.class, 111l);//            member.setName("update");//            //em.persist(member); //요걸 써야할거같지만 쓰면안됨!//            System.out.println("==== ===== ====");            //[3]준영속//            Member member2 = new Member();//            member2.setId(30L);//            member2.setName("30L");////            em.persist(member2);//            em.detach(member2);            //[4]삭제 : 요건 sql이 생성되네?//            Member member3 = new Member();//            member3.setId(40L);//            member3.setName("40L");////            em.persist(member3);//            em.remove(member3);            tx.commit();        } catch (Exception e) {            tx.rollback();        } finally {            em.close();        }        emf.close();    }}