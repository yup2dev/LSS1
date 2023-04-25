package org.example.service;

import org.example.dto.Article;
import org.example.dto.Question;

import static org.example.Container.*;

public class QuestionService {
    public void showQuestionComment(int questionid) {
        questionRepository.showComment(questionid);
    }

    public int write(int memberId, String title, String body, int hit) {
        return questionRepository.write(memberId, title, body, hit);
    }

    public int addcomment(int memberID, int questionId, String comment) {
        return questionRepository.addcomment(memberID, questionId, comment);
    }

    public void increaseHit(int id) {
        questionRepository.increaseHit(id);
    }

    public Question getQuestionById(int id) {
        return questionRepository.getQuestionById(id);
    }
}