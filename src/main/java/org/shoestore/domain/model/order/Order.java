package org.shoestore.domain.model.order;

import java.util.List;
import java.util.Optional;
import org.shoestore.domain.model.User.User;
import org.shoestore.domain.model.cart.Cart;
import org.shoestore.domain.model.order.vo.Buyer;
import org.shoestore.domain.model.product.Product;

public class Order {

    private final List<OrderLine> orderLines; // 주문 상세내역
    private final Buyer buyer;

    // region constructor
    public Order(List<OrderLine> orderLines, User user) {
        this.orderLines = orderLines;
        this.buyer = new Buyer(user);
    }

    public Order(Cart cart, User user) {
        this.orderLines = cart.getProducts().stream().map(OrderLine::new).toList();
        this.buyer = new Buyer(user);
    }
    // endregion

    // region getter logic

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

    public double getProductSalesAmount(Product product){
        OrderLine orderLine = this.getOrderLine(product);
        if (orderLine.isCanceled()) {
            return 0;
        }
        return orderLine.getSalesAmount();
    }

    /**
     * 상품으로 주문 라인 조회
     */
    private OrderLine getOrderLine(Product product) {
        Optional<OrderLine> optionalOrderLine = orderLines.stream()
                .filter(orderLine -> orderLine.isEqualProduct(product)).findFirst();
        if (optionalOrderLine.isEmpty()) {
            throw new RuntimeException("동일 상품을 찾을 수 없음");
        }
        return optionalOrderLine.get();
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
    public void partialCancel(Product product) {
        OrderLine orderLine = this.getOrderLine(product);
        orderLine.cancel();
    }
    // endregion
}
