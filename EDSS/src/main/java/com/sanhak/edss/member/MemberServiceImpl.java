package com.sanhak.edss.member;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    public Member regiserMember(Member member) {
        validateUser(member.getEmployNumber());
        memberRepository.save(member);
        return member;
    }


    public void validateUser(String employNumber) {
        Member _member = memberRepository.findByEmployNumber(employNumber);
        if(_member != null) {
            throw new IllegalStateException("이미 가입된 사원입니다.");
        }
    }

    public Member findMemberInfoByEmployNumber(String employNumber) {
        Member member = memberRepository.findByEmployNumber(employNumber);
        if (member == null)
            throw new RuntimeException("사용자 정보가 없습니다.");
        return member;
    }
}
