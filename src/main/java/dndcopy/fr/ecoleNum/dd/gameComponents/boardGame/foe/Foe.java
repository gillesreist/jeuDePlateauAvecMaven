package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.foe;

import dndcopy.fr.ecoleNum.dd.character.Character;
import dndcopy.fr.ecoleNum.dd.exceptions.CharacterDeadException;
import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.Case;

public class Foe extends Case {

    private int lifeLevel;
    private int attackStrength;
    private boolean fightInProgress;

    public Foe() {
        this(1,1);
    }

    public Foe(int lifeLevel, int attackStrength) {
        super();
        this.lifeLevel = lifeLevel;
        this.attackStrength = attackStrength;
    }

    @Override
    public String toString() {
        return "A monster is in the way. ";
    }

    @Override
    public void interaction(Character character) throws CharacterDeadException {
        fight(character);
    }

    protected void fight(Character character) throws CharacterDeadException {
        System.out.println("You cannot avoid the fight.");
        fightInProgress = true;
        while (fightInProgress) {
            character.attack(this);
            if (getLifeLevel() <= 0) {
                System.out.println("You killed him.");
                fightInProgress = false;
            } else {
                System.out.println("He got " + getLifeLevel() + " life points left.");
                character.defend(this);
                if (character.getLifeLevel() <= 0) {
                    System.out.println("I'm very sorry to inform you that you are now dead.");
                    throw new CharacterDeadException("Character is dead");
                } else {
                    System.out.println("You have " + character.getLifeLevel() + " life points left.");
                    if (!(this instanceof GiantCockroach)) {
                        System.out.println("Do you want to flee ?");
                        if (interactionMenu.yesOrNo()) {
                            fightInProgress = false;
                            character.setHasFleed(true);
                        } else {
                            System.out.println("You valiantly continue your fight.");
                        }
                    } else {
                        System.out.println("You cannot flee.");
                    }
                }
            }
        }
    }

    public int chooseAttackEquipment(Character character) {
        return interactionMenu.getAttackLevelFromInventory(character.getAttackInventory());
    }

    public int getLifeLevel() {
        return lifeLevel;
    }

    public void setLifeLevel(int lifeLevel) {
        this.lifeLevel = lifeLevel;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }
}
