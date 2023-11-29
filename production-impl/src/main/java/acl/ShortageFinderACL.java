package acl;

import external.CurrentStock;
import shortages.*;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinderACL {

    private final ProductionOutputsRepository productionOutputsRepository;
    private final DemandsRepository demandsRepository;

    public ShortageFinderACL(DemandsRepository demandsRepository, ProductionOutputsRepository productionOutputsRepository) {
        this.demandsRepository = demandsRepository;
        this.productionOutputsRepository = productionOutputsRepository;
    }

    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        DateRange dates = DateRange.of(today, daysAhead);
        ShortagePredictionService service = new ShortagePredictionService(
                productionOutputsRepository,
                demandsRepository,
                productRefNo1 -> stock.getLevel()
        );
        Shortage shortages = service.predict(productRefNo, dates);
        return shortages.entities();
    }

}
