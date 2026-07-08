package dio.budgeting.application;

import dio.budgeting.application.input.GetTotalSpendInput;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class GetTotalSpendUseCase {
    private final TransactionRepository transactionRepository;

    public GetTotalSpendUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "get-total-spend", description = "Retorna o valor total gasto de todas as transações ou de uma categoria específica.")
    public double execute(GetTotalSpendInput input) {
        List<Transaction> transactions;
        if (input != null && input.category() != null) {
            transactions = transactionRepository.findAllByCategory(input.category());
        } else {
            transactions = transactionRepository.findAll();
        }

        long totalCents = transactions.stream()
                .mapToLong(Transaction::getAmount)
                .sum();

        return BigDecimal.valueOf(totalCents)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

