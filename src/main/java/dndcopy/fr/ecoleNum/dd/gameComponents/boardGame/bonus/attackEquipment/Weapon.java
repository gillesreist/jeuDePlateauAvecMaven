package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment;
import dndcopy.fr.ecoleNum.dd.character.Character;
import dndcopy.fr.ecoleNum.dd.character.Warrior;

public class Weapon extends AttackEquipment {

    public Weapon() {
        this("weapon",1);
    }

    public Weapon(String name, int attackLevel) {
        super(name, attackLevel);
    }

    @Override
    public String toString() {
        return "A weapon just lying on the side of the road. Weird...";
    }


    @Override
    public void interaction(Character character) {
        if (character instanceof Warrior) {
            System.out.println("You have some ideas on how to use this tool. And they are all bloody.");
            equip(character);
        } else {
            System.out.println("You don't have the strength to even lift such a thing.");
        }
    }
}
