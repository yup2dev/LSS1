package org.example.controller;

import org.example.Container;
import org.example.dto.Article;
import org.example.dto.ArticleComment;

import java.sql.*;
import java.util.ArrayList;

import static org.example.Container.*;

public class ArticleController {
    int hit = 0;

    public void run() {
        while (true) {
            System.out.println("-".repeat(30));
            System.out.println("게시판 보기 / 상세보기 / 글쓰기 / 돌아가기");
            System.out.printf("게시판 명령어: ");
            String command = Container.scanner.nextLine();
            if (command.equals("게시판 보기")) {
                articleController.showList();
            } else if (command.equals("상세보기")) {
                articleController.showDetail();
            } else if (command.equals("글쓰기")) {
                articleController.write();
            } else if (command.equals("돌아가기")) {
                break;
            }
        }
    }

    private void showList() {
        ArrayList<Article> articleList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:3306/LSS?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");

            String sql = ("SELECT A.id, A.title");
            sql += (" FROM article AS A");

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");

                Article article = new Article(id, title);
                articleList.add(article);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
            System.out.println("에러: " + e);
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

        if (articleList.isEmpty()) {
            System.out.println("게시물이 존재 하지 않습니다.");
        }

        for (Article article : articleList) {
            System.out.printf("%d / %s \n", article.id, article.title);
        }
        System.out.printf("총 등록된 게시글 수는 %d 입니다\n", articleList.size());
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
        int id = articleService.write(memberId, title, body, hit);
        System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
        ArrayList<Article> articleList = new ArrayList<>();
        articleList.add(new Article(id, title));
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

        articleService.increaseHit(id);
        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.println("-".repeat(30));
        System.out.printf("번호 : %d\n", article.id);
        System.out.printf("등록날짜 : %s\n", article.regDate);
        System.out.printf("수정날짜 : %s\n", article.updateDate);
        System.out.printf("작성자 : %s\n", article.extra__writerName);
        System.out.printf("조회수 : %d\n", article.hit);
        System.out.printf("제목 : %s\n", article.title);
        System.out.printf("내용 : %s\n", article.body);
        System.out.println("-".repeat(30));
        articleService.showArticleComment(id);

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
                articleService.showArticleComment(id);
            } else if (cmd.equals("뒤로가기")) {
                System.out.println("-".repeat(30));
                System.out.println("게시물 목록으로 돌아갑니다.");
                break;
            }
        }
    }
    public void addcomment(int articleID) {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.println("댓글 내용을 입력해주세요 : ");
        String comment = Container.scanner.nextLine();

        int memberId = Container.session.loginedMemberId;
        int id = articleService.addcomment(memberId, articleID, comment);
        System.out.printf("댓글이 등록되었습니다.\n");
        ArrayList<ArticleComment> articleCommentList = new ArrayList<>();
        articleCommentList.add(new ArticleComment(id, memberId, articleID, comment));
    }
}
