package org.example.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Member {
  private int id;
  private String regDate;
  private String updateDate;
  private String loginId;
  private String loginPw;
  private String name;
  private String nickname;
  private String birth;

  public Member(Map<String, Object> memberMap) {
    this.id = (int) memberMap.get("id");
    this.regDate = (String) memberMap.get("regDate");
    this.updateDate = (String) memberMap.get("updateDate");
    this.loginId = (String) memberMap.get("loginId");
    this.loginPw = (String) memberMap.get("loginPw");
    this.name = (String) memberMap.get("name");
    this.nickname = (String) memberMap.get("nickname");
    this.birth = (String) memberMap.get("birth");
  }
}
