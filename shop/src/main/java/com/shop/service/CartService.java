package com.shop.service;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.dto.CartOrderDto;
import com.shop.dto.OrderDto;
import com.shop.entity.*;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.shop.entity.QCartItem.cartItem;

@Service
@Transactional
@RequiredArgsConstructor

public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCart(CartItemDto cartItemDto, String email){
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityExistsException::new);
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        CartItem savedCartIem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        // 현재 장바구에 이미 해당 상품이 들어 있는지 확인
        if(savedCartIem != null){
            savedCartIem.addCount(cartItemDto.getCount());
            return savedCartIem.getId();
        }else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }

    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();
        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            return cartDetailDtoList;
        }
        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
        return cartDetailDtoList;
    }


    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityExistsException::new);
        cartItem.updateCount(count);
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String name) {
        Member curMember = memberRepository.findByEmail(name);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityExistsException::new);
        Member savedCartItem = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedCartItem.getEmail())) {
            return false;
        }
        return true;
    }

    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityExistsException::new);
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();

        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            // CartItemId() 를 이용하여 cartItem 객체를 반환
            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            // 주문정보에 해당상품의 id가 설정됩니다.
            orderDto.setCount(cartItem.getCount());
            // 주분 정보에 해당상품으 수량이 설정됩니다.
            orderDtoList.add(orderDto);
            // 생성한 orderDto 객체를 orderDtoList 에 추가
        }   // cartItem 정보를 가져와서 OrderDto 객체로 변환 리스트 추가하는 작업

        Long orderId = orderService.orders(orderDtoList, email);
        for (CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }   // 장바구니 주문을 실행하고 장바구니 비우기(삭제)

        return orderId;
    }

}
