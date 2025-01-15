package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.CalculationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calculation")
public class CalculationController {
    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping("/sum-parallel")
    public ResponseEntity<Map<String, Object>> getSumParallel() {
        long startTime = System.currentTimeMillis();
        int sum = calculationService.calculateSumWithParallelStream();
        long endTime = System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("sum", sum);
        response.put("timeTaken", (endTime - startTime) + " ms");

        return ResponseEntity.ok(response);
    }
}
