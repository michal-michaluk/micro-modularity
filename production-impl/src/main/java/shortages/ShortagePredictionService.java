package shortages;

public class ShortagePredictionService {
    private final ProductionOutputsRepository outputs;
    private final DemandsRepository demands;
    private final WarehouseStockRepository warehouse;

    public ShortagePredictionService(ProductionOutputsRepository outputs, DemandsRepository demands, WarehouseStockRepository warehouse) {
        this.outputs = outputs;
        this.demands = demands;
        this.warehouse = warehouse;
    }

    public Shortage predict(String productRefNo, DateRange dates) {
        Algorithm algorithm = new Algorithm(
                productRefNo,
                outputs.get(productRefNo, dates.start()),
                demands.get(productRefNo, dates.start()),
                dates,
                warehouse.getCurrentStockLevel(productRefNo));

        Shortage shortages = algorithm.predict();
        return shortages;
    }
}
