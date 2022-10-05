package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

//    public OrderSimpleQueryDto(Order order){
//        orderId = order.getId();
//        name = order.getMember().getName(); // Lazy 초기화
//        orderDate = order.getOrderDate();
//        orderStatus = order.getStatus();
//        address = order.getDelivery().getAddress();
//    }

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate,OrderStatus orderStatus, Address address){
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
