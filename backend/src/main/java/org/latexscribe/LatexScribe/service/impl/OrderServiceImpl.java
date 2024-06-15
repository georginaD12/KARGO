package org.latexscribe.LatexScribe.service.impl;

import lombok.RequiredArgsConstructor;
import org.latexscribe.LatexScribe.domain.dto.OrderDto;
import org.latexscribe.LatexScribe.domain.model.Order;
import org.latexscribe.LatexScribe.domain.model.User;
import org.latexscribe.LatexScribe.repository.OrderRepository;
import org.latexscribe.LatexScribe.service.IOrderService;
import org.latexscribe.LatexScribe.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    final private IUserService userService;

    final private OrderRepository orderRepository;

    @Override
    public List<Order> listAll() {
        return orderRepository.findAll();
    }


    @Override
    public List<Order> findByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (user == null) {
            throw new IllegalArgumentException("provided user is null");
        }
        return orderRepository.findByUser(user);
    }

    @Override
    public Optional<Order> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("provided id is null");
        }
        return orderRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("provided id is null");
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Order save(OrderDto orderDto) {
        if (orderDto == null) {
            throw new IllegalArgumentException("provided order must not be null");
        }

//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
//        if (user == null) {
//            throw new IllegalArgumentException("provided user is null");
//        }

        Order order = new Order();
        order.setName(orderDto.name());
        order.setDescription(orderDto.description());
        order.setStatus(orderDto.status());
//        order.setUser(user);
        User user=userService.findByUsername(orderDto.username()).orElse(null);
        order.setUser(user);
        return orderRepository.saveAndFlush(order);
    }


    @Override
    public List<Order> findByName(String name) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return orderRepository.findByNameContainsIgnoreCaseAndUser(name, user);
    }

    @Override
    public Order update(Long id, OrderDto orderDto) {
        Optional<Order> order = this.findById(id);
        if (order.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "document not found"
            );
        }
        order.get().setStatus(orderDto.status());
        order.get().setName(orderDto.name());
        order.get().setDescription(orderDto.description());
        User user=userService.findByUsername(orderDto.username()).orElse(null);
        order.get().setUser(user);


        return orderRepository.saveAndFlush(order.get());
    }

}
