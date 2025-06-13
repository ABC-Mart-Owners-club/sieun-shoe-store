package org.shoestore.product.model.type;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import org.shoestore.Util;
import org.shoestore.product.model.Stock;

public enum StockDiscountType {
    ORIGINAL(price -> price),
    ONE_MONTH_OVER(price -> price * 0.5),
    ONE_WEEK_OVER(price -> price * 0.3),
    ;

    private final Function<Double, Double> calculateDiscount;

    StockDiscountType(Function<Double, Double> calculateDiscount) {
        this.calculateDiscount = calculateDiscount;
    }

    public static StockDiscountType getStockDiscountType(Stock stock) {
        LocalDateTime storedDatetime = Util.milliToLocalDateTime(stock.getStoredDate());
        long daysBetween = ChronoUnit.DAYS.between(storedDatetime, LocalDateTime.now());

        if (daysBetween >= 30) {
            return StockDiscountType.ONE_MONTH_OVER;
        }

        if (daysBetween >= 7) {
            return StockDiscountType.ONE_WEEK_OVER;
        }
        return StockDiscountType.ORIGINAL;
    }

    public double calculate(double amount) {
        return this.calculateDiscount.apply(amount);
    }
}
