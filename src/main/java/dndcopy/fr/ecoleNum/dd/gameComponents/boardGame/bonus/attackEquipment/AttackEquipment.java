package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment;

import dndcopy.fr.ecoleNum.dd.character.Character;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.Bonus;

public class AttackEquipment extends Bonus {
    private int attackLevel;

    public AttackEquipment() {
        this("attackEquipment",1);
    }

    public AttackEquipment(String name, int attackLevel) {
        super(name);
        this.attackLevel = attackLevel;
    }

    @Override
    public String toString() {
        return "Something useful just lying on the side of the road. Weird...";
    }

    public int getAttackLevel() {
        return attackLevel;
    }

    public void setAttackLevel(int attackLevel) {
        this.attackLevel = attackLevel;
    }

    protected void equip(Character character) {
        boolean equip = false;
        System.out.println("It has an attack level of " + this.getAttackLevel());
        System.out.println("Do you want to pick it up ?");
        equip = interactionMenu.yesOrNo();

        if (equip) {
            if (character.getAttackInventorySize() < 2) {
                character.addToAttackInventory(this);
                System.out.println("You put it in your backpack.");
            } else {
                interactionMenu.removeFromInventory(character.getAttackInventory());
                if (character.getAttackInventorySize() < 2) {
                    character.addToAttackInventory(this);
                    this.inInventory = true;
                    System.out.println("You put it in your backpack.");
                } else {
                    System.out.println("It is not worth it.");
                }
            }
        } else {
            System.out.println("It is not worth it.");
        }
    }
}
