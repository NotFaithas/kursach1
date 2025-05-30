package com.example.ScienceJournal.Controllers;

import com.example.ScienceJournal.Entities.Article;
import com.example.ScienceJournal.Entities.Role;
import com.example.ScienceJournal.Entities.Tag;
import com.example.ScienceJournal.Entities.User;
import com.example.ScienceJournal.Repos.ArticleRepo;
import com.example.ScienceJournal.Repos.UserRepo;
import com.example.ScienceJournal.Services.ArticleService;
import com.example.ScienceJournal.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService uService;

    @Autowired
    UserRepo uRepo;

    @Autowired
    ArticleService aService;

    @Autowired
    ArticleRepo aRepo;

    @GetMapping
    public String admin(Model model) {
        List<User> userList = uService.findAll();
        model.addAttribute("userList", userList);
        List<Article> articleList = aService.findByTag(Tag.Moderating);
        model.addAttribute("Moderating", articleList);
        return "adminPage";
    }

    @GetMapping("/edit/{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    @PostMapping("/edit")
    public String userSave(@RequestParam("id") User user,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username)
    {
        user.setUsername(username);

        Set<String> newRoles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (newRoles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        uRepo.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/View/{article}")
    @PreAuthorize("!#article.editable")
    public String view(@PathVariable Article article, Model model) {
        model.addAttribute("article", article);
        return "adminView";
    }
    @PostMapping("/Response")
    public String response(@RequestParam("id") Article article, @RequestParam String response) {
        article.getTag().clear();
        article.getTag().add(Tag.Response);
        article.setResponse(response);
        aRepo.save(article);
        return "redirect:/admin";
    }
    @PostMapping("/Publish")
    public String publish(@RequestParam("id") Article article) {
        article.getTag().clear();
        article.getTag().add(Tag.Published);
        article.setResponse(null);
        aRepo.save(article);
        return "redirect:/admin";
    }
}
