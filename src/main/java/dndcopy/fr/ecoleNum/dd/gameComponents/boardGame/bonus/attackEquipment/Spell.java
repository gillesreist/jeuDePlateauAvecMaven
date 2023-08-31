package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment;

import dndcopy.fr.ecoleNum.dd.character.Character;
import dndcopy.fr.ecoleNum.dd.character.Warrior;

public class Spell extends AttackEquipment {

    public Spell() {
        this("spell",1);
    }

    public Spell(String name, int attackLevel) {
        super(name, attackLevel);
    }

    @Override
    public String toString() {
        return "You found a scroll with strange symbols on it.";
    }

    @Override
    public void interaction(Character character) {
        if (character instanceof Warrior) {
            System.out.println("If only you knew how to read...");
        } else {
            System.out.println("You could learn something here...");
            equip(character);
        }
    }
}
