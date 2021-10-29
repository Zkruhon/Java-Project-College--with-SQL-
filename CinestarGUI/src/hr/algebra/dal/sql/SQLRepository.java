/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.Genre;
import hr.algebra.model.Person;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author zvoni
 */
public class SQLRepository implements Repository {
    
    private static final String ID_MOVIE = "IDFilm";
    private static final String TITLE = "Naziv";
    private static final String DESCRIPTION = "Opis";
    private static final String RUN_TIME = "Trajanje";
    private static final String PICTURE_PATH = "Slika";
    
    //movie
    private static final String CREATE_MOVIES = "{ CALL createMovie (?, ?, ?, ?, ?) }";
    private static final String CREATE_MOVIE = "{ CALL createOneMovie (?,?,?,?) }";
    private static final String UPDATE_MOVIE = " { CALL updateMovie (?, ?, ?, ?) }";
    private static final String DELETE_MOVIE = " { CALL deleteMovie (?) }";
    private static final String DELETE_MOVIES = " { CALL deleteMovies }";
    private static final String SELECT_MOVIE = " { CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = " { CALL selectMovies }";
    
    //director
    private static final String CREATE_DIRECTORS = " { CALL createDirector (?, ?, ?, ?) }";
    private static final String SELECT_DIRECTORS = " { CALL selectDirectors }";
    
    //actor
    private static final String SELECT_ACTORS = " { CALL selectActors }";
    
    //genre
    private static final String CREATE_GENRES = " { CALL createGenre (?, ?) }";
    private static final String SELECT_GENRES = " { CALL selectGenres }";
    
    //person
    private static final String CREATE_MOVIE_DIRECTOR = " { CALL createMovieDjelatnik (?, ?, ?) }";
    private static final String DELETE_FILM_D = " { CALL deleteMovieDjelatnik }";
    
    //mix
    private static final String CREATE_MOVIE_GENRE = "{ CALL createMovieGenre (?,?,?) }";
    private static final String DELETE_MOVIE_GENRE = " { CALL deleteMovieGenre }";
    private static final String DELETE_PERSON = " { CALL deletePerson (?) }";
    private static final String CREATE_ACTOR_FOR_SPECIFIC_MOVIE = " { CALL createGenreToSpecificMovie (?, ?) }";
    private static final String CREATE_GENRE_FOR_SPECIFIC_MOVIE = " { CALL createGenreToSpecificMovie (?, ?) }";

    @Override
    public void createMovie(Movie film) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
                
                stmt.setString(1, film.getTitle());
                stmt.setString(2, film.getDescription());
                stmt.setInt(3, film.getRunTime());
                stmt.setString(4, film.getPicturePath());
                
