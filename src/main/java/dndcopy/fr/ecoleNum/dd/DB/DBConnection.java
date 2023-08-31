package dndcopy.fr.ecoleNum.dd.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Cette classe permet la création d'un singleton Connection à une base de données.
 */
public class DBConnection {

    private static Connection connection;

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Properties env = Env.getEnv();

            String id = env.getProperty("id");
            String password = env.getProperty("password");
            String database = env.getProperty("database");
            String ip = env.getProperty("ip");
            String server = env.getProperty("server");

            connection = DriverManager.getConnection("jdbc:"+server+"://"+ip+"/"+database, id, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cette méthode créé une connexion lors de son premier appel et renvoie cette connexion à chaque appel.
     * @return l'objet Connection
     */
    static Connection  getInstance() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }

}
