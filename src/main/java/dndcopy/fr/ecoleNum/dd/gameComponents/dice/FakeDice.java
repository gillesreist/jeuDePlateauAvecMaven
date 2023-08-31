package dndcopy.fr.ecoleNum.dd.gameComponents.dice;

public class FakeDice implements Dice{

    private int value;

    public FakeDice() {
        value = 1;
    }
    @Override
    public void throwDice() {
    }

    public int getValue() {
        return value;
    }
}
