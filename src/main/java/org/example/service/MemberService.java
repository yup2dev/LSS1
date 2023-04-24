package org.example.service;

import org.example.Container;
import org.example.dto.Member;
import org.example.repository.MemberRepository;

import java.sql.Connection;

public class MemberService {
    private MemberRepository memberRepository;
    public MemberService() {
        memberRepository = Container.memberRepository;
    }

    public boolean isLoginIdDup(String loginId) {
        return memberRepository.isLoginIdDup(loginId);
    }

    public int join(String loginId, String loginPw, String name, String nickname, String birth) {
        return memberRepository.join(loginId, loginPw, name, nickname, birth);
    }

    public Member getMemberByLoginId(String loginId) {
        return memberRepository.getMemberByLoginId(loginId);
    }
}
