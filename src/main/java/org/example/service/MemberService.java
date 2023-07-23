package org.example.service;

import org.example.Container;
import org.example.dto.Member;
import org.example.repository.MemberRepository;


public class MemberService {

    public boolean isLoginIdDup(String loginId) {
        return Container.memberRepository.isLoginIdDup(loginId);
    }

    public int join(String loginId, String loginPw, String name, String nickname, String birth) {
        return Container.memberRepository.join(loginId, loginPw, name, nickname, birth);
    }

    public Member getMemberByLoginId(String loginId) {
        return Container.memberRepository.getMemberByLoginId(loginId);
    }
}
