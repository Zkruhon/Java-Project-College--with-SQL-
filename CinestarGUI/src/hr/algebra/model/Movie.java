/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author zvoni
 */

public class Movie {
    
    public static final DateTimeFormatter DATE_LOCAL_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter DATE_DB_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    
    private int id;
    private String title;
    private LocalDateTime pubDate;
    
    private String description;
    private String origNaziv;
    private List<Director> director;
    private int runTime;
    private List<Genre> genre;
    private int year;
    private int ratings;
    private String picturePath;
    private List<Actor> actors;

    public Movie() {
    }

    public Movie(String title, LocalDateTime pubDate, String description, String origNaziv, List<Director> director, int runTime, List<Genre> genre, int year, int ratings, String picturePath, List<Actor> actors) {
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
        this.origNaziv = origNaziv;
        this.director = director;
        this.runTime = runTime;
        this.genre = genre;
        this.year = year;
        this.ratings = ratings;
        this.picturePath = picturePath;
        this.actors = actors;
    }

    public Movie(int id, String title, String description, int runTime, String picturePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.runTime = runTime;
        this.picturePath = picturePath;
    }

    public Movie(String title, String description, int runTime, String picturePath) {
        this.title = title;
        this.description = description;
        this.runTime = runTime;
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return title + ", description=" + description.substring(description.indexOf(">") + 1) + ", runTime=" + runTime + ", picturePath=" + picturePath + '}';
    }

    public static DateTimeFormatter getDATE_LOCAL_FORMATTER() {
        return DATE_LOCAL_FORMATTER;
    }

    public static DateTimeFormatter getDATE_DB_FORMATTER() {
        return DATE_DB_FORMATTER;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public String getOrigNaziv() {
        return origNaziv;
    }

    public List<Director> getDirector() {
        return director;
    }

    public int getRunTime() {
        return runTime;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public int getRatings() {
        return ratings;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrigNaziv(String origNaziv) {
        this.origNaziv = origNaziv;
    }

    public void setDirector(List<Director> director) {
        this.director = director;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    
}
