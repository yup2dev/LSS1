package org.example.service;

import org.example.Container;
import org.example.dto.Article;


public class ArticleService {

    public void showArticleComment(int articleid){
        Container.articleRepository.showComment(articleid);
    }
    public int write(int memberId, String title, String body, int hit) {
        return Container.articleRepository.write(memberId, title, body, hit);
    }

    public int addcomment(int memberID, int articleID, String comment) {
        return Container.articleRepository.addcomment(memberID, articleID, comment);
    }

    public void increaseHit(int id) {
        Container.articleRepository.increaseHit(id);
    }

    public Article getArticleById(int id) {
        return Container.articleRepository.getArticleById(id);
    }

}
