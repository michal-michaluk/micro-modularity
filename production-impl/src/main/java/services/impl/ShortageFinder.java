package services.impl;

import entities.ShortageEntity;
import external.CurrentStock;
import shortages.*;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinder {

    private final ProductionOutputsRepository productionOutputsRepository;
    private final DemandsRepository demandsRepository;

    public ShortageFinder(DemandsRepository demandsRepository, ProductionOutputsRepository productionOutputsRepository) {
        this.demandsRepository = demandsRepository;
        this.productionOutputsRepository = productionOutputsRepository;
    }

    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        ProductionOutputs outputs = productionOutputsRepository.get(productRefNo, today);
        Demands demands = demandsRepository.get(productRefNo, today);
        DateRange dates = DateRange.of(today, daysAhead);

        long level = stock.getLevel();

        Algorithm algorithm = new Algorithm(productRefNo, outputs, demands, dates, level);
        Shortage shortages = algorithm.predict();
        return shortages.entities();
    }

}
