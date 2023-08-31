package dndcopy.fr.ecoleNum.dd.gameComponents.dice;

/**
 * Cette interface définie les deux méthodes nécessaires pour qu'un objet soit un dé.
 */
public interface Dice {

    /**
     * Cette méthode permet de lancer un dé et que celui-ci ai un entier associé.
     */
    public void throwDice();

    /**
     * Cette méthode permet de récupérer la valeur du dernier lancé.
     * @return la valeur du dernier lancé.
     */
    public int getValue();

    }
