package dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.bonus;

import dndcopy.fr.ecoleNum.dd.gameComponents.boardGame.Case;

public class Shield extends Case {
    String name;

    Shield() {
        name = "Dragon character.Shield";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
