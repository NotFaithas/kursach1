package com.example.ScienceJournal.Controllers;

import com.example.ScienceJournal.Configs.UserDetailsService;
import com.example.ScienceJournal.Entities.Article;
import com.example.ScienceJournal.Entities.Tag;
import com.example.ScienceJournal.Repos.ArticleRepo;
import com.example.ScienceJournal.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Article")
public class ArticleController {
    @Autowired
    ArticleService aService;
    @Autowired
    UserDetailsService UDS;
    @Autowired
    ArticleRepo aRepo;

    @GetMapping
    public String article(Model model) {
        List<Article> userArticles = aService.findByAuthor(UDS.getCurrentUser());
        model.addAttribute("userArticles", userArticles);
        return "userArticlesPage";
    }

    @GetMapping("/Creating")
    public String creating() {
        return "createArticlePage";
    }
    @PostMapping("/Creating/Create")
    public String create(@RequestParam String name, @RequestParam String text) {
        aService.save(new Article(name, text));
        return "redirect:/Article";
    }

    @GetMapping("/Edit/{article}")
    @PreAuthorize("#article.author == authentication.getName() and #article.editable")
    public String edit(@PathVariable Article article, Model model) {
        model.addAttribute("articleEdit", article);
        return "articleEdit";
    }
    @PostMapping("/Edit")
    public String edit(@RequestParam("id") Article article,
                       @RequestParam String name,
                       @RequestParam String text)
    {
        article.setName(name);
        article.setText(text);
        aRepo.save(article);
        return "redirect:/Article";
    }
    @PostMapping("/Edit/Request")
    @PreAuthorize("#article.author == authentication.getName()")
    public String request(@RequestParam("id") Article article)
    {
        article.getTag().clear();
        article.getTag().add(Tag.Moderating);
        aRepo.save(article);
        return "redirect:/Article";
    }
    @GetMapping("/View/{article}")
    @PreAuthorize("#article.author == authentication.getName() or #article.published")
    public String view(@PathVariable Article article, Model model) {
        model.addAttribute("articleView", article);
        return "articleView";
    }
    @PostMapping("/Delete")
    @PreAuthorize("#article.author == authentication.getName()")
    public String delete(@RequestParam("id") Article article) {
        aRepo.deleteById(article.getId());
        return "redirect:/Article";
    }
}
