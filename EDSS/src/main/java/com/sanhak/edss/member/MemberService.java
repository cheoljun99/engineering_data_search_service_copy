package com.sanhak.edss.member;

public interface MemberService {
    Member regiserMember(Member member);
    void validateUser(String employNumber);
    Member findMemberInfoByEmployNumber(String employNumber);
}
