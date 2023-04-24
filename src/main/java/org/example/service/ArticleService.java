package org.example.service;

import org.example.dto.Article;

import static org.example.Container.articleRepository;

public class ArticleService {
    public int write(int memberId, String title, String body, int hit) {
        return articleRepository.write(memberId, title, body, hit);
    }

    public void increaseHit(int id) {
        articleRepository.increaseHit(id);
    }

    public Article getArticleById(int id) {
        return articleRepository.getArticleById(id);
    }

}
