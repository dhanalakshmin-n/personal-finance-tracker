package com.example.finance.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Overall financial summary: total income, total expenses and balance.")
public class SummaryResponse {

    @Schema(description = "Total income over the selected period.", example = "5000.00")
    private BigDecimal totalIncome;

    @Schema(description = "Total expenses over the selected period.", example = "3200.00")
    private BigDecimal totalExpenses;

    @Schema(description = "Balance = totalIncome - totalExpenses.", example = "1800.00")
    private BigDecimal balance;

    public SummaryResponse() {
    }

    public SummaryResponse(BigDecimal totalIncome, BigDecimal totalExpenses) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.balance = totalIncome.subtract(totalExpenses);
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

