package it.ingsw.cinemates20_mobile.model.builder;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import it.ingsw.cinemates20_mobile.model.MovieFilter;

public class MovieFilterBuilder {
    private int genreID;
    private int directorID;
    private int actorID;
    private int year;
    private int runtime;

    public MovieFilterBuilder genreID(int genreID){
        this.genreID = genreID;
        return this;
    }

    public MovieFilterBuilder directorID(int directorID){
        this.directorID = directorID;
        return this;
    }

    public MovieFilterBuilder actorID(int actorID){
        this.actorID = actorID;
        return this;
    }

    public MovieFilterBuilder year(int year){
        this.year = year;
        return this;
    }

    public MovieFilterBuilder runtime(int runtime){
        this.runtime = runtime;
        return this;
    }

    public MovieFilter build(){
        return new MovieFilter(genreID, directorID, actorID, year, runtime);
    }

    @NonNull
    @Contract(" -> new")
    public static MovieFilterBuilder getBuilder(){
        return new MovieFilterBuilder();
    }
}
