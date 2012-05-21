package org.ad13.denarius.dto;

import java.math.BigDecimal;

public class StatisticsDTO {
    private BigDecimal overallFunds;
    private BigDecimal monthlyExpenditure;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyVariation;
    private BigDecimal monthlyDifference;

    public StatisticsDTO(BigDecimal overallFunds, BigDecimal monthlyExpenditure, BigDecimal monthlyIncome,
            BigDecimal monthlyVariation, BigDecimal monthlyDifference) {
        super();
        this.overallFunds = overallFunds;
        this.monthlyExpenditure = monthlyExpenditure;
        this.monthlyIncome = monthlyIncome;
        this.monthlyVariation = monthlyVariation;
        this.monthlyDifference = monthlyDifference;
    }

    public BigDecimal getOverallFunds() {
        return overallFunds;
    }

    /**
     * The overall amount of money the user currently has.
     * 
     * @param overallFunds
     */
    public void setOverallFunds(BigDecimal overallFunds) {
        this.overallFunds = overallFunds;
    }

    public BigDecimal getMonthlyExpenditure() {
        return monthlyExpenditure;
    }

    /**
     * The amount of money leaving a user's accounts based on transactional
     * data.
     * 
     * @param monthlyExpenditure
     */
    public void setMonthlyExpenditure(BigDecimal monthlyExpenditure) {
        this.monthlyExpenditure = monthlyExpenditure;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    /**
     * The amount of money entering a user's accounts based on transactional
     * data.
     * 
     * @param monthlyIncome
     */
    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getMonthlyVariation() {
        return monthlyVariation;
    }

    /**
     * The difference between the transactional expenditure and the recorded
     * difference in account value for this month.
     * 
     * @param monthlyVariation
     */
    public void setMonthlyVariation(BigDecimal monthlyVariation) {
        this.monthlyVariation = monthlyVariation;
    }

    public BigDecimal getMonthlyDifference() {
        return monthlyDifference;
    }

    /**
     * The difference between the initial value of the user's accounts and the
     * final value.
     * 
     * @param monthlyDifference
     */
    public void setMonthlyDifference(BigDecimal monthlyDifference) {
        this.monthlyDifference = monthlyDifference;
    }
}
