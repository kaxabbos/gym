package com.gym.repo;

import com.gym.model.Trainers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainersRepo extends JpaRepository<Trainers, Long> {

}
