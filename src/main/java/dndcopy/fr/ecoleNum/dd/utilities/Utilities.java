package dndcopy.fr.ecoleNum.dd.utilities;

/**
 * Cette classe contient des méthodes static utilitaires.
 */
public class Utilities {

    /**
     * Cette méthode transforme une chaîne de caractères en entier, si la chaîne n'est pas composée uniquement de chiffres, l'entier renvoyé est -1.
     * @param numbers est la chaîne de caractères à transformer.
     * @return un entier.
     */
    public static int toIntIfValid(String numbers) {
        int result;
        if (!numbers.isEmpty()) {
            try {
                result = Integer.parseInt(numbers);
            } catch (NumberFormatException nfe) {
                result = -1;
            }
        }else {
                result = -1;
        }
        return result;
    }
}
