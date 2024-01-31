package hellojpa;import javax.persistence.EntityManager;import javax.persistence.EntityManagerFactory;import javax.persistence.EntityTransaction;import javax.persistence.Persistence;import java.util.List;public class JpaMain {    public static void main(String[] args) {        //factory는 로딩시점에 딱 한번만 만들어야함        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");        //manager는 트랜잭션 단위마다 만들어줘야함        EntityManager em = emf.createEntityManager();        //이 안에 실제 동작하는 코드를 작성하게됨        //jpa는 트랜잭션 안에서 동작을 구현해야하므로 트랜잭션을 얻어오고        EntityTransaction tx = em.getTransaction();        tx.begin(); //트랜잭션 시작!        //(1) Member 저장//        try {//            Member member = new Member(); //member타입 객체를 생성하고//            member.setId(3L);//            member.setName("hello_3"); //id랑 name 넣어주고//            em.persist(member); //persist = 저장, insert////            tx.commit(); //트랜잭션 끝! 문제가 생기는경우엔 rollback을 구현해야하지만 일단 테스트로 돌려보기!//        } catch (Exception e) {//            tx.rollback(); //진행중 문제가 발생하는경우 트랜잭션 롤백하기//        } finally {//            em.close(); //최종적으로 트랜잭션이 어떤식으로든 마무리된 뒤에는 manager 꼭 닫아줘야함!//        }        //(2) 삭제//        try {////            //삭제하고싶은 값을 찾아서 findMember에 담아주고//            Member findMember = em.find(Member.class, 3L);//            //find 잘 되었는지 확인해보기//            System.out.println("ID : "+findMember.getId());//            System.out.println("NAME : "+findMember.getName());//            //찾아온 값을 전달해서 remove로 삭제//            em.remove(findMember);////            tx.commit(); //트랜잭션 끝! 문제가 생기는경우엔 rollback을 구현해야하지만 일단 테스트로 돌려보기!//        } catch (Exception e) {//            tx.rollback(); //진행중 문제가 발생하는경우 트랜잭션 롤백하기//        } finally {//            em.close(); //최종적으로 트랜잭션이 어떤식으로든 마무리된 뒤에는 manager 꼭 닫아줘야함!//        }        //(3) 수정//        try {////            //수정하고싶은 값을 찾아서 findMember에 담아주고//            Member findMember = em.find(Member.class, 2L);//            //찾아온 값에 set메소드를 사용해 넣어주면//            findMember.setName("update");//            //set메소드 사용후에 persist로 저장해줘야할거같지만 여기에서 수정은 끝!////            tx.commit(); //트랜잭션 끝! 문제가 생기는경우엔 rollback을 구현해야하지만 일단 테스트로 돌려보기!//        } catch (Exception e) {//            tx.rollback(); //진행중 문제가 발생하는경우 트랜잭션 롤백하기//        } finally {//            em.close(); //최종적으로 트랜잭션이 어떤식으로든 마무리된 뒤에는 manager 꼭 닫아줘야함!//        }        //(4)JPQL        try {            //(4-1) 전체회원 조회            List<Member> result = em.createQuery("select m from Member as m", Member.class)                    .getResultList();            for (Member member : result) {                System.out.println("Member Name : "+member.getName());            }            tx.commit(); //트랜잭션 끝! 문제가 생기는경우엔 rollback을 구현해야하지만 일단 테스트로 돌려보기!        } catch (Exception e) {            tx.rollback(); //진행중 문제가 발생하는경우 트랜잭션 롤백하기        } finally {            em.close(); //최종적으로 트랜잭션이 어떤식으로든 마무리된 뒤에는 manager 꼭 닫아줘야함!        }        emf.close(); //실제 애플리케이션이 완전히 끝나면 emf를 닫아줘야함    }}