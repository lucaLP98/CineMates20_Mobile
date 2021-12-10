package it.ingsw.cinemates20_mobile.model;

public class MovieFilter{
    private final Integer genreID;
    private final Integer directorID;
    private final Integer actorID;
    private final Integer year;
    private final Integer runtime;

    public MovieFilter(Integer genreID, Integer directorID, Integer actorID, Integer year, Integer runtime){
        this.actorID = actorID;
        this.directorID = directorID;
        this.genreID = genreID;
        this.runtime = runtime;
        this.year = year;
    }


    public Integer getGenreID(){
        return genreID;
    }

    public Integer getDirectorID(){
        return directorID;
    }

    public Integer getActorID(){
        return actorID;
    }

    public Integer getYear(){
        return year;
    }

    public Integer getRuntime(){
        return runtime;
    }
}
