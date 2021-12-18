package it.ingsw.cinemates20_mobile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import it.ingsw.cinemates20_mobile.DAO.concrete.movieProvider.MovieDAO_TMDB;
import it.ingsw.cinemates20_mobile.model.MovieFilter;
import it.ingsw.cinemates20_mobile.model.builder.MovieFilterBuilder;

public class BuildFilterSearchUrlTest {
    private MovieDAO_TMDB dao = new MovieDAO_TMDB();;
    private String url = "test";

    @Test
    public void buildFilterUrl_1_2_3_6_10_14_18_22(){
        MovieFilter filter = MovieFilterBuilder.getBuilder().actorID(23).build();
        assertEquals(dao.buildFilterSearchUrl(filter, url), "test&with_cast=23");
    }

    @Test
    public void buildFilterUrl_1_2_6_7_10_14_18_22(){
        MovieFilter filter = MovieFilterBuilder.getBuilder().directorID(4).build();
        assertEquals(dao.buildFilterSearchUrl(filter, url), "test&with_crew=4");
    }

    @Test
    public void buildFilterUrl_1_2_6_10_11_14_18_22(){
        MovieFilter filter = MovieFilterBuilder.getBuilder().year(1998).build();
        assertEquals(dao.buildFilterSearchUrl(filter, url), "test&year=1998");
    }

    @Test
    public void buildFilterUrl_1_2_6_10_14_15_18_22(){
        MovieFilter filter = MovieFilterBuilder.getBuilder().genreID(7).build();
        assertEquals(dao.buildFilterSearchUrl(filter, url), "test&with_genres=7");
    }

    @Test
    public void buildFilterUrl_1_2_6_10_14_18__19_22(){
        MovieFilter filter = MovieFilterBuilder.getBuilder().runtime(100).build();
        assertEquals(dao.buildFilterSearchUrl(filter, url), "test&with_runtime.lte=100");
    }
}
