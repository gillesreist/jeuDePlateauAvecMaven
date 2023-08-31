package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus.attackEquipment;

public class ThunderBolt extends Spell{
    public ThunderBolt() {
        super("thunderBolt", 2);
    }
    public ThunderBolt(String name, int attackLevel){
        super(name, attackLevel);
    }

    @Override
    public String toString() {
        return "You found a scroll with lightning symbols on it.";
    }
}
