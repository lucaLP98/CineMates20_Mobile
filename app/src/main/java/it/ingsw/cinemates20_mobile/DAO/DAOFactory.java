package it.ingsw.cinemates20_mobile.DAO;

import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.ConnectionRequestDAOlambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.FriendsDAOlambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.ReviewDAOlambda;
import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.UserDAOLambda;
import it.ingsw.cinemates20_mobile.DAO.interfaces.ConnectionRequestDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.FriendsDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.ReviewDAO;
import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;

public abstract class DAOFactory {
    private enum DatabaseType{
        LAMBDA
    }

    private static final DatabaseType concreteDatabase = DatabaseType.LAMBDA;

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
}
