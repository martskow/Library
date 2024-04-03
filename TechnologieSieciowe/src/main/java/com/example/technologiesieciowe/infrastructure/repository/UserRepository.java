package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query("from UserEntity u where u.UserName = ?1")
    UserEntity getByUserName(String UserName);
}
