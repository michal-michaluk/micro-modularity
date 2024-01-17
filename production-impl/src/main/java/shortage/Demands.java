package shortage;

import entities.DemandEntity;
import enums.DeliverySchema;
import tools.Util;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DemandEntity> demandsPerDay;

    public Demands(List<DemandEntity> demands) {
        Map<LocalDate, DemandEntity> demandsPerDay = new HashMap<>();
        for (DemandEntity demand : demands) {
            demandsPerDay.put(demand.getDay(), demand);
        }
        this.demandsPerDay = Collections.unmodifiableMap(demandsPerDay);
    }

    public DailyDemand get(LocalDate day) {
        DemandEntity demand = demandsPerDay.get(day);
        if (demand == null) {
            return null;
        }
        return new DailyDemand(demand);
    }

    public static class DailyDemand {
        private final DemandEntity demand;

        public DailyDemand(DemandEntity demand) {
            this.demand = demand;
        }

        private DeliverySchema getDeliverySchema() {
            return Util.getDeliverySchema(demand);
        }

        private long getLevel() {
            return Util.getLevel(demand);
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            long levelOnDelivery;
            if (getDeliverySchema() == DeliverySchema.atDayStart) {
                levelOnDelivery = level - getLevel();
            } else if (getDeliverySchema() == DeliverySchema.tillEndOfDay) {
                levelOnDelivery = level - getLevel() + produced;
            } else if (getDeliverySchema() == DeliverySchema.every3hours) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
            return levelOnDelivery;
        }

        public long calculateEndOfDayLevel(long level, long produced) {
            return level + produced - getLevel();
        }
    }
}




