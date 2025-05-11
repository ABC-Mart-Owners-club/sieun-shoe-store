package org.shoestore.order.model.vo;

import java.util.Objects;
import org.shoestore.product.model.Product;

public record OrderItem(
        Long productId,
        Long stockId,
        double purchasedPrice
) {

    public OrderItem(Product product) {
        this(product.getProductId(), product.getUsableStockId(), product.getSalesAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(productId, orderItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
