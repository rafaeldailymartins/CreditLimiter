package app.suitpay.CreditLimiter.services;

import app.suitpay.CreditLimiter.models.History;
import app.suitpay.CreditLimiter.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository repository;

    public void create(History history) {
        repository.save(history);
    }

    public Page<History> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
