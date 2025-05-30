package com.example.ScienceJournal.Services;

import com.example.ScienceJournal.Configs.UserDetailsService;
import com.example.ScienceJournal.Entities.Article;
import com.example.ScienceJournal.Entities.Tag;
import com.example.ScienceJournal.Repos.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService {
    @Autowired
    ArticleRepo aRepo;
    @Autowired
    UserDetailsService UDS;

    public List<Article> findByAuthor(String author) {return aRepo.findByAuthor(author);}

    public List<Article> findAll() {return aRepo.findAll();}

    public List<Article> findByTag(Tag tag) {return aRepo.findByTag(tag);}

    public void save(Article article)
    {
        article.setAuthor(UDS.getCurrentUser());
        article.setTag(Collections.singleton(Tag.Created));
        aRepo.save(article);
    }
}
