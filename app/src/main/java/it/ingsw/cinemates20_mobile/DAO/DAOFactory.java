package it.ingsw.cinemates20_mobile.DAO;

import it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda.UserDAOLambda;
import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;

public abstract class DAOFactory {
    private enum DatabaseType{
        LAMBDA,
        POSTGRES,
        ORACLE,
        MYSQL
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
}
