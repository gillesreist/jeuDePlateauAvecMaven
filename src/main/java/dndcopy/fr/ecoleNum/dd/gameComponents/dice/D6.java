package dndcopy.fr.ecoleNum.dd.gameComponents.dice;

import java.util.concurrent.ThreadLocalRandom;

public class D6 implements Dice {
    private int value;

    public D6(){
        value = 1;
    }
    @Override
    public void throwDice() {
        value = ThreadLocalRandom.current().nextInt(1, 6 + 1);
    }

    public int getValue() {
        return value;
    }

}
