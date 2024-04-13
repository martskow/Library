package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.LoanArchiveEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanArchiveRepository extends JpaRepository<LoanArchiveEntity, Integer> {
    boolean existsByUserAndBook(Optional<UserEntity> user, Optional<BookEntity> book);
}
