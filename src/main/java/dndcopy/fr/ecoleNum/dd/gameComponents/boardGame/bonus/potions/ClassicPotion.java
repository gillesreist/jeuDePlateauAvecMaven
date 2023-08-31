package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.potions;

public class ClassicPotion extends Potion{
    public ClassicPotion() {
        super("classicPotion", 2);
    }

    public ClassicPotion(String name, int healthRecovery){
      super(name, healthRecovery);
    }

    @Override
    public String toString() {
        return "You see an ordinary potion in the grass";
    }

}
