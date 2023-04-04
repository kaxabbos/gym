package com.gym.repo;

import com.gym.model.Purchased;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedRepo extends JpaRepository<Purchased, Long> {

}
