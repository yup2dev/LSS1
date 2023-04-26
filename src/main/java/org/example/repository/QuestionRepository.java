package org.example.repository;

import org.example.Container;
import org.example.dto.Question;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.util.List;
import java.util.Map;

public class QuestionRepository {

    public List<Map<String,Object>> findComment(int questionid){
        SecSql sql = new SecSql();
        sql.append("SELECT questioncomment.questionid, questioncomment.comment from questioncomment");
        sql.append("WHERE questionid = " + questionid);
        return DBUtil.selectRows(Container.conn, sql);
    }

    public void showComment(int questionid) {
        for(Map<String, Object> commentMap : findComment(questionid)){
            System.out.println("댓글 : " + commentMap.get("comment"));
        }
    }

    public int qwrite(int memberId, String title, String body) {

        SecSql sql = new SecSql();

        sql.append("INSERT INTO question");
        sql.append(" SET regDate = NOW()");
        sql.append(", updateDate = NOW()");

        sql.append(", memberId = ?", memberId);
        sql.append(", title = ?", title);
        sql.append(", `body` = ?", body);


        int qid = DBUtil.insert(Container.conn, sql);
        return qid;
    }

    public Question getQuestionById(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT Q.*");
        sql.append(", M.nickname AS extra__writerName");
        sql.append("FROM question AS Q");
        sql.append("INNER JOIN member AS M");
        sql.append("ON Q.memberId = M.id");
        sql.append("WHERE Q.id = ?", id);
        Map<String, Object> questionMap = DBUtil.selectRow(Container.conn, sql);
        if (questionMap.isEmpty()) {
            return null;
        }
        return new Question(questionMap);
    }

    public int addcomment(int memberId, int questionId, String comment) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO questioncomment");
        sql.append(" SET regDate = NOW()");
        sql.append(", updateDate = NOW()");
        sql.append(", memberId = ?", memberId);
        sql.append(", questionId = ?", questionId);
        sql.append(", `comment` = ?", comment);

        int id = DBUtil.insert(Container.conn, sql);
        System.out.println(id);
        return id;
    }
}
