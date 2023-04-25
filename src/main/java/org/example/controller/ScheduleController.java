package org.example.controller;

import org.example.Container;
import org.example.Rq;
import org.example.dto.Content;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ScheduleController {
    public void run() {

        Scanner sc = Container.scanner;

        while (true) {
            System.out.println("\n마이 스케줄러 / 요일별 일정 / 종류별 일정 / 돌아가기\n");
            System.out.println("-".repeat(30));
            System.out.printf("명령) ");
            String cmd = Container.scanner.nextLine();

            Rq rq = new Rq(cmd);
            Connection conn = null;

            if (rq.getUrlPath().equals("돌아가기")) {
                break;
            }

            try {
                // 연결시작
                Class.forName("com.mysql.jdbc.Driver");

                String url = "jdbc:mysql://127.0.0.1:3306/LSS?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

                conn = DriverManager.getConnection(url, "root", "");
                // 연결끝

                // 명령 실행
                int actionResult = doAction(conn, rq);

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
        }
    }

    private int doAction(Connection conn, Rq rq) {
        if (rq.getUrlPath().equals("마이 스케줄러")) {
            while (true) {
                System.out.println("\n========== 마이 스케줄러 ==========");
                System.out.println("-".repeat(30));

                PreparedStatement pstmt = null;
                ResultSet rs = null;

                List<Content> contents = new ArrayList<>();

                try {
                    String sql = "SELECT *";
                    sql += " FROM content";
                    sql += " WHERE `select` = TRUE";

                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery(sql);


                    while (rs.next()) {
                        int id = rs.getInt("id");

                        String type = rs.getString("type");
                        String content_name = rs.getString("content_name");
                        String location = rs.getString("location");
                        String time = rs.getString("time");

                        Content content = new Content(id, type, content_name, location, time);
                        contents.add(content);
                    }

                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (rs != null && !rs.isClosed()) {
                            rs.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("종류 / 이름 / 위치 / 발생시간\n");

                if (contents.isEmpty()) {
                    System.out.println("마이 스케줄이 존재 하지 않습니다.");
                }

                for (Content content : contents) {
                    System.out.printf("%s / %s / %s / %s\n\n", content.type, content.content_name, content.location, content.time);
                }

                System.out.println("-".repeat(30));
                System.out.println("추가하기 / 삭제하기 / 돌아가기");
                System.out.print("명령) ");
                String scheduleCmd = Container.scanner.nextLine();

                if (scheduleCmd.equals("추가하기") || scheduleCmd.equals("삭제하기")) {
                    contentList(conn);
                }

                while (true) {
                    if (scheduleCmd.equals("추가하기")) {

                        System.out.println("번호 입력(-1 : 돌아가기)) ");
                        int addcontentId = -1;

                        try {
                            addcontentId = Container.scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("숫자만 입력하세요.");
                        }
                        if (addcontentId == -1) break;

                        updateTrue(addcontentId);


                    } else if (scheduleCmd.equals("삭제하기")) {

                        System.out.println("번호 입력(-1 : 돌아가기)) ");
                        int deletecontentId = -1;

                        try {
                            deletecontentId = Container.scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("숫자만 입력하세요.");
                        }
                        if (deletecontentId == -1) break;

                        updateFalse(deletecontentId);

                    } else if (scheduleCmd.equals("돌아가기")) {
                        return 0;

                    } else {
                        System.out.println("메뉴를 다시 선택해주세요.");
                        System.out.println("-".repeat(30));
                        break;
                    }
                }

            }


        } else if (rq.getUrlPath().equals("요일별 일정")) {
            while (true) {

                System.out.println("요일) 월 / 화 / 수 / 목 / 금 / 토 / 일 / 돌아가기");
                System.out.printf("요일 선택) ");
                String selectday = Container.scanner.nextLine();

                PreparedStatement pstmt = null;
                ResultSet rs = null;

                List<Content> contents = new ArrayList<>();

                try {
                    String sql = "SELECT *";
                    sql += " FROM content";

                    if (selectday.equals("월")) {
                        sql += " WHERE monday = TRUE";
                    } else if (selectday.equals("화")) {
                        sql += " WHERE tuesday = TRUE";
                    } else if (selectday.equals("수")) {
                        sql += " WHERE wednesday = TRUE";
                    } else if (selectday.equals("목")) {
                        sql += " WHERE thursday = TRUE";
                    } else if (selectday.equals("금")) {
                        sql += " WHERE friday = TRUE";
                    } else if (selectday.equals("토")) {
                        sql += " WHERE saturday = TRUE";
                    } else if (selectday.equals("일")) {
                        sql += " WHERE sunday = TRUE";
                    } else if (selectday.equals("돌아가기")) {
                        break;
                    } else {
                        System.out.println("메뉴를 다시 선택해주세요");
                        System.out.println("-".repeat(30));
                        continue;
                    }

                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery(sql);


                    while (rs.next()) {
                        int id = rs.getInt("id");

                        String type = rs.getString("type");
                        String content_name = rs.getString("content_name");
                        String location = rs.getString("location");
                        String time = rs.getString("time");

                        Content content = new Content(id, type, content_name, location, time);
                        contents.add(content);
                    }

                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (rs != null && !rs.isClosed()) {
                            rs.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("\n========== " + selectday + "요일 스케줄 ==========");
                System.out.println("종류 / 이름 / 위치 / 발생시간\n");

                if (contents.isEmpty()) {
                    System.out.println("스케줄이 존재 하지 않습니다.");
                    return -1;
                }

                for (Content content : contents) {
                    System.out.printf("%s / %s / %s / %s\n\n", content.type, content.content_name, content.location, content.time);
                }
                System.out.println("-".repeat(30));

            }
        } else if (rq.getUrlPath().equals("종류별 일정")) {
            while (true) {

                System.out.println("섬 / 필드보스 / 카오스게이트 / 유령선 / 돌아가기");
                System.out.printf("종류 선택) ");
                String selectType = Container.scanner.nextLine();

                PreparedStatement pstmt = null;
                ResultSet rs = null;

                List<Content> contents = new ArrayList<>();


                try {
                    String sql = "SELECT *";
                    sql += " FROM content";

                    if (selectType.equals("섬")) {
                        sql += " WHERE type = '섬'";
                    } else if (selectType.equals("필드보스")) {
                        sql += " WHERE type = '필드보스'";
                    } else if (selectType.equals("카오스게이트")) {
                        sql += " WHERE type = '카오스게이트'";
                    } else if (selectType.equals("유령선")) {
                        sql += " WHERE type = '유령선'";
                    } else if (selectType.equals("돌아가기")) {
                        break;
                    } else {
                        System.out.println("메뉴를 다시 선택해주세요");
                        System.out.println("-".repeat(30));
                        continue;
                    }

                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery(sql);


                    while (rs.next()) {
                        int id = rs.getInt("id");

                        String type = rs.getString("type");
                        String content_name = rs.getString("content_name");
                        String location = rs.getString("location");
                        String time = rs.getString("time");

                        Content content = new Content(id, type, content_name, location, time);
                        contents.add(content);
                    }

                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (rs != null && !rs.isClosed()) {
                            rs.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("\n========== " + selectType + " 스케줄 ==========");

                System.out.println("이름 / 위치 / 발생시간\n");

                if (contents.isEmpty()) {
                    System.out.println("스케줄이 존재 하지 않습니다.");
                    return -1;
                }

                for (Content content : contents) {
                    System.out.printf("%s / %s / %s\n\n", content.content_name, content.location, content.time);
                }
                System.out.println("-".repeat(30));
            }

        } else {
            System.out.println("메뉴를 다시 선택해주세요.");
            System.out.println("-".repeat(30));
        }
        return 0;
    }

    public void updateTrue(int id) {
        SecSql sql = new SecSql();

        sql.append("UPDATE content");
        sql.append("SET `select` = TRUE");
        sql.append("WHERE id = " + id);

        DBUtil.update(Container.conn, sql);
    }

    public void updateFalse(int id) {
        SecSql sql = new SecSql();

        sql.append("UPDATE content");
        sql.append("SET `select` = FALSE");
        sql.append("WHERE id = " + id);

        DBUtil.update(Container.conn, sql);
    }

    public void contentList(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Content> contents = new ArrayList<>();
        try {
            String sql = "SELECT *";
            sql += " FROM content";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery(sql);


            while (rs.next()) {
                int id = rs.getInt("id");

                String type = rs.getString("type");
                String content_name = rs.getString("content_name");
                String location = rs.getString("location");
                String time = rs.getString("time");

                Content content = new Content(id, type, content_name, location, time);
                contents.add(content);
            }

        } catch (SQLException e) {
            System.out.println("에러 : " + e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n========== 전체 스케줄 ==========");
        System.out.println("번호 / 종류 / 이름 / 위치 / 발생시간\n");

        for (Content content : contents) {
            System.out.printf("%d번 / %s / %s / %s / %s\n\n", content.id, content.type, content.content_name, content.location, content.time);
        }
        System.out.println("-".repeat(30));


    }

}

