package aniagg;

import aniagg.CustomExceptions.AnimeExceptions;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
//import static org.junit.Assert.fail;
//import static org.junit.Assert.assertTrue;

public class AnimeTest {

    private Anime toEqual;
    private Anime toEqualString;
    private Anime testSetandGet;

    private String[] fieldValues = {"some_title", "TV", "12", "Finished Airing", "2015-10-5", "2015-12-21", "Fall",
            "Mondays at 01:05 (JST)", "TV Tokyo", "producer_name", "Viz Media", "Madhouse", "Manga", "Action",
            "24 min", "R", "8.73", "675113", "994888", "30140", "Some Description"};

    @Before
    public void setup() {
        toEqual = new Anime();
        toEqual.setValues(fieldValues);
        toEqualString = new Anime();
        toEqualString.setValues(fieldValues);
        toEqualString.setEpisodes(-1);
        testSetandGet = new Anime();
    }

    @Test
    public void testEquals() {
        Anime toCompare = new Anime();
        toCompare.setValues(fieldValues);
        assertEquals(toEqual, toCompare);
    }

    @Test
    public void testEqualsSameObject() {
        Anime toCompare = toEqual;
        assertEquals(toEqual, toCompare);
    }

    @Test
    public void testToString() {
        Anime toPrint = new Anime();
        toPrint.setValues(fieldValues);
        assertEquals(toEqual.toString(), toPrint.toString());
    }

    @Test
    public void testToStringOngoingEpisodes() {
        Anime toPrint = new Anime();
        toPrint.setValues(fieldValues);
        toPrint.setEpisodes(-1);
        assertEquals(toEqualString.toString(), toPrint.toString());
    }

    @Test
    public void testSetAndGetTitle() {
        String toSet = "a title";
        testSetandGet.setTitle(toSet);
        String toGet = testSetandGet.getTitle();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetType() {
        String toSet = "type";
        testSetandGet.setType(toSet);
        String toGet = testSetandGet.getType();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetEpisodes() {
        int toSet = 5;
        testSetandGet.setEpisodes(toSet);
        int toGet = testSetandGet.getEpisodes();
        assertEquals(toSet, toGet);
    }

    @Test (expected = AnimeExceptions.NegativeIntegerException.class)
    public void testSetEpisodesNegative() {
        int toSet = -5;
        testSetandGet.setEpisodes(toSet);
    }

    @Test
    public void testSetAndGetStatus() {
        String toSet = "Status";
        testSetandGet.setStatus(toSet);
        String toGet = testSetandGet.getStatus();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetStartDate() {
        String toSet = "StartDate";
        testSetandGet.setStartDate(toSet);
        String toGet = testSetandGet.getStartDate();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetEndDate() {
        String toSet = "EndDate";
        testSetandGet.setEndDate(toSet);
        String toGet = testSetandGet.getEndDate();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetSeason() {
        String toSet = "Season";
        testSetandGet.setSeason(toSet);
        String toGet = testSetandGet.getSeason();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetBroadcast() {
        String toSet = "Broadcast";
        testSetandGet.setBroadcast(toSet);
        String toGet = testSetandGet.getBroadcast();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetProducer() {
        String toSet = "Producer";
        testSetandGet.setProducer(toSet);
        String toGet = testSetandGet.getProducer();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetLicensor() {
        String toSet = "Licensor";
        testSetandGet.setLicensor(toSet);
        String toGet = testSetandGet.getLicensor();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetStudio() {
        String toSet = "Studio";
        testSetandGet.setStudio(toSet);
        String toGet = testSetandGet.getStudio();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetSource() {
        String toSet = "Source";
        testSetandGet.setSource(toSet);
        String toGet = testSetandGet.getSource();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetGenre() {
        String toSet = "Genre";
        testSetandGet.setGenre(toSet);
        String toGet = testSetandGet.getGenre();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetDuration() {
        String toSet = "Duration";
        testSetandGet.setDuration(toSet);
        String toGet = testSetandGet.getDuration();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetRating() {
        String toSet = "Rating";
        testSetandGet.setRating(toSet);
        String toGet = testSetandGet.getRating();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetScore() {
        double toSet = 9.5;
        testSetandGet.setScore(toSet);
        double toGet = testSetandGet.getScore();
        assertEquals(0, Double.compare(toSet, toGet));
    }

    @Test
    public void testSetAndGetScoredBy() {
        int toSet = 5;
        testSetandGet.setScoredBy(toSet);
        int toGet = testSetandGet.getScoredBy();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetMembers() {
        int toSet = 5;
        testSetandGet.setMembers(toSet);
        int toGet = testSetandGet.getMembers();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetFavorites() {
        int toSet = 5;
        testSetandGet.setFavorites(toSet);
        int toGet = testSetandGet.getFavorites();
        assertEquals(toSet, toGet);
    }

    @Test
    public void testSetAndGetDescription() {
        String toSet = "Description";
        testSetandGet.setDescription(toSet);
        String toGet = testSetandGet.getDescription();
        assertEquals(toSet, toGet);
    }

}
