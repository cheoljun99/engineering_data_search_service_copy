package com.sanhak.edss.member;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member, String> {
    Member findByEmployNumber(String employNumber);

    boolean existsByEmployNumber(String employNumber);
}
