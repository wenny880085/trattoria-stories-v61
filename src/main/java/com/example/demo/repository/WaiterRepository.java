package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Waiter;

public interface WaiterRepository extends JpaRepository<Waiter, Long> {
}