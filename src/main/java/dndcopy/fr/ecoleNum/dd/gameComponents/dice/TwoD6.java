package dndcopy.fr.ecoleNum.dd.gameComponents.dice;

import java.util.concurrent.ThreadLocalRandom;

public class TwoD6 implements Dice{
    private int value;

    public TwoD6(){
        value=2;
    }
    @Override
    public void throwDice() {
        int dice1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int dice2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        value = dice1+dice2;
    }

    public int getValue() {
        return value;
    }
}
