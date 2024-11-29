package hello.jpaStudy;

import hello.jpaStudy.chapter5.Member;
import hello.jpaStudy.chapter5.Team;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class JpaTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Commit
    void testSave() {

        //팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        em.persist(member1);
        Member member2 = new Member("member2", "회원1");
        member2.setTeam(team1);
        em.persist(member2);

    }

    @Test
     void queryLogicJoin(EntityManager em) {

        String jpql = "select m from Member m join m.team t where " +
                "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }
}
