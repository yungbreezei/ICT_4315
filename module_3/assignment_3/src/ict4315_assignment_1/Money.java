package ict4315_assignment_1;

import java.util.Objects;

/**
 * Represents monetary values, stored in cents to ensure precision.
 * Use for simple arithmetic and formatting in financial operations.
 */
public class Money{
    
	private final double amount;
	private final String currency;
    
    /**
     * Constructs a Money object.\
     */
    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /*
     * Getters
     */  
    public double getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    
    /*
     * Adds two Money amounts of the same currency.
     */
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies do not match");
        }
        return new Money(this.amount + other.amount, this.currency);
    }
    
    /**
     * Subtracts another Money amount from this one.
     *
     * @param other another Money object
     * @return a new Money representing the result
     */
    public Money subtract(Money other) {
        validateCurrencyMatch(other);
        return new Money(this.amount - other.amount, this.currency);
    }

    /**
     * Round Money output To TwoDecimals
     *
     * @return round in times() and arithmetic methods:
     * avoid stuff like: USD 8.79999999999999
     */
    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * Multiplies this Money amount by a scalar value.
     *
     * @param multiplier a scalar (e.g., 0.8 for 20% discount)
     * @return a new Money object
     */
    public Money times(double multiplier) {
    	return new Money(roundToTwoDecimals(this.amount * multiplier), this.currency);
    }
       
    /**
     * Compares this Money object with another based on amount.
     *
     * @param other the Money to compare to
     * @return -1, 0, or 1
     */
    public int compareTo(Money other) {
        validateCurrencyMatch(other);
        return Double.compare(
            roundToTwoDecimals(this.amount),
            roundToTwoDecimals(other.amount)
        );
    }



    private void validateCurrencyMatch(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies do not match: " + 
        this.currency + " vs " + other.currency);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return Double.compare(
                   roundToTwoDecimals(this.amount), 
                   roundToTwoDecimals(money.amount)
               ) == 0 && currency.equals(money.currency);
    }


    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
    
    @Override
    public String toString() {
        return String.format("%s %.2f", currency, amount);
    }
}
