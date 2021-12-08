package it.ingsw.cinemates20_mobile.model;

public class MovieFilter {
    private int genreID;
    private int directorID;
    private int actorID;
    private int year;
    private int runtime;

    public MovieFilter(int genreID, int directorID, int actorID, int year, int runtime){
        this.actorID = actorID;
        this.directorID = directorID;
        this.genreID = genreID;
        this.runtime = runtime;
        this.year = year;
    }

    public int getGenreID(){
        return genreID;
    }

    public int getDirectorID(){
        return directorID;
    }

    public int getActorID(){
        return actorID;
    }

    public int getYear(){
        return year;
    }

    public int getRuntime(){
        return runtime;
    }
}
