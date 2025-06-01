package org.shoestore.product.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JProgressBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shoestore.Util;
import org.shoestore.product.model.type.StockDiscountType;
import org.shoestore.product.model.type.Supplier;
import org.shoestore.testprep.TestDomainModelPrep;

class ProductTest {

    @Test
    void 한달재고할인() {

        Stock stock1 = new Stock(1L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(3)));
        Stock stock2 = new Stock(4L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(31)));
        Stock stock3 = new Stock(2L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(29)));

        List<Stock> stocks = new ArrayList<>(List.of(new Stock[]{stock1, stock2, stock3}));
        Product product = new Product(1L, "나이키", "조던", 150000, new StockHistory(stocks));

        StockDiscountType stockDiscountType = product.getStockDiscountType();
        assertThat(stockDiscountType).isEqualTo(StockDiscountType.ONE_MONTH_OVER);
    }

    @Test
    void 일주재고할인() {

        Stock stock1 = new Stock(1L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(3)));
        Stock stock2 = new Stock(4L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(10)));
        Stock stock3 = new Stock(2L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(29)));

        List<Stock> stocks = new ArrayList<>(List.of(new Stock[]{stock1, stock2, stock3}));
        Product product = new Product(1L, "나이키", "조던", 150000, new StockHistory(stocks));

        StockDiscountType stockDiscountType = product.getStockDiscountType();
        assertThat(stockDiscountType).isEqualTo(StockDiscountType.ONE_WEEK_OVER);
    }

    @Test
    void 재고할인없음() {

        Stock stock1 = new Stock(1L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(3)));
        Stock stock2 = new Stock(4L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(6)));
        Stock stock3 = new Stock(2L, Supplier.CRAZY_DISTRIBUTOR, 3L, 3L, Util.localDateTimeToMilli(LocalDateTime.now().minusDays(2)));

        List<Stock> stocks = new ArrayList<>(List.of(new Stock[]{stock1, stock2, stock3}));
        Product product = new Product(1L, "나이키", "조던", 150000, new StockHistory(stocks));

        StockDiscountType stockDiscountType = product.getStockDiscountType();
        assertThat(stockDiscountType).isEqualTo(StockDiscountType.ORIGINAL);
    }
}