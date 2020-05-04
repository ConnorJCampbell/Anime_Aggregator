package aniagg;


import aniagg.DB.DBConnection;
import aniagg.Interfaces.ISearchable;
import aniagg.Interfaces.IUpdatable;

import aniagg.CustomExceptions.DBExceptions;
import aniagg.CustomExceptions.AnimeExceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Anime implements IUpdatable, ISearchable {

    private String title, type, status, startDate, endDate, season, broadcast, producer, licensor, studio;
    private String source, genre, duration, rating;
    private double score;
    private int episodes, scoredBy, members, favorites;
    private String description;

    /**
     * Default constructor for Anime class
     */
    public Anime() {

    }

    /** Sets all fields in an anime object
     * @param fields A String array with all the fields in an anime object
     */
    public void setValues(String[] fields) {
        title = fields[0];
        type = fields[1];
        try {
            episodes = Integer.parseInt(fields[2]);
        } catch (NumberFormatException e) {
            //System.out.println("That episode number isn't there.");
            episodes = -1;
        }
        status = fields[3];
        startDate = fields[4];
        endDate = fields[5];
        season = fields[6];
        broadcast = fields[7];
        producer = fields[8];
        licensor = fields[9];
        studio = fields[10];
        source = fields[11];
        genre = fields[12];
        duration = fields[13];
        rating = fields[14];
        try {
            score = Double.parseDouble(fields[15]);
        } catch (NumberFormatException e) {
            System.out.println("The score was recorded in an improper format: " +  e.getMessage());
            score = 0.0;
        }
        try {
            scoredBy = Integer.parseInt(fields[16]);
        } catch (NumberFormatException e) {
            System.out.println("ScoredBy was recorded in an improper format: " +  e.getMessage());
            scoredBy = 0;
        }
        try {
            members = Integer.parseInt(fields[17]);
        } catch (NumberFormatException e) {
            System.out.println("Members was recorded in an improper format: " +  e.getMessage());
            members = 0;
        }
        try {
            favorites = Integer.parseInt(fields[18]);
        } catch (NumberFormatException e) {
            System.out.println("Favorites was recorded in an improper format: " +  e.getMessage());
            favorites = 0;
        }
       description = fields[19];
    }

    /**
     * Lists all records from the Anime table
     * @param db A DBConnection for communicating with the Database
     */
    public void getAllRecords(DBConnection db) {
        String sql = "SELECT * FROM Anime ORDER BY title;";
        List<Anime> animeList;
        animeList = db.getAnimeRecords(sql);
        if (animeList.isEmpty()) {
            System.out.println("There are no records to print.");
        } else {
            for (Anime anime : animeList) {
                System.out.println(anime.toString());
            }
        }
    }

    /**
     * Gets a list of anime with a title matching the parameter and prints them to stdout
     * @param db A DBConnection for communicating with the Database
     * @param title A string representing the title or a substring of it
     */
    public void getRecordByTitle(DBConnection db, String title) {
        //String sql = "SELECT * FROM Anime WHERE LOWER(title) LIKE CONCAT('%', ?, '%');";
        String sql = "SELECT * FROM Anime WHERE LOWER(title) LIKE ? ORDER BY title;";
        List<Anime> animeList;
        List<String> sanitizedValues = new ArrayList<>();
        String titleToSearch = "%" + title + "%";
        sanitizedValues.add(titleToSearch.toLowerCase());
        animeList = db.getAnimeRecordsSanitized(sql, sanitizedValues);
        if (animeList.isEmpty()) {
            System.out.println("An anime with the title \"" + title + "\" was not found in the records.");
        } else {
            System.out.println("Anime matching the search keyword(s): ");
            for (Anime anime : animeList) {
                System.out.println(anime.toString());
            }
        }
    }

    /**
     * Selects the average number of viewers based on the input genre.
     * @param db A DBConnection for communicating with the Database
     * @param genre A string representing the title or a substring of it
     */
    public void getAverageViewersByGenre(DBConnection db, String genre) {
        String sql = "SELECT AVG(members) AS average FROM Anime WHERE LOWER(genre) LIKE ? ORDER BY title;";
        String result = null;
        String genreToSearch = "%" +  genre + "%";
        try {
            result = db.getAverageByGenre(sql, genreToSearch.toLowerCase());
        } catch (DBExceptions.EmptyResultException ex) {
            System.out.println("No anime records with a genre like \"" + genre + "\" were found.\n");
        } finally {
            if (result != null) {
                System.out.println("The average number of viewers for a show with the genre \"" + genre + "\" is: "
                        + result + " viewers.\n");
            }
        }
    }

    /**
     * Creates a new entry in the Anime table for the current anime object
     * @param db A DBConnection for communicating with the Database
     */
    public void addNewDBRecord(DBConnection db) {

        //create a list of variables that need to be sanitized
        List<String> sanitizedValues;
        sanitizedValues = getSafeValuesForNewRecord();

        //episodes can be ongoing, and may not have a definitive value
        String episodeString = "NULL";
        if (episodes != -1) {
            episodeString = String.valueOf(episodes);
        }

        String sql = "INSERT INTO Anime" +
                "(title,type,episodes,status,start_date,end_date,season,broadcast,producer,licensor,studio,source," +
                "genre,duration,rating,score,scored_by,members,favorites,description) " +
                "VALUES( ?,'" + type + "'," + episodeString + ",'" +  status + "','" + startDate + "','" +
                endDate + "','" + season + "','" + broadcast + "', ?, ?, ?,'" + source + "','" + genre + "','" +
                duration + "','" + rating + "'," + score + "," + scoredBy + "," + members +
                "," + favorites + ", ? );";
        db.customUpdateSanitized(sql, sanitizedValues);
    }

    /**
     * Removes all records from the anime table
     * @param db A DBConnection for communicating with the Database
     */
    public void deleteAllRecords(DBConnection db) {
        String sql = "DELETE FROM Anime;";
        db.customUpdate(sql);
    }

    private List<String> getSafeValuesForNewRecord() {
        List<String> sanitizedValues = new ArrayList<>();
        sanitizedValues.add(title);
        sanitizedValues.add(producer);
        sanitizedValues.add(licensor);
        sanitizedValues.add(studio);
        sanitizedValues.add(description);
        return sanitizedValues;
    }

    /**
     * Checks whether or not the values in the anime object have been set
     * @return true if the object is empty, false otherwise
     */
    public boolean isEmpty() {
        return title.isEmpty();
    }

    /**
     * Overrides toString for printing out info about anime
     * @return A string with info about the anime
     */
    @Override
    public String toString() {
        String toReturn;
        toReturn = "Title: '" + title + "'\n" +
                "Type: '" + type + "'\n";
                if (episodes == -1) {
                    toReturn += "Episodes: 'Ongoing'\n";
                } else {
                    toReturn += "Episodes: '" + episodes + "'\n";
                }
        toReturn += "Status: '" + status + "'\n" +
                "Start Date: '" + startDate + "'\n" +
                "End Date: '" + endDate + "'\n" +
                "Season: '" + season + "'\n" +
                //", broadcast='" + broadcast + "'\n" +
                //", producer='" + producer + "'\n" +
                "Licensor: '" + licensor + "'\n" +
                "Studio: '" + studio + "'\n" +
                "Source: '" + source + "'\n" +
                "Genre: '" + genre + "'\n" +
                "Duration: '" + duration + "'\n" +
                "Rating: '" + rating + "'\n" +
                "Description: '" + description + "'\n" +
                //", scoredBy=" + scoredBy + "\n"
                //", members=" + members + "\n"
                //", favorites=" + favorites + "\n"
                "Score: " + score + "/10\n";
        return toReturn;
    }

    /**
     * Compares two anime objects to see if they are equal
     * @param o object to be compared
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Anime anime = (Anime) o;
        return Double.compare(anime.score, score) == 0 &&
                episodes == anime.episodes &&
                scoredBy == anime.scoredBy &&
                members == anime.members &&
                favorites == anime.favorites &&
                title.equals(anime.title) &&
                type.equals(anime.type) &&
                status.equals(anime.status) &&
                startDate.equals(anime.startDate) &&
                endDate.equals(anime.endDate) &&
                season.equals(anime.season) &&
                broadcast.equals(anime.broadcast) &&
                producer.equals(anime.producer) &&
                licensor.equals(anime.licensor) &&
                studio.equals(anime.studio) &&
                source.equals(anime.source) &&
                genre.equals(anime.genre) &&
                duration.equals(anime.duration) &&
                rating.equals(anime.rating) &&
                description.equals(anime.description);
    }

    /**
     * Returns the hashcode for an anime object
     * @return int representing the object's hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    /**
     * gets the title
     * @return A string representing the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * gets the type
     * @return A string representing the type
     */
    public String getType() {
        return type;
    }

    /**
     * gets the number of episodes
     * @return A string representing the number of episodes
     */
    public int getEpisodes() {
        return episodes;
    }

    /**
     * gets the status of the anime
     * @return A string representing the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * gets the start date
     * @return a String representing the startdate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * gets the enddate
     * @return a string representing the enddate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * gets the season the show premiered
     * @return A String representing the season a show premiered.
     */
    public String getSeason() {
        return season;
    }

    /**
     * gets the boradcast time
     * @return a String representing the broadcast time
     */
    public String getBroadcast() {
        return broadcast;
    }

    /**
     * gets the producer
     * @return a string representing the producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * gets the licensor
     * @return a String representing the licensor
     */
    public String getLicensor() {
        return licensor;
    }

    /**
     * gets the studio
     * @return A string representing the studio
     */
    public String getStudio() {
        return studio;
    }

    /**
     * gets the source
     * @return a string representing the source
     */
    public String getSource() {
        return source;
    }

    /**
     * gets the genre
     * @return a String representing the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * gets the duration
     * @return a string representing the rating
     */
    public String getDuration() {
        return duration;
    }

    /**
     * gets the rating
     * @return a string representing the rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * gets the score
     * @return an int representing the score
     */
    public double getScore() {
        return score;
    }

    /**
     * gets the number of people who scored the show
     * @return an int representing the number of people
     */
    public int getScoredBy() {
        return scoredBy;
    }

    /**
     * gets the number of members
     * @return an int representing the number s of members
     */
    public int getMembers() {
        return members;
    }

    /**
     * an int representing the favourites
     * @return an int representing the number of favourites
     */
    public int getFavorites() {
        return favorites;
    }

    /**
     * gets the description of the show
     * @return String representing the description of the show
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the title of the show
     * @param title A string representing the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * sets the type of show
     * @param type string representing the format of the show
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * sets the episode count
     * @param episodes String representing the episode count
     */
    public void setEpisodes(int episodes) {
        if (episodes < -1) {
            throw new AnimeExceptions.NegativeIntegerException("Episode number cannot be below -1");
        } else {
            this.episodes = episodes;
        }
    }

    /**
     * sets the status
     * @param status A string representing the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * sets the startdate
     * @param startDate string representing the startdate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * sets the enddate
     * @param endDate String representing the end date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * sets the season
     * @param season String for the season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * sets the broadcast time
     * @param broadcast String for the broadcast time
     */
    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    /**
     * sets the producer
     * @param producer string for the producer
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * sets the licensor
     * @param licensor String for the licensor
     */
    public void setLicensor(String licensor) {
        this.licensor = licensor;
    }

    /**
     * sets the studio
     * @param studio String for the studio
     */
    public void setStudio(String studio) {
        this.studio = studio;
    }

    /**
     * sets the source
     * @param source String representing the source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * sets the genre
     * @param genre string representing the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * sets the duration
     * @param duration string representing the duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * sets the rating
     * @param rating String representing the rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * sets the score value
     * @param score int representing the score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * sets the scoredby value
     * @param scoredBy int representing the number of people
     */
    public void setScoredBy(int scoredBy) {
        this.scoredBy = scoredBy;
    }

    /**
     * sets the number of members
     * @param members int for the number of members
     */
    public void setMembers(int members) {
        this.members = members;
    }

    /**
     * sets the favourites
     * @param favorites an int representing the favourites
     */
    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    /**
     * sets the description
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
