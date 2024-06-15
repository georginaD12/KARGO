package org.latexscribe.LatexScribe.repository;

import org.latexscribe.LatexScribe.domain.model.Order;
import org.latexscribe.LatexScribe.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByNameContainsIgnoreCaseAndUser(String name, User user);



}
