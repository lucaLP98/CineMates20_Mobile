package it.ingsw.cinemates20_mobile.DAO;

import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.ConnectionRequestDAOlambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.FriendsDAOlambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.NotificationDAOlambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.ReviewDAOlambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.SharingMovieDAOlamda;
import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.UserDAOLambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.movieProvider.MovieDAO_TMDB;
import it.ingsw.cinemates20_mobile.DAO.interfaces.ConnectionRequestDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.FriendsDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.MovieDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.NotificationDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.ReviewDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.SharingMovieDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;

public abstract class DAOFactory {
    private enum DatabaseType{
        LAMBDA
    }

    private enum MovieDatabaseType{
        TMDB
    }

    private static final DatabaseType concreteDatabase = DatabaseType.LAMBDA;

    private static final MovieDatabaseType concreteMovieDatabase = MovieDatabaseType.TMDB;

    public static UserDAO getUserDao(){
        UserDAO concreteDAO;

        switch (concreteDatabase){
            case LAMBDA:
                concreteDAO = new UserDAOLambda();
                break;

            default: throw new RuntimeException();
        }

        return concreteDAO;
    }

    public static ReviewDAO getReviewDao(){
        ReviewDAO concreteDAO;

        switch (concreteDatabase){
            case LAMBDA:
                concreteDAO = new ReviewDAOlambda();
                break;

            default: throw new RuntimeException();
        }

        return concreteDAO;
    }

    public static ConnectionRequestDAO getConnectionRequestDAO(){
        ConnectionRequestDAO concreteDAO;

        switch (concreteDatabase){
            case LAMBDA:
                concreteDAO = new ConnectionRequestDAOlambda();
                break;

            default: throw new RuntimeException();
        }

        return concreteDAO;
    }

    public static FriendsDAO getFriendsDAO(){
        FriendsDAO concreteDAO;

        switch (concreteDatabase){
            case LAMBDA:
                concreteDAO = new FriendsDAOlambda();
                break;

            default: throw new RuntimeException();
        }

        return concreteDAO;
    }

    public static NotificationDAO getNotificationDAO(){
        NotificationDAO concreteDAO;

        switch (concreteDatabase){
            case LAMBDA:
                concreteDAO = new NotificationDAOlambda();
                break;

            default: throw new RuntimeException();
        }

        return concreteDAO;
    }

    public static SharingMovieDAO getSharingMovieDAO(){
        SharingMovieDAO concreteDAO;

        switch (concreteDatabase){
            case LAMBDA:
                concreteDAO = new SharingMovieDAOlamda();
                break;

            default: throw new RuntimeException();
        }

        return concreteDAO;
    }

    public static MovieDAO getMovieDAO(){
        MovieDAO concreteDAO;

        switch (concreteMovieDatabase){
            case TMDB:
                concreteDAO = new MovieDAO_TMDB();
                break;

            default: throw new RuntimeException();
        }

        return concreteDAO;
    }
}
