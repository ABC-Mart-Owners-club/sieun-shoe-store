package org.shoestore.order.model;

import java.util.List;
import java.util.Optional;
import org.shoestore.user.model.User;
import org.shoestore.order.model.vo.Buyer;
import org.shoestore.product.model.Product;

public class Order {

    private final Long orderId;
    private final List<OrderLine> orderLines; // 주문 상세내역
    private final Buyer buyer;

    // region constructor

    public Order(Long orderId, List<OrderLine> orderLines, Buyer buyer) {
        this.orderId = orderId;
        this.orderLines = orderLines;
        this.buyer = buyer;
    }

    public Order(List<Product> products, User user) {
        this.orderId = null;
        this.orderLines = products.stream().map(OrderLine::new).toList();
        this.buyer = new Buyer(user);
    }
    // endregion

    // region getter logic

    /**
     * orderId 조회
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 전체 취소 여부
     */
    public boolean isCanceled(){
        return this.orderLines.stream().allMatch(OrderLine::isCanceled);
    }

    /**
     * 부분취소 여부
     */
    public boolean isPartialCanceled(){
        boolean anyCanceled = this.orderLines.stream().anyMatch(OrderLine::isCanceled);
        boolean anyPurchased = this.orderLines.stream().anyMatch(OrderLine::isPurchased);
        return anyCanceled && anyPurchased;
    }

    /**
     * 주문 총액 조회
     */
    public double getTotalPrice(){
        return this.orderLines.stream().mapToDouble(OrderLine::getPurchasePrice).sum();
    }

    /**
     * 상품의 판매 금액 조회
     */
    public double getProductSalesAmount(Product product){
        OrderLine orderLine = this.getOrderLine(product.getProductId());
        if (orderLine.isCanceled()) {
            return 0;
        }
        return orderLine.getPurchasePrice();
    }

    public double getProductPurchasePrice(Product product) {
        OrderLine orderLine = this.getOrderLine(product.getProductId());
        return orderLine.getPurchasePrice();
    }

    /**
     * 상품으로 주문 라인 조회
     */
    private OrderLine getOrderLine(Long productId) {
        Optional<OrderLine> optionalOrderLine = orderLines.stream()
                .filter(orderLine -> orderLine.isEqualProduct(productId)).findFirst();
        if (optionalOrderLine.isEmpty()) {
            throw new RuntimeException("동일 상품을 찾을 수 없음");
        }
        return optionalOrderLine.get();
    }

    /**
     * 주문 상품 Id 조회
     */
    public List<Long> getProductIds() {
        return this.orderLines.stream().map(OrderLine::getProductId).toList();
    }

    /**
     * 재고 할인 적용된 판매금액 조회
     */
    public double getStockDiscountSaleAmount() {
        return this.orderLines.stream().mapToDouble(OrderLine::getPurchasePrice).sum();
    }
    // endregion

    // region setter logic
    /**
     * 주문 취소
     */
    public void cancel(){
        orderLines.forEach(OrderLine::cancel);
    }

    /**
     * 상품 부분 취소
     */
    public void partialCancel(Long productId) {
        OrderLine orderLine = this.getOrderLine(productId);
        orderLine.cancel();
    }

    /**
     * 상품 부분취소 실패 보상 로직
     */
    public void undoPartialCancel(Long productId) {
        OrderLine orderLine = this.getOrderLine(productId);
        orderLine.undoCancel();
    }
    // endregion
}
