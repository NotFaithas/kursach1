package com.example.ScienceJournal.Controllers;

import com.example.ScienceJournal.Entities.Article;
import com.example.ScienceJournal.Entities.Tag;
import com.example.ScienceJournal.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    ArticleService aService;

    @GetMapping
    public String main(Model model) {
        List<Article> articleList = aService.findByTag(Tag.Published);
        model.addAttribute("publishedArticles", articleList);
        model.addAttribute(new Article());
        return "mainPage";
    }
    @GetMapping("/View/{article}")
    @PreAuthorize("#article.published")
    public String view(@PathVariable Article article, Model model) {
        model.addAttribute("articleView", article);
        return "articleView";
    }
}
