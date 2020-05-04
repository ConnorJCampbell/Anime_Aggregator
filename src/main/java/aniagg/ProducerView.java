package aniagg;

import aniagg.Interfaces.IParser;
import aniagg.Interfaces.IUpdatable;

public class ProducerView extends DefaultView {


    private IParser parser;
    private IUpdatable animeUpdate;

    /**
     * Default Constructor, creates objects for holding records and for gathering user input
     */
    public ProducerView() {
        super();
        parser = new CSVParser();
        animeUpdate = new Anime();
    }

    /**
     * Main user interaction routine.  Loops until program is exited.
     */
    public void useView() {

        System.out.println(getWelcome());
        String inputLine;

        boolean finished = false;
        while (!finished) {
            System.out.print(getMenu());
            inputLine = getScanner().nextLine();
            switch (inputLine) {
                //Read in csv
                case "1":
                    addCSVData();
                    break;
                //print all the records
                case "2":
                    getAnimeToSearch().getAllRecords(getDbConnection());
                    break;
                //get input for genre type then find average viewers
                case "3":
                    findGenre();
                    break;
                //Delete all records in the anime table
                case "4":
                    confirmDeleteAllRecords();
                    break;
                //Quit
                case "5":
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
     * Returns a Welcome message for a producer user
     * @return A String with the welcome message
     */
    protected String getWelcome() {
        return "\nWelcome to the Anime Aggregator. As a producer, you can select one of the following options:\n";
    }

    /**
     * Returns the menu with options for a producer viewer
     * @return A String with the menu
     */
    protected String getMenu() {
        return "\nPlease select an option:\n"
                + "1 - Load in a csv\n"
                + "2 - Print all records (Might be a lot)\n"
                + "3 - Get average viewers based on a genre\n"
                + "4 - Delete all anime records (requires confirmation)\n"
                + "5 - Quit\n\n> ";
    }

    private void addCSVData() {
        String inputLine;

        //get csv file name
        System.out.print("\nEnter the name of the csv file (with extension):\n> ");
        inputLine = super.getScanner().nextLine();

        //create a new CSVParser for parsing the file
        parser.populateDataFromFile("src/main/resources/" + inputLine, getDbConnection());
    }

    private void findGenre() {
        String inputLine;

        //get csv file name
        System.out.print("\nEnter the name of the genre you want to search for:\n> ");
        inputLine = super.getScanner().nextLine();

        //create a new CSVParser for parsing the file
        getAnimeToSearch().getAverageViewersByGenre(getDbConnection(), inputLine);
    }

    private void confirmDeleteAllRecords() {
        String input;
        System.out.print("\nAre you sure you want to delete all records?\n1 - Yes\n2 (or anything else) - No\n> ");
        input = super.getScanner().nextLine();

        //Delete data
        if ("1".equals(input)) {
            System.out.println("Deleting records....");
            animeUpdate.deleteAllRecords(getDbConnection());
            //Return to the menu
        }
        System.out.println("Returning to main menu.");
    }


    /**
     * Main method, provides an interface for the producer role
     * @param args command line arguments
     */
    public static void main(String[] args) {
        DefaultView toUse = new ProducerView();
        toUse.useView();
    }
}


