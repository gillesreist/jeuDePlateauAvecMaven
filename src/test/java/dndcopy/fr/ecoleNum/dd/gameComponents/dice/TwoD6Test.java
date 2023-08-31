package dndcopy.fr.ecoleNum.dd.gameComponents.dice;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TwoD6Test {
    @Test
    public void testTwoD6Value() {
        TwoD6 dice = new TwoD6();
        boolean asIntended = true;
        int sameValueCounter = 0;
        int previousValue = 0;
        for (int i = 0; i < 100; i++) {
            dice.throwDice();
            if (dice.getValue()<2 || dice.getValue()>12) {
                asIntended = false;
            }
            if (dice.getValue()==previousValue) {
                sameValueCounter++;
            } else {
                sameValueCounter = 0;
            }
            if (sameValueCounter == 5) {
                asIntended = false;
            }
            previousValue = dice.getValue();
        }
        assertTrue(asIntended);
    }
}
