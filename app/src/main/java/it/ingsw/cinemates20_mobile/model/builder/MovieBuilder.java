package it.ingsw.cinemates20_mobile.model.builder;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.ProductionCountry;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import it.ingsw.cinemates20_mobile.model.Movie;

public class MovieBuilder {
    private int movieID;

    private String title;
    private String years;
    private int duration;
    private String plot;
    private List<ProductionCountry> productionCountry;
    private List<PersonCrew> crew;
    private List<Genre> genres;
    private List<PersonCast> cast;
    private Uri posterUri;

    private MovieBuilder(int movieID){
        this.movieID = movieID;
    }

    public MovieBuilder title(String title){
        this.title = title;
        return this;
    }

    public MovieBuilder crew(List<PersonCrew> crew){
        this.crew = crew;
        return this;
    }

    public MovieBuilder years(String years){
        this.years = years;
        return this;
    }

    public MovieBuilder duration(int duration){
        this.duration = duration;
        return this;
    }

    public MovieBuilder plot(String plot){
        this.plot = plot;
        return this;
    }

    public MovieBuilder productionCountru(List<ProductionCountry> productionCountry){
        this.productionCountry = productionCountry;
        return this;
    }

    public MovieBuilder genres(List<Genre> genres){
        this.genres = genres;
        return this;
    }

    public MovieBuilder cast(List<PersonCast> cast){
        this.cast = cast;
        return this;
    }

    public MovieBuilder poster(Uri posterUri){
        this.posterUri = posterUri;
        return this;
    }

    public Movie build(){
        return new Movie(movieID, title, crew, years, duration, plot, productionCountry, genres, cast, posterUri);
    }

    public static MovieBuilder getBuilder(int movieID){
        return new MovieBuilder(movieID);
    }
}
