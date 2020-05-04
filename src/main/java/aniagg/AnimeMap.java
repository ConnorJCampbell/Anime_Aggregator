package aniagg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnimeMap {
    private Map<String, Anime> animeMap;

    /**
     * Contstructor for AnimeMap
     */
    public AnimeMap() {
        animeMap = new HashMap<>();
    }

    /**
     * Sets a new member in the HashMap
     * @param title String for the title of the anime
     * @param anime Anime object
     */
    public void setAnime(String title, Anime anime) {
        animeMap.put(title.toLowerCase(), anime);
    }

    /**
     * Returns a single anime object from the Map
     * @param title the title of the anime
     * @return an anime object
     */
    public Anime getAnime(String title) {
        return animeMap.get(title.toLowerCase());
    }

    /**
     * returns the whole HashMap
     * @return A hashmap of anime objects
     */
    public Map<String, Anime> getAnimeList() {
        return animeMap;
    }

    /**
     * Checks if the map of anime is empty
     * @return boolean true if map is empty, false otherwise
     */
    public boolean isEmpty() {
        return animeMap.isEmpty();
    }

    /**
     * Prints out every single anime one record. This method is only for testing purposes
     */
    public void printMap() {
        Iterator it = animeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getValue().toString());
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
