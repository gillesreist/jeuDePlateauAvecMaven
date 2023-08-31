package dndcopy.fr.ecoleNum.dd.gameEngine;

import dndcopy.fr.ecoleNum.dd.DB.Env;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static Properties config;

    private Config() {
        try {
            config = new Properties();
            InputStream file = getClass().getClassLoader().getResourceAsStream("my_config.properties");
            config.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cette méthode créé un objet properties lors de son premier appel et renvoie cet objet à chaque appel.
     * @return l'objet properties.
     */
    static Properties getConfig() {
        if (config == null) {
            new Config();
        }
        return config;
    }



}
