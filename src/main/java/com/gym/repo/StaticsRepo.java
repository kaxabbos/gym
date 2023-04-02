package com.gym.repo;

import com.gym.model.Statics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaticsRepo extends JpaRepository<Statics, Long> {

}
