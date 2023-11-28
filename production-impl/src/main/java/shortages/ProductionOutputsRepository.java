package shortages;

import dao.ProductionDao;
import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionOutputsRepository {
    private final ProductionDao productionDao;

    public ProductionOutputsRepository(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    public ProductionOutputs get(String productRefNo, LocalDate today) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, today.atStartOfDay());
        Map<LocalDate, Long> summed = productions.stream().collect(Collectors.groupingBy(
                productionEntity -> productionEntity.getStart().toLocalDate(),
                Collectors.summingLong(ProductionEntity::getOutput)
        ));
        return new ProductionOutputs(summed);
    }
}
