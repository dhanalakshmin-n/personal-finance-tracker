package com.example.finance.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Monthly or yearly summary item.")
public class PeriodSummaryItem {

    @Schema(description = "Year of the summary.", example = "2026")
    private int year;

    @Schema(description = "Month of the summary, 1-12. For yearly summaries this may be 0.", example = "1")
    private int month;

    @Schema(description = "Total income for this period.", example = "3000.00")
    private BigDecimal totalIncome;

    @Schema(description = "Total expenses for this period.", example = "2200.00")
    private BigDecimal totalExpenses;

    @Schema(description = "Balance (income - expenses) for this period.", example = "800.00")
    private BigDecimal balance;

    public PeriodSummaryItem() {
    }

    public PeriodSummaryItem(int year, int month, BigDecimal totalIncome, BigDecimal totalExpenses) {
        this.year = year;
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.balance = totalIncome.subtract(totalExpenses);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

