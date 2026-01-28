package com.example.finance.controller;

import com.example.finance.dto.*;
import com.example.finance.model.EntryType;
import com.example.finance.service.FinanceEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Finance Entries", description = "Create, read, update, delete entries and view summaries.")
//get finance entry by id
public class FinanceEntryController {

    private final FinanceEntryService service;

    public FinanceEntryController(FinanceEntryService service) {
        this.service = service;
    }

    @Operation(
            summary = "Create income or expense entry",
            description = """
                    Create a new income or expense entry.
                    
                    Input instructions:
                    - amount: Positive decimal value (e.g. 1200.50). Do not use commas.
                    - category: Short label like "Food", "Rent", "Salary".
                    - type: Must be exactly INCOME or EXPENSE.
                    - date: Use yyyy-MM-dd format (for example 2026-01-28).
                    - description: Optional note, at most 500 characters.
                    """
    )
    @ApiResponse(
            responseCode = "201",
            description = "Entry created successfully.",
            content = @Content(schema = @Schema(implementation = FinanceEntryResponse.class))
    )
    @PostMapping("/entries")
    public ResponseEntity<FinanceEntryResponse> createEntry(
            @Valid @RequestBody FinanceEntryRequest request) {
        FinanceEntryResponse response = service.createEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get all entries with optional filters",
            description = """
                    Returns all entries.
                    
                    Optional query parameters:
                    - type: INCOME or EXPENSE
                    - category: Exact category name (case-insensitive)
                    - startDate: Only include entries on or after this date (yyyy-MM-dd)
                    - endDate: Only include entries on or before this date (yyyy-MM-dd)
                    """
    )
    @GetMapping("/entries")
    public ResponseEntity<List<FinanceEntryResponse>> getEntries(
            @Parameter(description = "Filter by type: INCOME or EXPENSE.", example = "INCOME")
            @RequestParam(name = "type", required = false) EntryType type,

            @Parameter(description = "Filter by category name. Case-insensitive.", example = "Food")
            @RequestParam(name = "category", required = false) String category,

            @Parameter(description = "Include entries on or after this date (yyyy-MM-dd).", example = "2026-01-01")
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

            @Parameter(description = "Include entries on or before this date (yyyy-MM-dd).", example = "2026-01-31")
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<FinanceEntryResponse> responses = service.getEntries(type, category, startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get single entry by id")
    @GetMapping("/entries/{id}")
    public ResponseEntity<FinanceEntryResponse> getEntryById(
            @PathVariable("id") Long id) {
        FinanceEntryResponse response = service.getEntryById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing entry",
            description = """
                    Completely replace the existing entry with the provided data.
                    
                    Input instructions are the same as for POST /entries.
                    """
    )
    @PutMapping("/entries/{id}")
    public ResponseEntity<FinanceEntryResponse> updateEntry(
            @PathVariable("id") Long id,
            @Valid @RequestBody FinanceEntryRequest request) {
        FinanceEntryResponse response = service.updateEntry(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an entry by id")
    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable("id") Long id) {
        service.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get total income, total expenses and balance",
            description = """
                    Returns overall totals for income, expenses and balance.
                    
                    Optional query parameters:
                    - startDate: Only include entries on or after this date (yyyy-MM-dd)
                    - endDate: Only include entries on or before this date (yyyy-MM-dd)
                    """
    )
    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary(
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        SummaryResponse summary = service.getOverallSummary(startDate, endDate);
        return ResponseEntity.ok(summary);
    }

    @Operation(
            summary = "Get monthly summary for a year",
            description = "Returns one summary per month for the given year."
    )
    @GetMapping("/summary/monthly")
    public ResponseEntity<List<PeriodSummaryItem>> getMonthlySummary(
            @RequestParam(name = "year") int year) {
        List<PeriodSummaryItem> items = service.getMonthlySummary(year);
        return ResponseEntity.ok(items);
    }

    @Operation(
            summary = "Get yearly summary",
            description = "Returns one summary per year across all stored entries."
    )
    @GetMapping("/summary/yearly")
    public ResponseEntity<List<PeriodSummaryItem>> getYearlySummary() {
        List<PeriodSummaryItem> items = service.getYearlySummary();
        return ResponseEntity.ok(items);
    }

    @Operation(
            summary = "Export all entries as CSV",
            description = """
                    Exports all entries as a CSV file.
                    
                    How to use:
                    - In a browser: open /api/v1/entries/export and your browser will download the CSV.
                    - In Postman: call GET /api/v1/entries/export and save the response body as a .csv file.
                    """
    )
    @GetMapping(value = "/entries/export", produces = "text/csv")
    public ResponseEntity<String> exportCsv() {
        String csv = service.exportEntriesAsCsv();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"finance-entries.csv\"");
        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }
}

