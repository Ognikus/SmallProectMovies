package com.example.proectlaba;

public class Movies {
    Integer Id;
    String name;
    Integer year;
    String genre;
    Integer score;

    public Movies(String id, String name, String year, String genre, String score) {
        this.Id = (id != null && !id.trim().isEmpty()) ? Integer.valueOf(id) : null;
        this.name = name;
        this.year = (year != null && !year.trim().isEmpty()) ? Integer.valueOf(year) : null;
        this.genre = genre;
        this.score = (score != null && !score.trim().isEmpty()) ? Integer.valueOf(score) : null;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
