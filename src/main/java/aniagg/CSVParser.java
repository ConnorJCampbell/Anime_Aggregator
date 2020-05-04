package aniagg;


import aniagg.CustomExceptions.ParserExceptions;
import aniagg.DB.DBConnection;
import aniagg.Interfaces.IParser;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVParser implements IParser {

    private static final String[] COLUMN_HEADERS = {"Title", "Type", "Episodes", "Status", "Start airing", "End airing",
            "Starting season", "Broadcast time", "Producers", "Licensors", "Studios", "Sources", "Genres", "Duration",
            "Rating", "Score", "Scored by", "Members", "Favorites", "Description"};

    /**
     * Constructor for a CSVParser object
     */
    public CSVParser() {

    }

    /**
     * Adds records from a file to the records on the system.
     * @param fileName name of the file to be read in
     * @param dbConnection A DBConnection for communicating with the Database
     */
    public void populateDataFromFile(String fileName, DBConnection dbConnection) {

        //variables for csv reader and temporary anime label
        Anime anime;

        //main parsing loop
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] line;
            line = reader.readNext();
            Map<String, Integer> columnIndexMap = getColumnHeaders(line);
            while ((line = reader.readNext()) != null) {
                //new anime object
                anime = new Anime();
                //set values based on csv (this should be updated if the anime object is broken into subclasses)
                anime.setValues(line);
                //add the object to the map
                anime.addNewDBRecord(dbConnection);
            }
            System.out.println("The data was successfully added.\n");
        } catch (FileNotFoundException e) {
            System.out.println("The specified file does not exist.\n");
        } catch (ParserExceptions.InvalidColumnException e) {
            System.out.println("An error was encountered before parsing could begin. Ensure the following columns are present:");
            for (String s : COLUMN_HEADERS) {
                System.out.println("  " + s);
            }
            System.out.println("");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("It doesn't look like that file is the correct format. Try another file.\n");
        } catch (Exception e) {
            System.out.println("Wow you hit an exception that I did not account for. Here it is:\n" + e.getMessage());
        }
    }

    private Map<String, Integer> getColumnHeaders(String[] columns) {
        Map<String, Integer> columnIndexMap = new HashMap<>();
        checkColumns(columns);
        for (int i = 0; i < columns.length; i++) {
            columnIndexMap.put(columns[i], i);
        }
        return columnIndexMap;
    }

    private void checkColumns(String[] columns)  {
        List<String> columnList = Arrays.asList(columns);
        columnList.replaceAll(String::toLowerCase);
        for (String requiredColumn : COLUMN_HEADERS) {
            if (!columnList.contains(requiredColumn.toLowerCase())) {
                throw new ParserExceptions.InvalidColumnException("Missing column " + requiredColumn);
            }
        }
    }

}
