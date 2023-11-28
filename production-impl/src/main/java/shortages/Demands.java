package shortages;

import enums.DeliverySchema;

import java.time.LocalDate;
import java.util.Map;

public class Demands {

    private static final DailyDemand NO_DEMAND = new DailyDemand(0, DeliverySchema.tillEndOfDay);

    private final Map<LocalDate, DailyDemand> demands;

    public Demands(Map<LocalDate, DailyDemand> demands) {
        this.demands = demands;
    }

    public DailyDemand get(LocalDate day) {
        return demands.getOrDefault(day, NO_DEMAND);
    }

    public static class DailyDemand {
        private final long demand;
        private final DeliverySchema schema;

        public DailyDemand(long level, DeliverySchema schema) {
            this.demand = level;
            this.schema = schema;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            if (schema == DeliverySchema.atDayStart) {
                return level - demand;
            } else if (schema == DeliverySchema.tillEndOfDay) {
                return level - demand + produced;
            } else {
                if (schema == DeliverySchema.every3hours) {
                    // TODO WTF ?? we need to rewrite that app :/
                    throw new UnsupportedOperationException();
                } else {
                    // TODO implement other variants
                    throw new UnsupportedOperationException();
                }
            }
        }

        public long calculateEndOfDayLevel(long level, long produced) {
            return level + produced - demand;
        }
    }
}
