package com.gym.repo;

import com.gym.model.Subs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsRepo extends JpaRepository<Subs, Long> {

}
