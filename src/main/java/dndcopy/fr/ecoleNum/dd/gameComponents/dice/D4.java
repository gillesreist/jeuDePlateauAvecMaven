package dndcopy.fr.ecoleNum.dd.gameComponents.dice;

import java.util.concurrent.ThreadLocalRandom;

public class D4 implements Dice {
    private int value;
    public D4(){
        value = 1;
    }
    @Override
    public void throwDice() {
        value = ThreadLocalRandom.current().nextInt(1, 4 + 1);
    }

    public int getValue() {
        return value;
    }
}
