package org.latexscribe.LatexScribe.webcontroller;

import lombok.RequiredArgsConstructor;
import org.latexscribe.LatexScribe.domain.dto.OrderDto;
import org.latexscribe.LatexScribe.domain.model.Order;
import org.latexscribe.LatexScribe.service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
  final private IOrderService orderService;


//    @GetMapping
//    public List<Order> getUserOrders(@RequestParam(required = false) String name) {
//        if (StringUtils.isEmpty(name)) {
//            return orderService.findByUser();
//        }
//        return orderService.findByName(name);
//
//    }

    @GetMapping
    public List<Order> listAll() {
        return orderService.listAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Order getById(@PathVariable("id") Long id) {
        Optional<Order> order = orderService.findById(id);
        if (order.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "document not found"
            );
        }
        return order.get();
    }

    @PostMapping
    public @ResponseBody Long createOrder(@RequestBody OrderDto orderDto) {
        var order = orderService.save(orderDto);
        return order.getId();
    }

    @DeleteMapping("/{id}")
    public HttpStatus putOrder(@PathVariable("id") Long id) {
        orderService.deleteById(id);
        return HttpStatus.OK;
    }

    @PutMapping("/{id}")
    public HttpStatus putOrder(@PathVariable("id") Long id, @RequestBody OrderDto orderDto) {
        orderService.update(id, orderDto);
        return HttpStatus.OK;
    }



}