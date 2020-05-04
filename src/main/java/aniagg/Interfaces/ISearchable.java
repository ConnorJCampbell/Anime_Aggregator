package aniagg.Interfaces;

import aniagg.DB.DBConnection;

public interface ISearchable {

    /**
     * Lists all records for a specified number of tables
     * @param db A DBConnection for communicating with the Database
     */
    void getAllRecords(DBConnection db);

    /**
     * Finds a record in a table based on a specified title
     * @param db A DBConnection for communicating with the Database
     * @param title A string representing the title or a substring of it
     */
    void getRecordByTitle(DBConnection db, String title);

    /**
     * Finds an average number of views based on a specified genre.
     * @param db A DBConnection for communicating with the Database
     * @param genre A string representing the title or a substring of it
     */
    void getAverageViewersByGenre(DBConnection db, String genre);
}
