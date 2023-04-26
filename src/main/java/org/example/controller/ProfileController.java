package org.example.controller;

import org.example.Container;
import org.example.dto.Member;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileController {
    public void run() {

        System.out.println("\n========= 내 정보 =========");
        Connection conn = null;


        try {
            // 연결시작
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:3306/LSS?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");
            // 연결끝

            // 명령 실행
            doAction(conn);

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

    private int doAction(Connection conn) {

        String findloginId = Container.session.loginedMember.getLoginId();


        SecSql sql = new SecSql();

        sql.append("SELECT *");
        sql.append("FROM member");
        sql.append("WHERE `loginId` = " + findloginId);

        List<Map<String, Object>> memberListMap = DBUtil.selectRows(Container.conn, sql);

        // map 분리
        String profileRow = memberListMap.get(0).toString();
        String[] profileDatas = profileRow.split(",");
        Map<String, String> params = new HashMap<>();


        for (String profileData : profileDatas) {
            String[] datas = profileData.split("=", 2);
            String key = datas[0];
            String value = datas[1];

            params.put(key, value);
        }
        // 분리 끝

        System.out.println("아이디: " + params.get(" loginId"));
        System.out.println("비밀번호: " + params.get(" loginPw"));
        System.out.println("닉네임: " + params.get(" nickname"));
        System.out.println("이름: " + params.get(" name"));
        System.out.println("생년월일: " + params.get(" birth"));
        System.out.println("-".repeat(30));

        while (true) {
            System.out.println("비밀번호 변경 / 닉네임 변경 / 돌아가기");
            System.out.print("명령) ");
            String cmd = Container.scanner.nextLine();

            if (cmd.equals("비밀번호 변경")) {
                System.out.printf("새 비밀번호 : ");
                String loginPw = Container.scanner.nextLine();

                while(true) {
                    System.out.printf("비밀번호 확인 : ");
                    String checkloginPw = Container.scanner.nextLine();

                    if (checkloginPw.equals(loginPw) == true ) {
                        updateLoginPw(findloginId, loginPw);
                        System.out.println("비밀번호가 수정되었습니다.\n");
                        break;
                    } else {
                        System.out.println("비밀번호가 다릅니다.");

                    }
                }


            } else if (cmd.equals("닉네임 변경")) {
                System.out.printf("새 닉네임 : ");
                String nickname = Container.scanner.nextLine();

                updateNickname(findloginId, nickname);
                System.out.println("닉네임이 수정되었습니다.\n");
            } else if (cmd.equals("돌아가기")) {
                System.out.println("-".repeat(30));
                break;
            } else {
                System.out.println("다시 입력해주세요.");
            }
        }

        return 0;
    }

    private void updateNickname(String findloginId, String nickname) {
        SecSql sql = new SecSql();

        sql.append("UPDATE member");
        sql.append("SET `nickname` = '" + nickname + "'");
        sql.append("WHERE `loginId` = " + findloginId);

        DBUtil.update(Container.conn, sql);

    }

    private void updateLoginPw(String findloginId, String loginPw) {
        SecSql sql = new SecSql();

        sql.append("UPDATE member");
        sql.append("SET `loginPw` = '" + loginPw + "'");
        sql.append("WHERE `loginId` = " + findloginId);

        DBUtil.update(Container.conn, sql);

    }
}