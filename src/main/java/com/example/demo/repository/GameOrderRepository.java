package com.example.demo.repository;

import com.example.demo.entity.GameOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameOrderRepository extends JpaRepository<GameOrder, Long> {

    List<GameOrder> findAllByOrderByCookedAtDesc();
}
