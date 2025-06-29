package org.shoestore.product.usecase;

import java.util.HashMap;
import java.util.List;
import org.shoestore.order.model.Order;
import org.shoestore.product.lock.DistributedLock;
import org.shoestore.product.lock.RedisDistributedLock;
import org.shoestore.product.model.Product;
import org.shoestore.product.model.Stock;
import org.shoestore.product.repository.ProductReader;
import org.shoestore.product.repository.StockHistoryReader;
import org.shoestore.product.repository.StockHistoryWriter;

public class ProductUseCase {

    private final ProductReader productReader;
    private final StockHistoryWriter stockHistoryWriter;
    private final StockHistoryReader stockHistoryReader;

    public ProductUseCase(ProductReader productReader, StockHistoryWriter stockHistoryWriter, StockHistoryReader stockHistoryReader) {
        this.productReader = productReader;
        this.stockHistoryWriter = stockHistoryWriter;
        this.stockHistoryReader = stockHistoryReader;
    }

    /**
     * 상품 목록 조회
     */
    public List<Product> getProductsByProductIds(List<Long> productIds) {
        return this.productReader.getProductsByIds(productIds);
    }

    /**
     * 상품 조회
     */
    public Product getProductById(Long productId) {
        return this.productReader.getProductById(productId);
    }

    /**
     * 상품 판매 처리
     */
    public void sale(Order order, List<Product> products) {
        products.forEach(Product::sale);
        stockHistoryWriter.saleStock(order.getOrderId(), products);
    }

    /**
     * 상품 판매 실패 보상 로직
     */
    public void saleFailure(Order order) {
        stockHistoryWriter.saleStockFailure(order.getOrderId());
    }

    /**
     * 주문 취소시 재입고 처리
     */
    public void cancel(Order order) {
        stockHistoryWriter.restoreStockByOrderId(order.getOrderId());
    }

    /**
     * 재입고 실패 보상로직
     */
    public void cancelFailure(Order order) {
        stockHistoryWriter.restoreStockFailureByOrderId(order.getOrderId());
    }

    /**
     * 부분취소 재입고 처리
     */
    public void partialCancel(Order order, Product product) {
        stockHistoryWriter.restoreStockByProductId(order.getOrderId(), product.getProductId());
    }

    /**
     * 부분취소 재입고 실패 보상로직
     */
    public void partialCancelFailure(Order order, Product product) {
        stockHistoryWriter.restoreStockFailureByProductId(order.getOrderId(), product.getProductId());
    }

    /**
     * 입고 처리
     */
    public void restock(Product product, Stock stock) {
        stockHistoryWriter.restock(product.getProductId(), stock);
    }

    /**
     * 재고 조회
     */
    public HashMap<Long, Stock> getProductStock(List<Long> productIds) {
        return stockHistoryReader.getProductStocks(productIds);
    }
}
