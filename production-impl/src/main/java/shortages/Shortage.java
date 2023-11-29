package shortages;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shortage {
    private final List<ShortageEntity> entities;

    public Shortage(List<ShortageEntity> entities) {
        this.entities = entities;
    }

    public static Builder builder(String productRefNo) {
        return new Builder(productRefNo);
    }

    public List<ShortageEntity> entities() {
        return entities;
    }

    public static class Builder {
        private final String productRefNo;
        private final List<ShortageEntity> entities = new ArrayList<>();

        public Builder(String productRefNo) {
            this.productRefNo = productRefNo;
        }

        public Shortage build() {
            return new Shortage(entities);
        }

        public void add(LocalDate day, long missing) {
            ShortageEntity entity = new ShortageEntity();
            entity.setRefNo(productRefNo);
            entity.setFound(LocalDate.now());
            entity.setAtDay(day);
            entity.setMissing(Math.abs(missing));
            entities.add(entity);
        }
    }
}
