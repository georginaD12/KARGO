package org.latexscribe.LatexScribe.service;

import org.latexscribe.LatexScribe.domain.dto.OrderDto;
import org.latexscribe.LatexScribe.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

    List<Order> listAll();
    List<Order> findByUser();

    Optional<Order> findById(Long id);

    void deleteById(Long id);

    Order save(OrderDto order);

    List<Order> findByName(String name);

    Order update(Long id, OrderDto orderDto);

}
