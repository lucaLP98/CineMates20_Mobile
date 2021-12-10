package it.ingsw.cinemates20_mobile.model.builder;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import it.ingsw.cinemates20_mobile.model.MovieFilter;

public class MovieFilterBuilder {
    private Integer genreID;
    private Integer directorID;
    private Integer actorID;
    private Integer year;
    private Integer runtime;

    public MovieFilterBuilder genreID(Integer genreID){
        this.genreID = genreID;
        return this;
    }

    public MovieFilterBuilder directorID(Integer directorID){
        this.directorID = directorID;
        return this;
    }

    public MovieFilterBuilder actorID(Integer actorID){
        this.actorID = actorID;
        return this;
    }

    public MovieFilterBuilder year(Integer year){
        this.year = year;
        return this;
    }

    public MovieFilterBuilder runtime(Integer runtime){
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
