package org.example.service;

import org.example.Container;
import org.example.dto.Question;


public class QuestionService {
    public void showQuestionComment(int questionid) {
        Container.questionRepository.showComment(questionid);
    }

    public int qwrite(int memberId, String title, String body) {
        return Container.questionRepository.qwrite(memberId, title, body);
    }

    public int addcomment(int memberID, int questionId, String comment) {
        return Container.questionRepository.addcomment(memberID, questionId, comment);
    }

    public Question getQuestionById(int id) {
        return Container.questionRepository.getQuestionById(id);
    }
}