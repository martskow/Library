package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.infrastructure.entity.QueueEntity;
import com.example.technologiesieciowe.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {
    private final QueueService queueService;

    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public QueueEntity addQueue(@RequestBody QueueEntity queue){
        return queueService.addQueue(queue);
    }

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<QueueEntity> getAllLoans(){
        return queueService.getAll();
    }

    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public QueueEntity getOne (@PathVariable Integer id) {
        return queueService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        queueService.delete(id);
    }

    @PutMapping("/endWaiting/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void endQueue(@PathVariable Integer id) {
        queueService.endQueue(id);
    }
}
