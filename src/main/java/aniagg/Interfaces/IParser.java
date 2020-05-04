package aniagg.Interfaces;

import aniagg.DB.DBConnection;

public interface IParser {
    /**
     * Adds records from a file to the records on the system.
     * @param fileName name of the file to be read in
     * @param dbConnection A DBConnection for communicating with the Database
     */
    void populateDataFromFile(String fileName, DBConnection dbConnection);
}
