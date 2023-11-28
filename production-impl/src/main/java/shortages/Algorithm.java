package shortages;

import java.time.LocalDate;

public class Algorithm {
    private final String productRefNo;
    private final ProductionOutputs outputs;
    private final Demands demandsPerDay;
    private final DateRange dates;
    private final long warehouse;

    public Algorithm(String productRefNo, ProductionOutputs outputs, Demands demandsPerDay, DateRange dates, long warehouse) {
        this.productRefNo = productRefNo;
        this.outputs = outputs;
        this.demandsPerDay = demandsPerDay;
        this.dates = dates;
        this.warehouse = warehouse;
    }

    public Shortage predict() {
        Shortage.Builder shortages = Shortage.builder(productRefNo);

        long level = warehouse;
        for (LocalDate day : dates) {
            Demands.DailyDemand demand = demandsPerDay.get(day);
            long produced = outputs.sumOfOutputs(day);
            long levelOnDelivery = demand.calculateLevelOnDelivery(level, produced);

            if (levelOnDelivery < 0) {
                shortages.add(day, levelOnDelivery);
            }
            long endOfDayLevel = demand.calculateEndOfDayLevel(level, produced);
            level = Math.max(endOfDayLevel, 0);
        }
        return shortages.build();
    }
}
