package it.ingsw.cinemates20_mobile.DAO;

import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.UserDAOLambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.MovieDAOtmdb;
import it.ingsw.cinemates20_mobile.DAO.interfaces.MovieDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;

public abstract class DAOFactory {
    private enum DatabaseType{
        LAMBDA,
        POSTGRES,
        ORACLE,
        MYSQL
    }

    private enum MovieProviderType{
        THEMOVIEDATABASE,
        OPENMOVIEDATABASE
    }

    private static final DatabaseType concreteDatabase = DatabaseType.LAMBDA;

    private static final MovieProviderType concreteMovieProvider = MovieProviderType.THEMOVIEDATABASE;

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

    public static MovieDAO getMovieDao(){
        MovieDAO concreteDAO;

        switch (concreteMovieProvider){
            case THEMOVIEDATABASE:
                concreteDAO = new MovieDAOtmdb();
                break;

            default: throw new RuntimeException();
        }

        return concreteDAO;
    }
}
