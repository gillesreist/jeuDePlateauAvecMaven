package dndcopy.fr.ecoleNum.dd.gameComponents.dice;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class D6Test {
    @Test
    public void testD6Value() {
        D6 dice = new D6();
        boolean asIntended = true;
        int sameValueCounter = 0;
        int previousValue = 0;
        for (int i = 0; i < 100; i++) {
            dice.throwDice();
            if (dice.getValue()<1 || dice.getValue()>6) {
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