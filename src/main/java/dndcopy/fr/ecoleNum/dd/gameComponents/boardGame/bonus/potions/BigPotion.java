package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.potions;

public class BigPotion extends Potion{
    public BigPotion() {
        super("bigPotion", 5);
    }

    public BigPotion(String name, int healthRecovery){
        super(name, healthRecovery);
    }

    @Override
    public String toString() {
        return "You see a huge potion in the grass";
    }
}
