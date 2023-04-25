package org.example.service;

import org.example.dto.Article;
import org.example.repository.ArticleRepository;

import static org.example.Container.articleRepository;

public class ArticleService {

    public void showArticleComment(){
        articleRepository.showComment();
    }
    public int write(int memberId, String title, String body, int hit) {
        return articleRepository.write(memberId, title, body, hit);
    }

    public int addcomment(int memberID, int articleID, String comment) {
        return articleRepository.addcomment(memberID, articleID, comment);
    }

    public void increaseHit(int id) {
        articleRepository.increaseHit(id);
    }

    public Article getArticleById(int id) {
        return articleRepository.getArticleById(id);
    }

}
