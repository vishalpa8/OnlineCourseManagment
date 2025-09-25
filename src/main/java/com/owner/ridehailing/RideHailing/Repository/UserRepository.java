package com.owner.ridehailing.RideHailing.Repository;

import com.owner.ridehailing.RideHailing.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
