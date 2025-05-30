package com.example.ScienceJournal.Repos;

import com.example.ScienceJournal.Entities.Article;
import com.example.ScienceJournal.Entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    List<Article> findByAuthor(String author);
    List<Article> findByTag(Tag tag);
}
