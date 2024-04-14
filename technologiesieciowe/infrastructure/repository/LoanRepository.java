package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository interface for accessing and manipulating LoanEntity objects in the database.
 */
@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

    /**
     * Checks if a loan entry exists for a given user and book.
     *
     * @param user Optional<UserEntity> representing the user involved in the loan.
     * @param book Optional<BookEntity> representing the book involved in the loan.
     * @return true if a loan entry exists for the given user and book, false otherwise.
     */
    boolean existsByUserAndBook(Optional<UserEntity> user, Optional<BookEntity> book);

    /**
     * Retrieves all loans submitted by a given user ID.
     *
     * @param bookId The ID of the book that was borrowed.
     * @return An Iterable containing all loans submitted by the given user ID.
     */
    Iterable<LoanEntity> findByBookId(Integer bookId);

    /**
     * Retrieves all loans submitted by a given user ID.
     *
     * @param userId The ID of the user who made the loan.
     * @return An Iterable containing all loans submitted by the given user ID.
     */
    Iterable<LoanEntity> findByUserUserId(Integer userId);
}