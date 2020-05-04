package aniagg.DB;

//import java.util.ArrayList;
import aniagg.Anime;
import aniagg.CustomExceptions.DBExceptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBConnection {

    //variables for executing queries on the db
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    private String username;
    private String password;

    //Used for creating an array to store Anime fields
    private final int animeIndices = 20;

    public DBConnection() {
        this(DBDetails.USERNAME, DBDetails.PASSWORD);
        connect();
    }

    /**
     * Constructor that takes a specified username and password
     *
     * @param name String for username
     * @param pass String for password
     */
    public DBConnection(String name, String pass) {
        username = name;
        password = pass;
    }

    /**
     * Used to open and close a connection every time the db needs to be accessed.
     */
    public void connect() {
        //Initialize the connection
        try {
            Class.forName(DBDetails.JDBC_DRIVER);
            conn = DriverManager.getConnection(DBDetails.DB_URL, username, password);
        } catch (Exception e) {
            System.out.println("Something went wrong when connecting to the database. Make sure that you can " +
                    "establish a connection to UofG's SoCS servers.\nExiting Program.");
            System.exit(0);
        }
    }

    /**
     * Used to open and close a connection every time the db needs to be accessed.
     */
    public void closeConnection() {
        //Initialize the connection
        //end the connection
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("Something went wrong when attempting to close the connection:\n" + e);
        }
    }

    /**
     * Gets a list of Anime objects from the Anime table based on the provided query.
     * @param sql String representing an SQL query.
     * @return A list of Anime objects
     */
    public List<Anime> getAnimeRecords(String sql) {
        //variables to return
        List<Anime> animeList = new ArrayList<>();
        Anime anime;
        String[] fields = new String[animeIndices];
        //connect();
        //execute the query
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            //for every returned row, create an anime object and add it to the list
            while (rs.next()) {
                anime = setAnimeFields(rs);
                //don't add if something went wrong
                if (!anime.isEmpty()) {
                    animeList.add(anime);
                }
            }
        //catch any issues along the way
        } catch (Exception e) {
            System.out.println(e);
        //close any/all connections
        } finally {
            try {
                stmt.close();
                //conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return animeList;
    }

    /**
     * Gets a list of Anime objects from the Anime table based on the provided query. Also takes a list of strings that
     * will be subbed into the query as sanitized values.
     * @param sql String representing an SQL query.
     * @param sanitizedValues List of strings representing sanitized values to be inserted into the query
     * @return A list of Anime objects
     */
    public List<Anime> getAnimeRecordsSanitized(String sql, List<String> sanitizedValues) {
        //list to return
        List<Anime> animeList = new ArrayList<>();
        Anime anime;
        //connect();
        //execute the query
        try {
            sanitizeQuery(sql, sanitizedValues);
            //System.out.println(prepStmt.toString());
            rs = prepStmt.executeQuery();

            //for every returned row, create an anime object and add it to the list
            while (rs.next()) {
                anime = setAnimeFields(rs);
                //don't add if something went wrong
                if (!anime.isEmpty()) {
                    animeList.add(anime);
                }
            }
        //catch any issues along the way
        } catch (Exception e) {
            System.out.println(e);
        //close any/all connections
        } finally {
            try {
                prepStmt.close();
                //conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return animeList;
    }

    /**
     * Executes a custom select query and returns the result as a string. Also takes a list of strings that
     * will be subbed into the query as sanitized values.
     * @param sql String representing an SQL query.
     * @param genre The genre string to be sanitized
     * @return A String with the result of the query.
     */
    public String getAverageByGenre(String sql, String genre) {
        String toReturn = null;
        try {
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, genre);
            rs = prepStmt.executeQuery();
        //catch any issues along the way
        } catch (Exception e) {
            System.out.println(e);
        //close any/all connections
        } finally {
            try {
                if (rs.next()) {
                    toReturn = rs.getString("average");
                    prepStmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (toReturn == null) {
            throw new DBExceptions.EmptyResultException("The query returned an empty set.");
        }
        return toReturn;
    }

    /**
     * Updates the db based on the input SQL string. Kind of dangerous.
     *
     * @param sql String representing an SQL query.
     */
    public void customUpdate(String sql) {
        //connect();
        //execute the query
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            //catch any issues along the way
        } catch (Exception e) {
            System.out.println(e);
            //close any/all connections
        } finally {
            try {
                stmt.close();
                //conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the db based on the input SQL string. Also takes a list of strings that will be subbed into the
     * query as sanitized values.
     *
     * @param sql             String representing an SQL query.
     * @param sanitizedValues List of strings representing sanitized values to be inserted into the query
     */
    public void customUpdateSanitized(String sql, List<String> sanitizedValues) {
        //connect();
        //execute the query
        try {
            //replace placeholders with sanitized values
            sanitizeQuery(sql, sanitizedValues);
            prepStmt.executeUpdate();
            //check for duplicate entry
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("The following record already exists: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
            //close any/all connections
        } finally {
            try {
                prepStmt.close();
                //conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sanitizeQuery(String sql, List<String> sanitizedValues) {
        try {
            prepStmt = conn.prepareStatement(sql);
            //sub values into the placeholders in the query to sanitize them
            for (int i = 0; i < sanitizedValues.size(); i++) {
                prepStmt.setString(i + 1, sanitizedValues.get(i));
            }
        //check for duplicate entry
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("A record was not added: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Anime setAnimeFields(ResultSet rs) {
        Anime anime = new Anime();
        String[] fields = new String[animeIndices];

        try {
            //populate the string based on the number of
            for (int i = 0; i < animeIndices; i++) {
                fields[i] = rs.getString(i + 2);
            }
            anime.setValues(fields);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return anime;
    }
}
