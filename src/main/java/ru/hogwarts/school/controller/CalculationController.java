package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.CalculationService;

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
        // Вызываем метод сервиса, который возвращает подготовленный результат
        Map<String, Object> result = calculationService.calculateSumWithParallelStream();
        return ResponseEntity.ok(result);
    }
}
