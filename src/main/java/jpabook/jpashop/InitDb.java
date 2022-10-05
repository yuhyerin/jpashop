package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
/**
 * userA
 *
 * userB
 *
 * */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit(){
            Member member = createMember("userA","서울","1","1111");
            em.persist(member);

            Book book1 = new Book();
            book1.setName("JPA1 BOOK");
            book1.setPrice(10000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA2 BOOK");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000,1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000,2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member,delivery,orderItem1,orderItem2);
            em.persist(order);

        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city,street,zipcode));
            return member;
        }

        public void dbInit2(){
            Member member = createMember("userB","부산","2","2222");
            em.persist(member);

            Book book1 = new Book();
            book1.setName("JPA1 BOOK");
            book1.setPrice(20000);
            book1.setStockQuantity(200);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA2 BOOK");
            book2.setPrice(40000);
            book2.setStockQuantity(300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000,3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000,4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member,delivery,orderItem1,orderItem2);
            em.persist(order);

        }
    }

}


