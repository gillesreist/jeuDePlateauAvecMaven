package dndcopy.fr.ecoleNum.dd.gameComponents.dice;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FakeDiceTest
{
    @Test
    public void testFakeDiceValue()
    {
        FakeDice dice = new FakeDice();
        boolean asIntended = true;
        for (int i=0; i<100; i++) {
            dice.throwDice();
            if (dice.getValue()!=1) {
                asIntended = false;
            }
        }
        assertTrue( asIntended );
    }
}