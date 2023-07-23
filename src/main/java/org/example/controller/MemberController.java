package org.example.controller;

import org.example.Container;
import org.example.dto.Member;
import org.example.service.MemberService;

public class MemberController {
    private MemberService memberService;

    public MemberController() {
        memberService = Container.memberService;
    }

    public void join() {
        String loginId;
        String loginPw;
        String loginPwConfirm;
        String name;
        String nickname;
        String birth;

        System.out.println("== 회원 가입 ==");

        // 로그인 아이디 입력
        while (true) {
            System.out.printf("로그인 아이디 : ");
            loginId = Container.scanner.nextLine().trim();
            if (loginId.length() == 0) {
                System.out.println("로그인 아이디를 입력해주세요.");
                continue;
            }
            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
            if (isLoginIdDup) {
                System.out.printf("%s(은)는 이미 사용중인 로그인 아이디입니다.\n", loginId);
                continue;
            }
            break;
        }

        // 로그인 비밀번호 입력
        while (true) {
            System.out.printf("로그인 비밀번호 : ");
            loginPw = Container.scanner.nextLine().trim();
            if (loginPw.length() == 0) {
                System.out.println("로그인 비밀번호를 입력해주세요.");
                continue;
            }

            // 로그인 비밀번호 확인 입력
            boolean loginPwConfirmIsSame = true;
            while (true) {
                System.out.printf("로그인 비밀번호 확인 : ");
                loginPwConfirm = Container.scanner.nextLine().trim();
                if (loginPwConfirm.length() == 0) {
                    System.out.println("로그인 비밀번호를 입력해주세요.");
                    continue;
                }
                if (loginPw.equals(loginPwConfirm) == false) {
                    System.out.println("로그인 비밀번호가 일치하지 않습니다.");
                    loginPwConfirmIsSame = false;
                    break;
                }
                break;
            }
            if (loginPwConfirmIsSame) {
                break;
            }
        }

        // 이름 입력
        while (true) {
            System.out.printf("이름 : ");
            name = Container.scanner.nextLine().trim();
            if (name.length() == 0) {
                System.out.println("이름을 입력해주세요.");
                continue;
            }
            break;
        }

        //생년월일 입력
        while (true) {
            System.out.printf("생년월일 : ");
            birth = Container.scanner.nextLine().trim();
            if (name.length() == 0) {
                System.out.println("닉네임을 입력해주세요.");
                continue;
            }
            break;
        }

        //닉네임 입력
        while (true) {
            System.out.printf("닉네임 : ");
            nickname = Container.scanner.nextLine().trim();

            if (nickname.length() == 0) {
                System.out.println("닉네임을 입력해주세요.");
                continue;
            }
            break;
        }
        int id = memberService.join(loginId, loginPw, name, nickname, birth);
        System.out.printf("%d번 회원이 등록되었습니다.\n", id);
        System.out.println("-".repeat(30));
    }


    public void login() {
        String loginId;
        String loginPw;

        System.out.println("== 로그인 ==");

        System.out.printf("로그인 아이디 : ");
        loginId = Container.scanner.nextLine().trim();

        if (loginId.length() == 0) {
            System.out.println("로그인 아이디를 입력해주세요.");
            return;
        }

        Member member = memberService.getMemberByLoginId(loginId);

        if (member == null ) {
            System.out.println("입력하신 로그인 아이디는 존재하지 않습니다.");
            return;
        }

        int loginTryMaxCount = 3;
        int loginTryCount = 0;

        // 로그인 비밀번호 입력
        while (true) {
            if(loginTryCount >= loginTryMaxCount) {
                System.out.println("비밀번호 확인 후 다음에 다시 시도해주세요.");
                break;
            }

            System.out.printf("로그인 비밀번호 : ");
            loginPw = Container.scanner.nextLine().trim();

            if (loginPw.length() == 0) {
                System.out.println("로그인 비밀번호를 입력해주세요.");
                continue;
            }

            if(member.getLoginPw().equals(loginPw) == false) {
                loginTryCount++;
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }

            System.out.printf("\"%s\"님 환영합니다.\n", member.getName());
            Container.session.login(member);

            System.out.println("-".repeat(30));
            break;
        }
        Container.moveController.move();
    }

    public void checkid() {
        if(Container.session.isLogined() == false) {
            System.out.println("로그인 상태가 아닙니다.");
        }
        else {
            System.out.println("아이디 : " + Container.session.loginedMember.getLoginId());
            System.out.println("이름 : " + Container.session.loginedMember.getName());
            System.out.println("닉네임 : " + Container.session.loginedMember.getNickname());
            System.out.println("생년월일 : " + Container.session.loginedMember.getBirth());
            System.out.println("가입일 : " + Container.session.loginedMember.getRegDate());
            System.out.println("-".repeat(30));
        }
    }

    public void logout() {
        Container.session.logout();
        System.out.println("로그아웃 되었습니다.");
    }
}
