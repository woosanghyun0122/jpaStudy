package hello.jpaStudy.chapter5;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }
    


}
