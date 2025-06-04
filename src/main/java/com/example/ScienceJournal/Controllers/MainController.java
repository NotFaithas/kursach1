package com.example.ScienceJournal.Controllers;

import com.example.ScienceJournal.Entities.Article;
import com.example.ScienceJournal.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    ArticleService aService;

    @GetMapping
    public String main(@RequestParam(required = false) String name, Model model) {
        model.addAttribute("publishedArticles", aService.findPublishedArticles(name));
        return "mainPage";
    }
    @GetMapping("/View/{article}")
    @PreAuthorize("#article.published")
    public String view(@PathVariable Article article, Model model) {
        model.addAttribute("articleView", article);
        return "articleView";
    }

}
