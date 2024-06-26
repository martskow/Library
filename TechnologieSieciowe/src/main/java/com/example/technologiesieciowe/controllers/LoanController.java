package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public LoanEntity addLoan(@RequestBody LoanEntity loan){
        return loanService.addLoan(loan);
    }

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<LoanEntity> getAllLoans(){
        return loanService.getAll();
    }

    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public LoanEntity getOne (@PathVariable Integer id) {
        return loanService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        loanService.delete(id);
    }
}
