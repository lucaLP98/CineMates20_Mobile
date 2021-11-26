package it.ingsw.cinemates20_mobile.model;

import android.net.Uri;

import java.util.List;

import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.ProductionCountry;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;

public class Movie {
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

    public Movie(int movieID, String title, List<PersonCrew> crew, String years, int duration, String plot, List<ProductionCountry> productionCountry, List<Genre> genres, List<PersonCast> cast, Uri posterUri){
        this.movieID = movieID;
        this.title = title;
        this.crew = crew;
        this.years = years;
        this.duration = duration;
        this.plot = plot;
        this.productionCountry = productionCountry;
        this.cast = cast;
        this.genres = genres;
        this.posterUri = posterUri;
    }

    public String getTitle(){return title;}

    public String getYears(){return years;}

    public int getDuration(){return duration;}

    public String getPlot(){return plot;}

    public List<ProductionCountry> getProductionCountry(){return productionCountry;}

    public List<PersonCrew> getCrew(){return crew;}

    public List<PersonCast> getCast(){return cast;}

    public List<Genre> getGenres(){ return genres;}

    public Uri getPosterUri(){return posterUri;}
}
