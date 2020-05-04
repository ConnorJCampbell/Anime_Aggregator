package aniagg;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ViewTest {
    private DefaultView producerView;
    private DefaultView watcherView;

    private String expectedWelcomeP;
    private String expectedWelcomeW;
    private String expectedMenuP;
    private String expectedMenuW;

    @Before
    public void setup() {
        producerView = new ProducerView();
        watcherView = new WatcherView();
        expectedWelcomeP = "\nWelcome to the Anime Aggregator. As a producer, you can select one of the " +
                "following options:\n";
        expectedWelcomeW = "\nWelcome to the Anime Aggregator. As a watcher, you can select one of the " +
                "following options:";
        expectedMenuP = "\nPlease select an option:\n"
                + "1 - Load in a csv\n"
                + "2 - Print all records (Might be a lot)\n"
                + "3 - Get average viewers based on a genre\n"
                + "4 - Delete all anime records (requires confirmation)\n"
                + "5 - Quit\n\n> ";
        expectedMenuW = "1 - Search anime by title\n"
                + "2 - Quit\n\n> ";
    }

    @Test
    public void testGetWelcomeProducer() {
        assertEquals(producerView.getWelcome(), expectedWelcomeP);
    }

    @Test
    public void testGetWelcomeWatcher() {
        assertEquals(watcherView.getWelcome(), expectedWelcomeW);
    }

    @Test
    public void testGetMenuProducer() {
        assertEquals(producerView.getMenu(), expectedMenuP);
    }

    @Test
    public void testGetMenuWatcher() {
        assertEquals(watcherView.getMenu(), expectedMenuW);
    }
}
