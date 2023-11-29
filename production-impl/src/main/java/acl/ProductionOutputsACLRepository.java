package acl;

import dao.ProductionDao;
import entities.ProductionEntity;
import shortages.ProductionOutputs;
import shortages.ProductionOutputsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionOutputsACLRepository implements ProductionOutputsRepository {
    private final ProductionDao productionDao;

    public ProductionOutputsACLRepository(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    @Override
    public ProductionOutputs get(String productRefNo, LocalDate today) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, today.atStartOfDay());
        Map<LocalDate, Long> summed = productions.stream().collect(Collectors.groupingBy(
                productionEntity -> productionEntity.getStart().toLocalDate(),
                Collectors.summingLong(ProductionEntity::getOutput)
        ));
        return new ProductionOutputs(summed);
    }
}
