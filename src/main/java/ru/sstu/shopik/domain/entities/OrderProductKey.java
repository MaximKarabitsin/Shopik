package ru.sstu.shopik.domain.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

//@Embeddable
public class OrderProductKey implements Serializable {
    @Column(name = "orderId")
    Long ordertId;

    @Column(name = "productId")
    Long productId;

    public OrderProductKey() {
    }

    public Long getOrdertId() {
        return ordertId;
    }

    public void setOrdertId(Long ordertId) {
        this.ordertId = ordertId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductKey that = (OrderProductKey) o;
        return Objects.equals(ordertId, that.ordertId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordertId, productId);
    }
}
