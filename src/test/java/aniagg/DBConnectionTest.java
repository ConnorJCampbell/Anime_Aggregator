package aniagg;

import aniagg.DB.DBConnection;
import aniagg.CustomExceptions.DBExceptions;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;



public class DBConnectionTest {
    private DBConnection testerOne;

    @Before
    public void setup() {
        testerOne = new DBConnection();
    }

    @Test
    public void testGetAnimeRecords() {
        String sql = "SELECT * FROM test_anime_db WHERE LOWER(title) = 'one piece' ORDER BY title;";
        List<Anime> animeList;
        animeList = testerOne.getAnimeRecords(sql);
        assertEquals(animeList.get(0).getTitle().toLowerCase(), "one piece");
    }

    @Test
    public void testGetAnimeRecordsSanitized() {
        String sql = "SELECT * FROM test_anime_db WHERE LOWER(title) = ? ORDER BY title;";
        String title = "one piece";
        List<Anime> animeList;
        List<String> sanitizedValues = new ArrayList<>();
        sanitizedValues.add(title.toLowerCase());
        animeList = testerOne.getAnimeRecordsSanitized(sql, sanitizedValues);
        assertEquals(animeList.get(0).getTitle().toLowerCase(), title);
    }

    @Test
    public void testGetAnimeRecordsEmptyResult() {
        String sql = "SELECT * FROM test_anime_db WHERE LOWER(title) = \"\" ORDER BY title;";
        List<Anime> animeList = testerOne.getAnimeRecords(sql);
        assertTrue(animeList.isEmpty());
    }

    @Test
    public void testGetAnimeRecordsSanitizedEmptyResult() {
        String sql = "SELECT * FROM test_anime_db WHERE LOWER(title) = ? ORDER BY title;";
        String invalidTitle = "this title should not match anything";
        List<Anime> animeList;
        List<String> sanitizedValues = new ArrayList<>();
        sanitizedValues.add(invalidTitle.toLowerCase());
        animeList = testerOne.getAnimeRecordsSanitized(sql, sanitizedValues);
        assertTrue(animeList.isEmpty());
    }

    @Test (expected = DBExceptions.EmptyResultException.class)
    public void testGetAverageByGenreEmptyResult() {
        String sql = "SELECT AVG(members) AS average FROM test_anime_db WHERE LOWER(genre) LIKE ? ORDER BY title;";
        String invalidGenre = "this should not match anything";
        testerOne.getAverageByGenre(sql, invalidGenre);
    }


}
