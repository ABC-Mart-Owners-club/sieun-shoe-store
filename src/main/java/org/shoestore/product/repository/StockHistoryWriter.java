package org.shoestore.product.repository;

import java.util.List;
import org.shoestore.product.model.Product;

public interface StockHistoryWriter {

    // 재고 사용 내역과 orderId, productId 정보 같이 저장
    void saleStock(Long orderId, List<Product> products);

    void saleStockFailure(Long orderId);

    void restoreStockByOrderId(Long orderId);

    void restoreStockFailureByOrderId(Long orderId);

    void restoreStockByProductId(Long orderId, Long productId);

    void restoreStockFailureByProductId(Long orderId, Long productId);
}
