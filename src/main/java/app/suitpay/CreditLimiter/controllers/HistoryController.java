package app.suitpay.CreditLimiter.controllers;

import app.suitpay.CreditLimiter.models.History;
import app.suitpay.CreditLimiter.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService service;

    @GetMapping
    public Page<History> getAllHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp")));
    }
}
