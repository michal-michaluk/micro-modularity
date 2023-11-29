package shortages;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class DateRange implements Iterable<LocalDate> {
    private final List<LocalDate> dates;

    public DateRange(List<LocalDate> dates) {
        this.dates = dates;
    }

    public static DateRange of(LocalDate start, int daysAhead) {
        assert daysAhead > 0;
        return new DateRange(
                Stream.iterate(start, date -> date.plusDays(1))
                        .limit(daysAhead)
                        .toList()
        );
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return dates.iterator();
    }

    public LocalDate start() {
        return dates.get(0);
    }
}
