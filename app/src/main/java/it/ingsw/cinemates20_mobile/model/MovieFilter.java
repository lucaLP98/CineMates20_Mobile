package it.ingsw.cinemates20_mobile.model;

public class MovieFilter {
    private final int genreID;
    private final int directorID;
    private final int actorID;
    private final int year;
    private final int runtime;
    private final String movieTitle;

    public MovieFilter(String movieTitle, int genreID, int directorID, int actorID, int year, int runtime){
        this.actorID = actorID;
        this.directorID = directorID;
        this.genreID = genreID;
        this.runtime = runtime;
        this.year = year;
        this.movieTitle = movieTitle;
    }

    public String getTitle(){
        return movieTitle;
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
