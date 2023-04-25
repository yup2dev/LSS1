package org.example.repository;

import org.example.Container;
import org.example.dto.Article;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {

    public List<Map<String,Object>> findComment(){
        SecSql sql = new SecSql();
        sql.append("SELECT articlecomment.articleid, articlecomment.comment from articlecomment");

        return DBUtil.selectRows(Container.conn, sql);
    }
    public void showComment() {
        for(Map<String, Object> commentMap : findComment()){
            System.out.println(commentMap.get("articlecomment"));
        }
    }

    public int write(int memberId, String title, String body, int hit) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append(" SET regDate = NOW()");
        sql.append(", updateDate = NOW()");
        sql.append(", memberId = ?", memberId);
        sql.append(", title = ?", title);
        sql.append(", `body` = ?", body);
        sql.append(", `hit` = ?", hit);

        int id = DBUtil.insert(Container.conn, sql);
        System.out.println(id);
        return id;
    }

    public Article getArticleById(int id) {
        SecSql sql = new SecSql();

        sql.append("SELECT A.*");
        sql.append(", M.nickname AS extra__writerName");
        sql.append("FROM article AS A");
        sql.append("INNER JOIN member AS M");
        sql.append("ON A.memberId = M.id");
        sql.append("WHERE A.id = ?", id);

        Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

        if (articleMap.isEmpty()) {
            return null;
        }

        return new Article(articleMap);
    }

    public void increaseHit(int id) {
        SecSql sql = new SecSql();

        sql.append("UPDATE article");
        sql.append("SET hit = hit + 1");
        sql.append("WHERE id = ?", id);

        DBUtil.update(Container.conn, sql);
    }

    public List<Article> allArticle(){
        SecSql sql = new SecSql();

        sql.append("SELECT A.id, A.title");
        sql.append(", M.nickname AS extra__writerName");
        sql.append("FROM article AS A");
        sql.append("INNER JOIN member AS M");
        sql.append("ON A.memberId = M.id");

        List<Article> articles = new ArrayList<>();

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.conn, sql);

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }
        System.out.println(articles);
        return articles;
    }

    public int addcomment(int memberId, int articleId, String comment) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO articlecomment");
        sql.append(" SET regDate = NOW()");
        sql.append(", updateDate = NOW()");
        sql.append(", memberId = ?", memberId);
        sql.append(", articleId = ?", articleId);
        sql.append(", `comment` = ?", comment);

        int id = DBUtil.insert(Container.conn, sql);
        System.out.println(id);
        return id;
    }
}
