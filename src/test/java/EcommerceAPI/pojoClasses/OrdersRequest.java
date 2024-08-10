package EcommerceAPI.pojoClasses;

import java.util.List;

public class OrdersRequest {
    public List<OrdersItemsRequest> orders;

    public List<OrdersItemsRequest> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersItemsRequest> orders) {
        this.orders = orders;
    }
}
