package it.ingsw.cinemates20_mobile;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.ingsw.cinemates20_mobile.presenters.SingUpPresenter;

public class EmailFromatTest{
    @Test //CE2
    public void correctEmailFormat_TestCorrectFormat(){
        assertTrue(SingUpPresenter.checkEmailCorrectFormat("luc.pastore@studenti.unina.it"));
    }

    @Test //CE1
    public void correctEmailFormat_TestNull(){
        assertFalse(SingUpPresenter.checkEmailCorrectFormat(null));
    }

    @Test //CE3
    public void correctEmailFormat_TestEmptyString(){
        assertFalse(SingUpPresenter.checkEmailCorrectFormat(""));
    }

    @Test //CE4
    public void correctEmailFormat_TestWithoutAt(){
        assertFalse(SingUpPresenter.checkEmailCorrectFormat("lucapastoregmail.com"));
    }
}
