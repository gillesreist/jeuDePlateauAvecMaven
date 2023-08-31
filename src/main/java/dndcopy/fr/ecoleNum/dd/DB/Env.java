package dndcopy.fr.ecoleNum.dd.DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Cette classe permet la création d'un singleton properties depuis un fichier qui contient les informations de la base de donnée.
 */
public class Env {

    private static Properties env;

    private Env() {
        try {
            env = new Properties();
            env.load(new FileInputStream(".env"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cette méthode créé un objet properties lors de son premier appel et renvoie cet objet à chaque appel.
     * @return l'objet properties.
     */
    static Properties getEnv() {
        if (env == null) {
            new Env();
        }
        return env;
    }



}
