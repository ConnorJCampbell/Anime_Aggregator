package aniagg.DB;

public final class DBDetails {
    //username for my database
    public static final String USERNAME = "ccampb21";

    //password for my database (student number)
    public static final  String PASSWORD = "0918831";

    //configuring driver and url
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = String.format("jdbc:mysql://dursley.socs.uoguelph.ca:3306/%s?" +
           "useLegacyDatetimeCode=false&serverTimezone=America/New_York", USERNAME);

    private DBDetails() {

    }
}
