package com.example.finance.dto;

import com.example.finance.model.EntryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Schema(description = "Request payload to create or update an income/expense entry.")
public class FinanceEntryRequest {

    @Schema(
            description = "Amount of the income or expense. Must be positive.",
            example = "1500.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01.")
    @Digits(integer = 17, fraction = 2, message = "Amount must have up to 2 decimal places.")
    private BigDecimal amount;

    @Schema(
            description = "Category of the entry (e.g. Food, Entertainment, Salary).",
            example = "Food",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Category is required.")
    @Size(max = 100, message = "Category must be at most 100 characters.")
    private String category;

    @Schema(
            description = "Type of the entry: INCOME or EXPENSE.",
            example = "EXPENSE",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Type is required and must be INCOME or EXPENSE.")
    private EntryType type;

    @Schema(
            description = "Date of the transaction in yyyy-MM-dd format.",
            example = "2026-01-28",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Date is required.")
    @PastOrPresent(message = "Date cannot be in the future.")
    private LocalDate date;

    @Schema(
            description = "Optional description, such as merchant name or note.",
            example = "Dinner with friends",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 500, message = "Description must be at most 500 characters.")
    private String description;

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

