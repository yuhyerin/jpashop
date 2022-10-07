package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.api.OrderApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    /**
     * 컬렉션은 별도로 조회
     * Query: 루트 1번, 컬렉션 N 번
     * 단건 조회에서 많이 사용하는 방식
     * */
    public List<OrderQueryDto> findOrderQueryDtos() {
        // 루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders(); // query - 1번 (결과:2개(N))
        // 루프를 돌면서 컬렉션 추가(추가쿼리 실행)
        result.forEach(o-> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId()); // query - 2번(N) 반복
            o.setOrderItems(orderItems);
        });
        // -> N+1문제
        return result;
    }

    /**
     * 1:N관계(컬렉션)을 제외한 나머지를 한번에 조회
     * */
    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class
        ).getResultList();
    }

    /**
     * 1:N 관계인 orderItems 조회
     * */
    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto" +
                                "(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi"+
                                " join oi.item i"+
                                " where oi.order.id = :orderId", OrderItemQueryDto.class
                ).setParameter("orderId",orderId)
                .getResultList();
    }


    /**
     * 최적화
     * Query: 루트1번, 컬렉션1번
     * 데이터를 한꺼번에 처리할 때 많이 사용하는 방식
     * */
    public List<OrderQueryDto> findAllByDto_optimization() {
        // 루트 조회(toOne코드를 모두 한번에 조회) - 쿼리 1번
        List<OrderQueryDto> result = findOrders();

        // orderItem 컬렉션을 Map 한방에 조회 - 쿼리 1번
        Map<Long, List<OrderItemQueryDto>> orderItemMap =
                findOrderItemMap(toOrderIds(result));

        // 루프 돌면서 컬렉션 추가(추가 쿼리 실행X)
        result.forEach(o->o.setOrderItems(orderItemMap.get(o.getOrderId())));

        // 쿼리 총 2번
        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto" +
                        "(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi"+
                        " join oi.item i"+
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
        // map으로 변환
        Map<Long, List<OrderItemQueryDto>> orderitemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
//                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
        return orderitemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderFlatDto" +
                        "(o.id,m.name,o.orderDate,o.status,d.address,i.name,oi.orderPrice,oi.count)" +
                        " from Order o"+
                        " join o.member m"+
                        " join o.delivery d"+
                        " join o.orderItems oi"+
                        " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
