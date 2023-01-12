package com.sanhak.edss.member;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
public class Member {
    @Id
    private int id;
    private String employNumber;
    private String password;
    private String userName;
    private String email;
    private int phoneNumber;
    private int birthday;
    private String adminKey;

    Member(String employNumber, String password, String userName, String email, int phoneNumber, int birthday, String adminKey) {
        this.employNumber = employNumber;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.adminKey = adminKey;
    }
}
