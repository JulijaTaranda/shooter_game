package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        //method might throw certain exceptions, so use throws
        // JDBC code that might throw SQLException
        // Dynamically load a class that might throw ClassNotFoundException

        //jdbc - Java Database Connectivity
        String DB = "jdbc:mysql://localhost:3306/shooting_game";
        String USER = "root";
        String PASS = "Pfhtwrbq2212";

        //to load a JDBC driver dynamically
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(DB,USER,PASS);

    }
}