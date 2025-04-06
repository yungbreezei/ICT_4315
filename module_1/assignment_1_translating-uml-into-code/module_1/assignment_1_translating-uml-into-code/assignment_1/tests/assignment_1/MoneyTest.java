/* Bria Wright
 * 
 * ICT 4315
 * Week 1 Assignment: Translating UML into Code
 * April 6, 2025
 */

package assignment_1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ict4315_assignment_1.Money;

class MoneyTest {

    private Money money;

    @BeforeEach
    public void setUp() {
        money = new Money(10.50, "USD");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(10.50, money.getAmount());
        assertEquals("USD", money.getCurrency());
    }

    @Test
    public void testSetters() {
        money.setAmount(15.75);
        money.setCurrency("EUR");

        assertEquals(15.75, money.getAmount());
        assertEquals("EUR", money.getCurrency());
    }

    @Test
    public void testEqualsAndHashCode() {
        Money sameMoney = new Money(10.50, "USD");
        Money differentMoney = new Money(20.00, "USD");

        assertEquals(money, sameMoney);
        assertEquals(money.hashCode(), sameMoney.hashCode());

        assertNotEquals(money, differentMoney);
    }

    @Test
    public void testToString() {
        String output = money.toString();
        assertTrue(output.contains("10.5") || output.contains("10.50"));
        assertTrue(output.contains("USD"));
    }
}
