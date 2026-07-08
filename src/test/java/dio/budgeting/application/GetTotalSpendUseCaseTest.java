package dio.budgeting.application;

import dio.budgeting.application.input.GetTotalSpendInput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class GetTotalSpendUseCaseTest {

    private TransactionRepository transactionRepository;
    private GetTotalSpendUseCase getTotalSpendUseCase;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        getTotalSpendUseCase = new GetTotalSpendUseCase(transactionRepository);
    }

    @Test
    void shouldReturnTotalSpendOfAllTransactionsWhenCategoryIsNull() {
        var t1 = new Transaction("Supermercado", 15000, Category.GROCERIES);
        var t2 = new Transaction("Remédio", 5000, Category.PHARMA);
        var t3 = new Transaction("Combustível", 8000, Category.AUTO);

        when(transactionRepository.findAll()).thenReturn(List.of(t1, t2, t3));

        double total = getTotalSpendUseCase.execute(new GetTotalSpendInput(null));

        assertThat(total).isEqualTo(28000.00);
    }

    @Test
    void shouldReturnTotalSpendOfCategoryWhenCategoryIsProvided() {
        var t1 = new Transaction("Combustível 1", 10000, Category.AUTO);
        var t2 = new Transaction("Combustível 2", 5000, Category.AUTO);

        when(transactionRepository.findAllByCategory(Category.AUTO)).thenReturn(List.of(t1, t2));

        double total = getTotalSpendUseCase.execute(new GetTotalSpendInput(Category.AUTO));

        assertThat(total).isEqualTo(15000.00);
    }

    @Test
    void shouldReturnZeroWhenNoTransactionsExist() {
        when(transactionRepository.findAll()).thenReturn(List.of());

        double total = getTotalSpendUseCase.execute(new GetTotalSpendInput(null));

        assertThat(total).isEqualTo(0.00);
    }
}
