package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus;

import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.Case;

public class Bonus extends Case {
    private String name;
    protected boolean inInventory;

    public Bonus() {
        this("Bonus");
    }

    public Bonus(String name) {
        super();
        this.name = name;
        inInventory = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInInventory() {
        return inInventory;
    }

    public void setInInventory(boolean inInventory) {
        this.inInventory = inInventory;
    }
}
