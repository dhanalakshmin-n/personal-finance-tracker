package com.example.finance.controller;

import com.example.finance.dto.PeriodSummaryItem;
import com.example.finance.dto.SummaryResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/summary")
public class SummaryController {

    // OVERALL SUMMARY
    @GetMapping
    public ResponseEntity<SummaryResponse> getOverallSummary(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(null);
    }

    // MONTHLY SUMMARY (BONUS)
    @GetMapping("/monthly")
    public ResponseEntity<List<PeriodSummaryItem>> getMonthlySummary(
            @RequestParam int year) {
        return ResponseEntity.ok(null);
    }

    // YEARLY SUMMARY (BONUS)
    @GetMapping("/yearly")
    public ResponseEntity<List<PeriodSummaryItem>> getYearlySummary() {
        return ResponseEntity.ok(null);
    }
}
