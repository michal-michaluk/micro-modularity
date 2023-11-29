package shortages;

public interface WarehouseStockRepository {
    long getCurrentStockLevel(String productRefNo);
}
