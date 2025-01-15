package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CalculationService {
    public int calculateSumWithParallelStream() {
        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
    }
}
