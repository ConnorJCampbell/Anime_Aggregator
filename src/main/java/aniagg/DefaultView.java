package aniagg;

import aniagg.DB.DBConnection;
import aniagg.Interfaces.ISearchable;

import java.util.Scanner;

public abstract class DefaultView {
    private Scanner scanner;
    private DBConnection dbConnection;
    private ISearchable animeToSearch;

    /**
     * Default Constructor, creates objects for holding records and for gathering user input
     */
    public DefaultView() {
        scanner = new Scanner(System.in);
        dbConnection = new DBConnection();
        animeToSearch = new Anime();
    }

    /**
     * Returns the scanner object
     * @return A Scanner for user input
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Returns a object that is used for communicating with the database
     * @return A DBConnection for communicating with the database
     */
    public DBConnection getDbConnection() {
        return dbConnection;
    }

    /**
     * Returns an Anime object that can be used for search queries
     * @return An object that implements ISearchable
     */
    public ISearchable getAnimeToSearch() {
        return animeToSearch;
    }

    /**
     * Provides the main menu loop for a particular user
     */
    protected abstract void useView();

    /**
     * Returns a Welcome message for the current user
     * @return A String with the welcome message
     */
    protected abstract String getWelcome();

    /**
     * Returns the menu with options for the current user
     * @return A String with the menu
     */
    protected abstract String getMenu();

}
