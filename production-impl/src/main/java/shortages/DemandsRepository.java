package shortages;

import dao.DemandDao;
import entities.DemandEntity;

import java.time.LocalDate;
import java.util.List;

public class DemandsRepository {
    private final DemandDao demandDao;

    public DemandsRepository(DemandDao demandDao) {
        this.demandDao = demandDao;
    }

    public Demands get(String productRefNo, LocalDate today) {
        List<DemandEntity> demands = demandDao.findFrom(today.atStartOfDay(), productRefNo);
        Demands demandsPerDay = new Demands(demands);
        return demandsPerDay;
    }
}
