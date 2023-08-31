package dndcopy.fr.ecoleNum.dd.character;

public class Sorcerer extends Character {

    public Sorcerer() {
        super();
    }

    public Sorcerer(String name) {
        super(name);
    }

    public Sorcerer(String name, int lifeLevel, int attackStrength) {
        super(name, lifeLevel, attackStrength);
    }

    protected void setCharacterMinMax() {
        setMinHealth(3);
        setMaxHealth(8);
        setMinStrength(6);
        setMaxStrength(15);
        setLifeLevel(getMinHealth());
        setAttackStrength(getMinStrength());
    }

}

