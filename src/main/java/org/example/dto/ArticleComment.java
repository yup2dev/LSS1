package org.example.dto;

import java.util.Map;

import lombok.Data;

@Data
public class ArticleComment {
    public int id;
    public String regDate;
    public String updateDate;
    public int memberId;
    public int articleId;
    public String comment;
    public String extra__writerName;
    public ArticleComment(int id, int articleId, String comment){
        this.id = id;
        this.comment = comment;
        this.articleId = articleId;
    }

    public ArticleComment(Map<String, Object> articleCommentMap) {
        this.id = (int) articleCommentMap.get("id");
        this.regDate = (String) articleCommentMap.get("regDate");
        this.updateDate = (String) articleCommentMap.get("updateDate");
        this.memberId = (int) articleCommentMap.get("memberId");
        this.comment = (String) articleCommentMap.get("comment");
        this.articleId = (int) articleCommentMap.get(articleId);

        if (articleCommentMap.get("extra__writerName") != null) {
            this.extra__writerName = (String) articleCommentMap.get("extra__writerName");
        }
    }
}

