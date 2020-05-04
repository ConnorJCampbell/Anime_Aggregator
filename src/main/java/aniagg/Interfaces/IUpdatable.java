package aniagg.Interfaces;

import aniagg.DB.DBConnection;

public interface IUpdatable {

    /**
     * adds a row to the database using the provided field values.
     * @param db A DBConnection for communicating with the Database
     */
    void addNewDBRecord(DBConnection db);

    /**
     * Remove all records from any number of tables
     * @param db A DBConnection for communicating with the Database
     */
    void deleteAllRecords(DBConnection db);
}
