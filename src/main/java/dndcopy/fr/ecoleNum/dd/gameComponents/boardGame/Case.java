package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame;
import dndcopy.fr.ecoleNum.dd.character.Character;
import dndcopy.fr.ecoleNum.dd.character.Warrior;
import dndcopy.fr.ecoleNum.dd.exceptions.CharacterDeadException;
import dndcopy.fr.ecoleNum.dd.gameEngine.InteractionMenu;

public abstract class Case {
    protected InteractionMenu interactionMenu;
    public Case() {
        interactionMenu = new InteractionMenu();
    }

    public void interaction(Character character) throws CharacterDeadException {
        if (character instanceof Warrior) {
            System.out.println("Your warrior is going forward.");
        } else {
            System.out.println("Your sorcerer is going forward.");
        }
    }
}
