package com.carrental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.model.Bill;
import com.carrental.service.BillService;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<Bill> generateBill(@RequestParam Long bookingId, @RequestParam Double damageCharges) {
        Bill bill = billService.generateBill(bookingId, damageCharges);
        return ResponseEntity.ok(bill);
    }
}
