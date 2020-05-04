package aniagg;

public class WatcherView extends DefaultView {

    /**
     * Default Constructor, creates objects for holding records and for gathering user input
     */
    public WatcherView() {
        super();
    }

    /**
     * Main user interaction routine.  Loops until program is exited.
     */
    public void useView() {

        System.out.println(getWelcome());
        String inputLine;

        //only two options right now, search by title and quit.
        boolean finished = false;
        while (!finished) {
            System.out.print(getMenu());
            inputLine = getScanner().nextLine();
            switch (inputLine) {
                //Search by title
                case "1":
                    searchByTitle();
                    break;
                //Quit
                case "2":
                    finished = true;
                    break;
                //Try again
                default:
                    System.out.println("Invalid menu option. please try again:\n");
                    break;
            }
        }
        System.out.println("Thank you for using this system. Good bye.");
        getDbConnection().closeConnection();
    }

    /**
     * Returns a Welcome message for a watcher user
     * @return A String with the welcome message
     */
    protected String getWelcome() {
        return "\nWelcome to the Anime Aggregator. As a watcher, you can select one of the following options:";
    }

    /**
     * Returns the menu with options for a watcher user
     * @return A String with the menu
     */
    protected String getMenu() {
        return "1 - Search anime by title\n"
                + "2 - Quit\n\n> ";
    }

    /**
     * Prompts the user to enter the title of and anime, and prints out information about the anime if it is found.
     */
    private void searchByTitle() {
        String inputLine;
        Anime tempAnime;

        //get anime title from user input
        System.out.print("\nEnter the title of the anime:\n> ");
        inputLine = getScanner().nextLine();

        //retrieve anime
        getAnimeToSearch().getRecordByTitle(getDbConnection(), inputLine);
    }

    /**
     * Main method, provides an interface for the watcher role
     * @param args command line arguments
     */
    public static void main(String[] args) {
        WatcherView toUse = new WatcherView();
        toUse.useView();
    }
}
