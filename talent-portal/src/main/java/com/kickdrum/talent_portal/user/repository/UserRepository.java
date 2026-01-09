package com.kickdrum.talent_portal.user.repository;

import com.kickdrum.talent_portal.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);
}
