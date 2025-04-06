package ict4315_assignment_1;

import java.util.Objects;

/**
 * Represents monetary values, stored in cents to ensure precision.
 */
public class Money{
    
    private double amount;
    private String currency;

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
    
    @Override
    public String toString() {
        return "Money{" +
                "Amount='" + amount + '\'' +
                ", Currency='" + currency +
                '}';
    }
}
