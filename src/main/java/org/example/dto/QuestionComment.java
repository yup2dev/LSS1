package org.example.dto;

import java.util.Map;

import lombok.Data;

@Data
public class QuestionComment {
    public int id;
    public String regDate;
    public String updateDate;
    public int memberId;
    public int questionId;
    public String comment;
    public String extra__writerName;
    public QuestionComment(int id, int memberId, int questionId, String comment){
        this.id = id;
        this.comment = comment;
        this.memberId = memberId;
        this.questionId = questionId;
    }

    public QuestionComment(Map<String, Object> questionCommentMap) {
        this.id = (int) questionCommentMap.get("id");
        this.regDate = (String) questionCommentMap.get("regDate");
        this.updateDate = (String) questionCommentMap.get("updateDate");
        this.memberId = (int) questionCommentMap.get("memberId");
        this.comment = (String) questionCommentMap.get("comment");
        this.questionId = (int) questionCommentMap.get(questionId);

        if (questionCommentMap.get("extra__writerName") != null) {
            this.extra__writerName = (String) questionCommentMap.get("extra__writerName");
        }
    }
}

