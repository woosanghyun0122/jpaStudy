package hello.jpaStudy.chapter5;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TestApplication {

    public static void main(String[] args) {

        EntityManager em = setting();

        // 단방향 저장
        //testSave(em);

        //testSaveNonOwner(em);

        // 쌍방향 저장
        testSaveDual(em);

        Member member = em.find(Member.class,"member1");
        log.info("member={}", member.getTeam().getName());

        biDirection(em);

        //updateRelation(em);

        queryLogicJoin(em);

        em.getTransaction().rollback();

    }

    public static EntityManager setting() {

        EntityManager em = Persistence.createEntityManagerFactory("jpaStudy").createEntityManager();

        em.getTransaction().begin();

        return em;
    }

    public static void testSave(EntityManager em) {

        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        em.persist(member1);
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);

        em.flush();
        em.clear();
    }

    /**
     *  jpql 조회
     * */
    public static void queryLogicJoin(EntityManager em) {

        String jpql = "select m from Member m join m.team t where " + "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀2")
                .getResultList();

        resultList.stream()
                .forEach(member -> log.info("member={}, team={}", member.getUsername(), member.getTeam().getName()));
    }

    public static void updateRelation(EntityManager em) {

        Team team2 = new Team("team2", "팀2");
        em.persist(team2);
        em.flush();
        em.clear();

        Member member = em.find(Member.class, "member1");
        log.info("member={} update", member.getTeam().getName());
        member.setTeam(team2);
    }

    public static void deleteRelation(EntityManager em) {

        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
    }

    public static void biDirection(EntityManager em) {

        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();

        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
        }
    }

    public static void testSaveNonOwner(EntityManager em) {

        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        em.persist(member2);

        Team team = new Team("team1", "팀1");

        team.getMembers().add(member1);
        team.getMembers().add(member2);

        em.persist(team);
    }

    public static void testSaveDual(EntityManager em) {

        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");

        //쌍방향관계
        member1.setTeam(team1);
        team1.getMembers().add(member1);
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");

        member2.setTeam(team1);
        team1.getMembers().add(member2);
        em.persist(member2);
    }

}
