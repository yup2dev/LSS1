package org.example.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Question {
    public int id;
    public String regDate;
    public String updateDate;
    public int memberId;
    public String title;
    public String body;
    public int hit;
    public String extra__writerName;

    public Question (int id, String title){
        this.id = id;
        this.title = title;
    }

    public Question(Map<String, Object> questionMap) {
        this.id = (int) questionMap.get("id");
        this.regDate = (String) questionMap.get("regDate");
        this.updateDate = (String) questionMap.get("updateDate");
        this.memberId = (int) questionMap.get("memberId");
        this.title = (String) questionMap.get("title");
        this.body = (String) questionMap.get("body");
        this.hit = (int) questionMap.get("hit");

        if(questionMap.get("extra__writerName") != null) {
            this.extra__writerName = (String)questionMap.get("extra__writerName");
        }
    }

}
