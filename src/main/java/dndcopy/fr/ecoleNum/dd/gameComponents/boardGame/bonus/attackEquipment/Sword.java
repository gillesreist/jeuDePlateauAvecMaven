package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment;

public class Sword extends Weapon {

    public Sword() {
        super("sword", 5);
    }
    public Sword(String name, int attackLevel){
        super(name, attackLevel);
    }

    @Override
    public String toString() {
        return "A nice sword is stuck in a rock.";
    }
}
