package com.example.test.service;

import com.example.test.entity.CartEntity;
import com.example.test.mapper.CartMapper;
import com.example.test.model.Cart;
import com.example.test.repository.CartRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartEntity> getCartList() {
        List<CartEntity> cart = cartRepository.findAll();
        List<CartEntity> list = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = ((User)principal).getUsername();

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getOwner().equals(owner)) {
                list.add(cart.get(i));
            }
        }

        return list;
    }

    public CartEntity getEntity(Integer id) {
        Optional<CartEntity> optionalCart = cartRepository.findById(id);
        var cartEntity = optionalCart.orElseGet(CartEntity::new);
        return cartEntity;
    }

    public String getTotal() {
        List<CartEntity> cart = cartRepository.findAll();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = ((User)principal).getUsername();
        Double total = 0.0;

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getOwner().equals(owner)) {
                total += cart.get(i).getPrice() * cart.get(i).getCount();
            }
        }

        String tot = total.toString() + "$";

        return tot;
    }

    public void add(CartEntity cartEntity) {
        cartRepository.save(cartEntity);
    }

    public void add1(Cart cart) {
        cartRepository.save(CartMapper.INSTANCE.DtoToEntity(cart));
    }

    public void deleteById(Integer id) {
        cartRepository.deleteById(id);
    }

    public void clear() {
        cartRepository.deleteAll();
    }
}
