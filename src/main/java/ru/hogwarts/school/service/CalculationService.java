package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CalculationService {
    public Map<String, Object> calculateSumWithParallelStream() {
        long startTime = System.currentTimeMillis(); // Начало замера времени
        // Параллельный расчет суммы
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        long endTime = System.currentTimeMillis(); // Конец замера времени
        // Формируем результат
        Map<String, Object> result = new HashMap<>();
        result.put("sum", sum);
        result.put("timeTaken", (endTime - startTime) + " ms");
        return result;
    }
}
