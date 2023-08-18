package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void order() {
        Member member = createMember();
        Item item = createBook("시골 JAP", 10000, 10);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000*2,getOrder.getTotalPrice(), "주문가격은 가격*수량");

        // 주문 수량만큼 재고가 줄어야 하나다.
        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 제고가 줄어야 한다.");
    }

    private Member createMember(){
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

    @Test
    public void 상품_재고수량초과() throws Exception{
        // Given
        // 1. member 생성
        Member member = createMember();

        // 2. item 생성시 재고 10개
        Item item = createBook("시골 JAP", 10000, 10);

        // 3. 재고보다 많은 수량
        int orderCount = 11;

        //when
        //orderSevice 를 실행 했을 때

        // ("재고 수향부족 예외 가 발생한다.") -  예외가 발생해서 테스트를 성공하게 만드시오

        // then
        assertThrows(NotEnoughStockException.class, ()->{
            orderService.order(member.getId(), item.getId(), orderCount);
        });

    }

    @Test
    public void 주문취소(){
        Member member = createMember();

        // 2. item 생성시 재고 10개
        Item item = createBook("시골 JAP", 10000, 10);

        // 3. 재고보다 많은 수량
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문취소상태는 CANCEL 이다. ");

        // 주문이 취소된 상품은 그만큼 재고가 증가해야 한다.
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가한다.");
    }
}