                stmt.executeUpdate();
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIES)) {
            
            for (Movie film : movies) {
                stmt.setString(1, film.getTitle());
                stmt.setString(2, film.getDescription());
                stmt.setInt(3, film.getRunTime());
                stmt.setString(4, film.getPicturePath());
                stmt.registerOutParameter(5, Types.INTEGER);
                
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateMovie(int id, Movie data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE))  {
            
                stmt.setString(1, data.getTitle());
                stmt.setString(2, data.getDescription());
                stmt.setInt(3, data.getRunTime());
                stmt.setInt(4, id);
                
                stmt.executeUpdate();
            
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    return Optional.of(new Movie (
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            rs.getString(DESCRIPTION),
                            rs.getInt(RUN_TIME),
                            rs.getString(PICTURE_PATH)));
                }
                
            }
            
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getInt(RUN_TIME),
                        rs.getString(PICTURE_PATH)));
            }
        }
        return movies;
    }

    @Override
    public void deleteMovies() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIES)) {
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void createDirectors(List<Director> directors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTORS)) {
            
            for (Director director : directors) {
                
                stmt.setString(1, director.getFirstName());
                stmt.setString(2, director.getLastName());
                stmt.setInt(3, 1);
                stmt.registerOutParameter(4, Types.INTEGER);
                
                stmt.executeUpdate();
            } 
        }
    }

    private static final String ID_PERSON= "IDOsoba";
    private static final String TYPE_ID = "TipID";
    private static final String FIRST_NAME= "Ime";
    private static final String LAST_NAME= "Prezime";
    
    @Override
    public List<Director> selectDirectors() throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                directors.add(new Director (
                        rs.getInt(ID_PERSON),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME),
                        rs.getInt(TYPE_ID)));
            }
        }
        return directors;
    }

    @Override
    public void createActors(List<Actor> actors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTORS)) {

            for (Actor actor : actors) {
                stmt.setString(1, actor.getFirstName());
                stmt.setString(2, actor.getLastName());
                stmt.setInt(3, 2);
                stmt.registerOutParameter(4, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }
    
    @Override
    public List<Actor> selectActors() throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTORS);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID_PERSON),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME),
                        rs.getInt(TYPE_ID)));
            }
        }
        return actors;
    }

    @Override
    public void createGenres(List<Genre> genres) throws Exception {
    DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_GENRES)) {
            
            for (Genre genre : genres) {
                stmt.setString(1, genre.getName());
                stmt.registerOutParameter(2, Types.INTEGER);
                
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public List<Genre> selectGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_GENRES);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                genres.add(new Genre(
                        rs.getInt("IDZanr"),
                        rs.getString("Naziv")));
            }
        }
        return genres;
    }

    @Override
    public void createMoviesDirectors(List<Movie> movies, List<Director> directors, List<Director> directorsSQL, List<Movie> moviesSQL) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_DIRECTOR)) {
            
            Director dir = new Director();
            Movie movie = new Movie();
            
            for (int i = 0; i < movies.size(); i++) {
                for (int j = 0; j < directors.size(); j++) {
                    if (movies.get(i).getDirector().contains(directors.get(j))) {
                        for (Director director : directorsSQL) {
                            for (Movie film : moviesSQL) {
                                if (directors.get(j).firstName.equals(director.firstName) && directors.get(j).lastName.equals(director.lastName) && movies.get(i).getTitle().equals(film.getTitle())) {
                                  
                                    dir = director;
                                    movie = film;

                                    stmt.setInt(1, movie.getId());
                                    stmt.setInt(2, dir.getId());
                                    stmt.registerOutParameter(3, Types.INTEGER);

                                    stmt.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<Director> selectDirectorsOfMovie(Movie movie) throws Exception {
        try {
            DataSource dataSource = DataSourceSingleton.getInstance();
            Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("select distinct Osoba.Ime, Osoba.Prezime from FilmDjelatnik inner join Osoba on FilmDjelatnik.OsobaID = IDOsoba where FilmId =" + movie.getId() + " and Osoba.TipID=1");
            ResultSet rs = statement.executeQuery();
            List<Director> directors = new ArrayList<>();
            
            while (rs.next()) {
                directors.add(new Director(rs.getString(FIRST_NAME), rs.getString(LAST_NAME)));
            }
            return directors;
        } catch (SQLException sqlException) {
            System.out.println("No Kendu");
            return null;
        }
    }

    @Override
    public void createMovieActors(List<Movie> movies, List<Actor> actors, List<Actor> actorsSQL, List<Movie> moviesSQL) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_DIRECTOR)) {
            for (int i = 0; i < movies.size(); i++) {

                for (int j = 0; j < actors.size(); j++) {

                    if (movies.get(i).getActors().contains(actors.get(j))) {
                        for (Actor actor : actorsSQL) {
                            if (actors.get(j).firstName.equals(actor.firstName)) {
                                for (Movie film : moviesSQL) {
                                    String[] str = film.getTitle().split(" ");
                                    if (movies.get(i).getTitle().contains(str[0])) {
                                        
                                        stmt.setInt(1, film.getId());
                                        stmt.setInt(2, actor.getId());
                                        stmt.registerOutParameter(3, Types.INTEGER);

                                        stmt.executeUpdate();
                                    }

                                }

                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<Actor> selectActorsOfMovie(Movie movie) throws Exception {
        try {
            DataSource dataSource = DataSourceSingleton.getInstance();
            Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("select distinct Osoba.Ime, Osoba.Prezime from FilmDjelatnik inner join Osoba on FilmDjelatnik.OsobaID = IDOsoba where FilmId =" + movie.getId() + " and Osoba.TipID=2");
            ResultSet result = statement.executeQuery();
            List<Actor> actors = new ArrayList<>();

            while (result.next()) {

                actors.add(new Actor(result.getString(FIRST_NAME), result.getString(LAST_NAME)));
            }
            return actors;
            
        } catch (SQLException sqlException) {
            System.out.println("No Kenndu");
            return null;
        }
    }

    @Override
    public void deleteFilmD() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_FILM_D)) {

                    stmt.executeUpdate();
        }
    }

    @Override
    public void createMovieGenre(List<Movie> movies, List<Genre> genres,
            List<Movie> moviesFromSQL, List<Genre> genreFromSQL) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_GENRE)) {
            Genre gen = new Genre();
            Movie movie = new Movie();
            for (int i = 0; i < movies.size(); i++) {
                for (int j = 0; j < genres.size(); j++) {
                    if (movies.get(i).getGenre().contains(genres.get(j))) {
                        for (Genre genre : genreFromSQL) {
                            for (Movie film : moviesFromSQL) {
                                if (genres.get(j).getName().equals(genre.getName()) && movies.get(i).getTitle().equals(film.getTitle())) {
                                    gen = genre;
                                    movie = film;

                                    stmt.setInt(1, movie.getId());
                                    stmt.setInt(2, gen.getId());
                                    stmt.registerOutParameter(3, Types.INTEGER);

                                    stmt.executeUpdate();
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    @Override
    public List<Genre> selectGenreOfMovie(Movie movie) throws Exception {
           
        try {
            DataSource dataSource = DataSourceSingleton.getInstance();
            Connection con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("select distinct Naziv from ZanrFilm inner join Zanr on Zanr.IDZanr=ZanrID where FilmId= " + movie.getId());
            ResultSet result = statement.executeQuery();
            List<Genre> genres = new ArrayList<>();

            while (result.next()) {
                genres.add(new Genre(result.getString(TITLE)));
            }
        return genres;
        
        } catch (SQLException sqlException) {
            System.out.println("No Kenndu");
            return null;
        }
    }

    @Override
    public void deleteFilmGenre() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(DELETE_MOVIE_GENRE)) {

            stmt.executeUpdate();
        }
    }

    @Override
    public void createNewActorMovie(Movie film, Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(CREATE_MOVIE_DIRECTOR)) {
            
            stmt.setInt(1, film.getId());
            stmt.setInt(2, actor.getId());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
        }
    }

    @Override
    public void createNewDirectorMovie(Movie film, Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_DIRECTOR)) {
            
            stmt.setInt(1, film.getId());
            stmt.setInt(2, director.getId());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
        }
    }

    @Override
    public void createNewGenreMovie(Movie film, Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_GENRE)) {
           
            stmt.setInt(1, film.getId());
            stmt.setInt(2, genre.getId());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
        }
    }

    @Override
    public void createActor(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTORS)) {

            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());
            stmt.setInt(3, 2);
            stmt.registerOutParameter(4, Types.INTEGER);

            stmt.executeUpdate();
        }
    }

    @Override
    public void createDirector(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTORS)) {

            stmt.setString(1, director.getFirstName());
            stmt.setString(2, director.getLastName());
            stmt.setInt(3, 1);
            stmt.registerOutParameter(4, Types.INTEGER);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePerson(Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_PERSON)) {

            stmt.setInt(1, person.id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void createActorForSpecificMovie(Movie movie, List<Actor> actors, List<Actor> actorsSQL) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACTOR_FOR_SPECIFIC_MOVIE)) {
            for (Actor actor : actors) {
                for (Actor actor1 : actorsSQL) {
                    if (actor.firstName == null ? actor1.firstName == null : actor.firstName.equals(actor1.firstName)) {
                       
                        stmt.setInt(1, movie.getId());
                        stmt.setInt(2, actor1.getId());
                        
                        stmt.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public void createDirectorForSpecificMovie(Movie movie, List<Director> directors, List<Director> directorsSQL) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACTOR_FOR_SPECIFIC_MOVIE)) {
            for (Director director : directors) {
                for (Director director1 : directorsSQL) {
                    if (director.firstName == null ? director1.firstName == null : director.firstName.equals(director1.firstName)) {
                        stmt.setInt(1, movie.getId());
                        stmt.setInt(2, director1.getId());
                        
                        stmt.executeUpdate();
                    }
                }
            }
        }
    }
    
    @Override
    public void createGenreForSpecificMovie(Movie movie, List<Genre> genres, List<Genre> genresSQL) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_GENRE_FOR_SPECIFIC_MOVIE)) {
            for (Genre genre : genres) {
                for (Genre genre1 : genresSQL) {
                    if (genre.getName().equals(genre1)) {
                        
                        stmt.setInt(1, movie.getId());
                        stmt.setInt(2, genre1.getId());
                        
                        stmt.executeUpdate();
                    }
                }
            }
        }
    }
}
