package com.example.ScienceJournal.Entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="Articles")
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = false, nullable = false)
    private String name;
    @Column(unique = false, nullable = true, columnDefinition = "TEXT", length=20000)
    private String text;
    @Column
    private String author;
    @ElementCollection(targetClass = Tag.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "article_tag", joinColumns = @JoinColumn(name = "article_id"))
    @Enumerated(EnumType.STRING)
    private Set<Tag> tag;
    @Column(unique = false, nullable = true)
    private String response;

    public Article() {}

    public Article(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Tag> getTag() {
        return tag;
    }

    public void setTag(Set<Tag> tag) {
        this.tag = tag;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isEditable() {
        return !this.tag.contains(Tag.Moderating) && !this.tag.contains(Tag.Published);
    }
    public boolean isPublished() {
        return this.tag.contains(Tag.Published);
    }
    public boolean isResponsed() {
        return this.response != null && !this.response.isEmpty();
    }
}
