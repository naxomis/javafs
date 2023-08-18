package com.shop.service;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistDto;
import com.shop.dto.OrderItemDto;
import com.shop.entity.*;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityExistsException::new);
        // 주문하려는 아이템을 itemRepository 에서 아이템 id로 조회, 존재하지 않으면 EntityExistsException

        // 주문자

        Member member = memberRepository.findByEmail(email);
        // 주문을 생성하는 회원 memberRepository 에서 이메일을 이용해 조회

        List<OrderItem> orderItemList = new ArrayList<>();  // 주문항목 리스트를 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        // 주문항목을 생성 OrderItem 클래스의 createOrderItem 메서드를 호출해서 주문항목을 생성
        // 이때 필요한 정보 아이템과 주문 수량
        orderItemList.add(orderItem);   // 생성한 주문항목을 주문 항목 리스트에 추가
        Order order = Order.createOrder(member, orderItemList);     // 주문을 생성합니다.
        // Order 클래스의 createOrder 메서드를 호출하여 주문을 생성하며,
        // 이때 회원 정보와 줌누 항목 리스트가 제공됩니다.
        orderRepository.save(order);
        // 생성한 주문을 데이터베이스에 저장합니다.
        return order.getId();   // 생성된 주문의 ID를 반환합니다.

    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
                        (orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto =
                        new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);    // orderHistDto 리스트에 추가
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
        // 생성된 orderHistDto 리스트를 페이징 처리하여 반환
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId).
                orElseThrow(EntityExistsException::new);

        Member savedMember = order.getMember();


        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }
        return true;

    }
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).
                orElseThrow(EntityExistsException::new);
        order.cancelOrder();
    }


    public Long orders(List<OrderDto> orderDtoList, String email) {

        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderDto orderDto : orderDtoList) {
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityExistsException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());

            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
