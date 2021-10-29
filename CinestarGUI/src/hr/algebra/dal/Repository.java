/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.Genre;
import hr.algebra.model.Person;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author zvoni
 */
public interface Repository {
    
    //movie
    void createMovie(Movie film) throws Exception;
    void createMovies(List<Movie> movies) throws Exception;
    void updateMovie(int id, Movie data) throws Exception;
    void deleteMovie(int id) throws Exception;
    Optional<Movie> selectMovie(int id) throws Exception;
    List<Movie> selectMovies() throws Exception;
    void deleteMovies() throws Exception;
    
    //director
    void createDirector(Director director) throws Exception;
    void createDirectors(List<Director> directors) throws Exception;
    List<Director> selectDirectors() throws Exception;
    
    //actor
    void createActor(Actor actor) throws Exception;
    void createActors(List<Actor> actors) throws Exception;
    List<Actor> selectActors() throws Exception;
    
    //person
    void deletePerson(Person person) throws Exception;
    
    //genre
    void createGenres(List<Genre> genres) throws Exception;
    List<Genre> selectGenres() throws Exception;
    
    //mix
    void createMoviesDirectors(List<Movie> movies, List<Director> directors, List<Director> directorsSQL, List<Movie> moviesSQL) throws Exception;
    
    List<Director> selectDirectorsOfMovie(Movie movie) throws Exception;
    
    void createMovieActors(List<Movie> movies, List<Actor> actors, List<Actor> actorsSQL, List<Movie> moviesSQL) throws Exception;
    
    List<Actor> selectActorsOfMovie(Movie movie) throws Exception;
 
    void deleteFilmD() throws Exception;
    
    void createMovieGenre(List<Movie> movies, List<Genre> genres, List<Movie> moviesFromSQL, List<Genre> genreFromSQL) throws Exception;

    List<Genre> selectGenreOfMovie(Movie movie) throws Exception;
    
    void deleteFilmGenre() throws Exception;
    
    void createNewActorMovie(Movie film, Actor actor) throws Exception;
    
    void createNewDirectorMovie(Movie film, Director director) throws Exception;
    
    void createNewGenreMovie(Movie film, Genre genre) throws Exception;
    

    
    void createActorForSpecificMovie(Movie movie, List<Actor> actors, List<Actor> actorsSQL) throws Exception;
    
    void createDirectorForSpecificMovie(Movie movie, List<Director> directors, List<Director> directorsSQL ) throws Exception;
    
    void createGenreForSpecificMovie(Movie movie, List<Genre> genres, List<Genre> genresSQL) throws Exception;
}
