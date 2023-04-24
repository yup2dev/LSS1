package com.mysql.jdbc.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCContentTest {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Content> contents = new ArrayList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:3306/LSS?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");

            String sql = "SELECT *";
            sql += " FROM content";
            sql += " ORDER BY id";

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

            System.out.println("결과 : " + contents);

        }
        catch(ClassNotFoundException e){
            System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
            System.out.println("에러: " + e);
        }
        finally{
            try{
                if( rs != null && !rs.isClosed()){
                    rs.close();
                }
            }
            catch( SQLException e){
                e.printStackTrace();
            }
            try{
                if( pstmt != null && !pstmt.isClosed()){
                    pstmt.close();
                }
            }
            catch( SQLException e){
                e.printStackTrace();
            }
            try{
                if( conn != null && !conn.isClosed()){
                    conn.close();
                }
            }
            catch( SQLException e){
                e.printStackTrace();
            }
        }
    }
}
