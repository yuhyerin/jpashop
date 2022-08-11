package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception{
        // tdd
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
        /**
         * member와 findMember는 같은 객체로 따짐.
         * -> 같은 트랜잭션 안에서 저장하고, 조회하기때문에 영속성 컨텍스트가 같음.
         * 같은 영속성 컨텍스트 안에서 id(식별자)값이 같으면 같은 entity로 식별된다.
         * 1차캐시에서 기존에 관리하던 객체게 나오기 때문.
         * 콘솔에 찍히는 쿼리도 insert문만 보인다. select는 실행하지 않음. */
    }

}