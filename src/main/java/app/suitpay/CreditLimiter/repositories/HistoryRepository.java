package app.suitpay.CreditLimiter.repositories;

import app.suitpay.CreditLimiter.models.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
