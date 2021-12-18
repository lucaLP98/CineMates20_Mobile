package it.ingsw.cinemates20_mobile;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.ingsw.cinemates20_mobile.presenters.EditReviewPresenter;

public class ReviewParameterTest {
    @Test // (CEV1 , CEB1)
    public void checkReviewParameter_TestVoteEmptyBodyEmpty(){
        assertFalse(EditReviewPresenter.checkReviewParameter("", ""));
    }

    @Test // (CEV2 , CEB1)
    public void checkReviewParameter_TestVoteNegativeBodyEmpty(){
        assertFalse(EditReviewPresenter.checkReviewParameter("-5", ""));
    }

    @Test // (CEV3 , CEB1)
    public void checkReviewParameter_TestVoteGreaterThan100BodyEmpty(){
        assertFalse(EditReviewPresenter.checkReviewParameter("120", ""));
    }

    @Test // (CEV4 , CEB1)
    public void checkReviewParameter_TestVoteCorrectBodyEmpty(){
        assertFalse(EditReviewPresenter.checkReviewParameter("70", ""));
    }

    @Test // (CEV1 , CEB2)
    public void checkReviewParameter_TestVoteEmptyBodyNotEmpty(){
        assertFalse(EditReviewPresenter.checkReviewParameter("", "Recensione di test"));
    }

    @Test // (CEV2 , CEB2)
    public void checkReviewParameter_TestVoteNegativeBodyNotEmpty(){
        assertFalse(EditReviewPresenter.checkReviewParameter("-10", "Recensione di test"));
    }

    @Test // (CEV3 , CEB2)
    public void checkReviewParameter_TestVoteGreaterThan100BodyNotEmpty(){
        assertFalse(EditReviewPresenter.checkReviewParameter("105", "Recensione di test"));
    }

    @Test // (CEV2 , CEB2)
    public void checkReviewParameter_TestVoteCorrectBodyNotEmpty(){
        assertTrue(EditReviewPresenter.checkReviewParameter("60", "Recensione di test"));
    }
}
