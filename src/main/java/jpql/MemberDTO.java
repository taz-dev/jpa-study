package jpql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private String username;
    private int age;

    //생성자
    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
