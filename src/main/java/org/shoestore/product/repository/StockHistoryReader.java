package org.shoestore.product.repository;

import java.util.HashMap;
import java.util.List;
import org.shoestore.product.model.Stock;

public interface StockHistoryReader {

    HashMap<Long, Stock> getProductStocks(List<Long> productIds);
}
