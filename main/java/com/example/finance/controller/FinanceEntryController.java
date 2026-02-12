package com.example.finance.controller;

import com.example.finance.dto.*;
import com.example.finance.model.EntryType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/entries")
public class FinanceEntryController {

    // CREATE
    @PostMapping
    public ResponseEntity<FinanceEntryResponse> createEntry(
            @RequestBody FinanceEntryRequest request) {
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // READ ALL + FILTERS
    @GetMapping
    public ResponseEntity<List<FinanceEntryResponse>> getAllEntries(
            @RequestParam(required = false) EntryType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(null);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<FinanceEntryResponse> getEntryById(
            @PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<FinanceEntryResponse> updateEntry(
            @PathVariable Long id,
            @RequestBody FinanceEntryRequest request) {
        return ResponseEntity.ok(null);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    // EXPORT CSV (BONUS)
    @GetMapping(value = "/export", produces = "text/csv")
    public ResponseEntity<String> exportEntriesAsCsv() {
        return ResponseEntity.ok("");
    }
}
