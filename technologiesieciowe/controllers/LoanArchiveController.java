package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.LoanArchiveEntity;
import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.service.LoanArchiveService;
import com.example.technologiesieciowe.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loanArchive")
public class LoanArchiveController {
    private final LoanArchiveService loanArchiveService;

    @Autowired
    public LoanArchiveController(LoanArchiveService loanArchiveService){
        this.loanArchiveService = loanArchiveService;
    }

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<LoanArchiveEntity> getAllLoansArchive(){
        return loanArchiveService.getAll();
    }

    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public LoanArchiveEntity getOne (@PathVariable Integer id) {
        return loanArchiveService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        loanArchiveService.delete(id);
    }

}
