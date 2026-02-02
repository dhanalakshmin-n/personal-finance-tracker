package com.example.finance.dto;

import com.example.finance.model.EntryType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Response representation of a finance entry.")
public class FinanceEntryResponse {

    @Schema(description = "Unique identifier of the entry.", example = "1")
    private Long id;

    @Schema(description = "Amount of the income or expense.", example = "1500.00")
    private BigDecimal amount;

    @Schema(description = "Category of the entry.", example = "Food")
    private String category;

    @Schema(description = "Type of the entry.", example = "EXPENSE")
    private EntryType type;

    @Schema(description = "Date of the entry.", example = "2026-01-28")
    private LocalDate date;

    @Schema(description = "Optional description.", example = "Dinner with friends")
    private String description;

    public FinanceEntryResponse() {
    }

    public FinanceEntryResponse(Long id, BigDecimal amount, String category, EntryType type, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public EntryType getType() {
        return type;
    }

    public void setType(EntryType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

