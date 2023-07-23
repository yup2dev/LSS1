package org.example.controller;

import org.example.Container;

public class MoveController {
    public void move() {
        while (true) {
            System.out.println("게시판 / 마이 스케줄러 / Q&A / 로스트아크 공식홈페이지 / 내정보 / 돌아가기");
            System.out.printf("이동 명령어 : ");
            String cmd = Container.scanner.nextLine();
            if (cmd.equals("게시판")) {
                System.out.println("게시판으로 이동합니다");
                Container.articleController.run();
            } else if (cmd.equals("마이 스케줄러")) {
                System.out.println("마이 스케줄러로 이동합니다");
                System.out.println("-".repeat(30));
                Container.scheduleController.run();
            } else if (cmd.equals("Q&A")) {
                System.out.println("Q&A로 이동합니다");
                System.out.println("-".repeat(30));
                Container.questionController.run();
            } else if (cmd.equals("로스트아크 공식홈페이지")) {
                System.out.println("로스트아크 공식홈페이지로 이동합니다");
                System.out.println("-".repeat(30));
                System.out.println("https://lostark.game.onstove.com/Main");
            } else if (cmd.equals("내정보")) {
                if(Container.session.isLogined() == false) {
                    System.out.println("로그인 상태가 아닙니다.");
                    break;
                }
                System.out.println("내 정보 페이지로 이동합니다.");
                System.out.println("-".repeat(30));
                Container.memberController.checkid();
            } else if (cmd.equals("돌아가기")) {
                System.out.println("메인홈페이지로 이동합니다");
                System.out.println("-".repeat(30));
                break;
            }
        }
    }
}
