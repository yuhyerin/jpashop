package jpabook.jpashop.service;

import jpabook.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @Rollback(false)
    public void 상품저장() throws Exception {
        // given
        Item item = new Book();
        item.setName("spring");

        // when
        Long savedId = itemRepository.save(item);

        // then
        Assertions.assertEquals(item, itemRepository.findOne(savedId));
    }

    @Test
    @Rollback(false)
    public void 상품재고추가() throws Exception{
        상품저장();
        Item item = itemRepository.findOne(1L);
        item.addStock(2);
        System.out.println("상품추가 후 재고>>> "+item.getStockQuantity());
    }

    @Test
    @Rollback(false)
    public void 상품재고삭제() throws Exception{
        상품저장();
        Item item = itemRepository.findOne(1L);
        item.addStock(3);
        System.out.println("상품추가 후 재고>>> "+item.getStockQuantity());
        item.removeStok(1);
        System.out.println("상품삭제 후 재고>>> "+item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    @Rollback(false)
    public void 상품재고삭제예외() throws Exception{
        상품저장();
        Item item = itemRepository.findOne(1L);
        item.addStock(1);
        System.out.println("상품추가 후 재고>>> "+item.getStockQuantity());
        item.removeStok(2);
        System.out.println("상품삭제 후 재고>>> "+item.getStockQuantity());

        Assert.fail("재고가 더 필요하다는 예외가 발생해야 한다. ");
    }
}
