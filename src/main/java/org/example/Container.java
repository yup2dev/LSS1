package org.example;

import org.example.controller.*;
import org.example.repository.ArticleRepository;
import org.example.repository.MemberRepository;
import org.example.repository.QuestionRepository;
import org.example.service.ArticleService;
import org.example.service.MemberService;
import org.example.service.QuestionService;
import org.example.session.Session;

import java.sql.Connection;
import java.util.Scanner;

public class Container {

    public static ArticleRepository articleRepository;
    public static MemberRepository memberRepository;
    public static QuestionRepository questionRepository;

    public static ArticleService articleService;
    public static MemberService memberService;
    public static QuestionService questionService;

    public static ArticleController articleController;
    public static MemberController memberController;
    public static MoveController moveController;
    public static ScheduleController scheduleController;
    public static QuestionController questionController;
    public static ProfileController profileController;
    public static Scanner scanner;
    public static Session session;
    public static Connection conn;
    public static Rq rq;


    public static void init() {
        articleRepository = new ArticleRepository();
        memberRepository = new MemberRepository();
        questionRepository = new QuestionRepository();
        articleService = new ArticleService();
        memberService = new MemberService();
        questionService = new QuestionService();

        articleController = new ArticleController();
        memberController = new MemberController();
        moveController = new MoveController();
        questionController = new QuestionController();
        scheduleController = new ScheduleController();
        profileController = new ProfileController();


        scanner = new Scanner(System.in);
        session = new Session();
    }
}
