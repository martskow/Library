package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookDetailsEntity;
import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.infrastructure.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<LoanEntity> getAll() {
        return loanRepository.findAll();
    }

    public void delete(Integer id) {
        loanRepository.deleteById(id);
    }

    public LoanEntity save(LoanEntity loan) {
        return loanRepository.save(loan);
    }

    public LoanEntity getOne(Integer loanId) {
        return loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found."));
    }
}
