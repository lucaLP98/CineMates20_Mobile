package it.ingsw.cinemates20_mobile.presenters.activities;

public class HomePresenter {
    private final boolean authentichedUser;

    public HomePresenter(boolean authentichedValue){
        authentichedUser = authentichedValue;
    }

    public boolean getUserAuthenticationStatus(){ return authentichedUser; }

}
