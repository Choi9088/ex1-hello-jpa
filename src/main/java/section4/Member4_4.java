package section4;import javax.persistence.*;import java.util.Date;@Entity//@GeneratedValue 를 통해 기본키자동생성을 사용할 때, 관련 seq에 대한 설정은 클래스단에서!//4-4. 기본키 매핑 : Sequence@SequenceGenerator(        name = "member_seq_generator", //generator 이름        sequenceName = "member_seq", //seq 이름        initialValue = 1, //시작값        allocationSize = 10 //한번 콜 할때마다 불러올 사이즈! 기본값은 50 > 성능최적화를 위해 사용)//4-4. 기본키 매핑 : Table//@TableGenerator(//        name = "MEMBER_SEQ_GENERATOR",//        table = "MY_SEQUENCES", //사용할 테이블명//        pkColumnValue = "MEMBER_SEQ", //생성할 pk 컬럼명//        allocationSize = 1)public class Member4_4 {    //4-4. 기본키 매핑    @Id    //@Id : 기본키를 직접 할당하는경우에 사용    //기본키를 자동으로 할당하고싶은경우 @GeneratedValue 어노테이션 + 사용할 전략속성 추가    //4-4. 기본키 매핑 : Sequence    @GeneratedValue(strategy = GenerationType.SEQUENCE, //어떤 전략을 사용할것인지            generator = "member_seq_generator" //어떤 제너레이터를 사용할것인지    )    //4-4. 기본키 매핑 : Table//    @GeneratedValue(strategy = GenerationType.TABLE, //매핑전략을 table로 설정했고//            generator = "MEMBER_SEQ_GENERATOR") //MEMBER_SEQ_GENERATOR를 연결해줌    private Long id;    //기록 남기고싶어서 IDENTITY랑 SEQUENCE를 한 엔티티에 넣었더니 시퀀스가 제대로 동작안함    //Id와 함께 사용해야하는데..Id는 한개씩만 사용 가능한건가?    @Column(name = "name", nullable = false)    private String username;    public Member4_4(){}    public Long getId() {        return id;    }    public void setId(Long id) {        this.id = id;    }    public String getUsername() {        return username;    }    public void setUsername(String username) {        this.username = username;    }}