package org.example.controller;

import org.example.Container;
import org.example.dto.Question;
import org.example.dto.QuestionComment;

import java.sql.*;
import java.util.ArrayList;

import static org.example.Container.*;

public class QuestionController {
    int hit = 0;

    public void run() {
        while (true) {
            System.out.println("-".repeat(30));
            System.out.println("Q&A 보기 / 글쓰기 / 상세보기 / 돌아가기");
            System.out.printf("게시판 명령어: ");
            String command = Container.scanner.nextLine();
            if (command.equals("Q&A 보기")) {
                questionController.showList();
            } else if (command.equals("글쓰기")) {
                questionController.write();
            } else if (command.equals("상세보기")) {
                questionController.showDetail();
            } else if (command.equals("돌아가기")) {
                break;
            }
        }
    }

    private void showList() {
        ArrayList<Question> questionList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:3306/LSS?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");

            String sql = ("SELECT Q.id, Q.title");
            sql += (" FROM question AS Q");

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");

                Question question = new Question(id, title);
                questionList.add(question);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("번호 / 제목");

        if (questionList.isEmpty()) {
            System.out.println("게시물이 존재 하지 않습니다.");
        }

        for (Question question : questionList) {
            System.out.printf("%d / %s \n", question.id, question.title);
        }
        System.out.printf("총 등록된 게시글 수는 %d 입니다\n", questionList.size());
        System.out.println("-".repeat(30));
        scanner.nextLine();
    }


    public void write() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.println("== 게시물 등록 ==");
        System.out.printf("제목 : ");
        String title = Container.scanner.nextLine();
        System.out.printf("내용 : ");
        String body = Container.scanner.nextLine();

        int memberId = Container.session.loginedMemberId;
        int id = questionService.write(memberId, title, body, hit);
        System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
        ArrayList<Question> questionList = new ArrayList<>();
        questionList.add(new Question(id, title));
    }

    public void showDetail() {
        System.out.println("-".repeat(30));
        System.out.println("게시글 번호를 입력하세요");
        int id = Container.scanner.nextInt();
        Container.scanner.nextLine();
        if (id == 0) {
            System.out.println("id를 올바르게 입력해주세요.");
            return;
        }

        questionService.increaseHit(id);
        Question question = questionService.getQuestionById(id);

        if (question == null) {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.println("-".repeat(30));
        System.out.printf("번호 : %d\n", question.id);
        System.out.printf("등록날짜 : %s\n", question.regDate);
        System.out.printf("수정날짜 : %s\n", question.updateDate);
        System.out.printf("작성자 : %s\n", question.extra__writerName);
        System.out.printf("조회수 : %d\n", question.hit);
        System.out.printf("제목 : %s\n", question.title);
        System.out.printf("내용 : %s\n", question.body);
        System.out.println("-".repeat(30));
        questionService.showQuestionComment(id);

        while (true) {
            System.out.println("-".repeat(30));
            System.out.println("\n상세보기 기능을 선택해주세요(댓글등록, 뒤로가기)");
            String cmd = Container.scanner.nextLine().trim();

            if (cmd.equals("댓글등록")) {
                if (Container.session.isLogined() == false) {
                    System.out.println("로그인 후 이용해주세요.");
                    return;
                }
                System.out.println("-".repeat(30));
                addcomment(id);
                System.out.println("-".repeat(30));
                questionService.showQuestionComment(id);
            } else if (cmd.equals("뒤로가기")) {
                System.out.println("-".repeat(30));
                System.out.println("게시물 목록으로 돌아갑니다.");
                break;
            }
        }
    }
    public void addcomment(int questionID) {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.println("댓글 내용을 입력해주세요 : ");
        String comment = Container.scanner.nextLine();

        int memberId = Container.session.loginedMemberId;
        int id = questionService.addcomment(memberId, questionID, comment);
        System.out.printf("댓글이 등록되었습니다.\n");
        ArrayList<QuestionComment> questionCommentList = new ArrayList<>();
        questionCommentList.add(new QuestionComment(id, memberId, questionID, comment));
    }
}
