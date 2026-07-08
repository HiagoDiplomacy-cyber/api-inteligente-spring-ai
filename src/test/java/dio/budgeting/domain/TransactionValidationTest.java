package dio.budgeting.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionValidationTest {

    @Test
    void shouldThrowExceptionWhenAmountIsZeroOrNegative() {
        assertThatThrownBy(() -> new Transaction("Gasto inválido", 0, Category.AUTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O valor da transação deve ser maior que zero.");

        assertThatThrownBy(() -> new Transaction("Gasto inválido", -100, Category.AUTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O valor da transação deve ser maior que zero.");
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsBlank() {
        assertThatThrownBy(() -> new Transaction("", 1000, Category.AUTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A descrição da transação não pode ser vazia.");

        assertThatThrownBy(() -> new Transaction(null, 1000, Category.AUTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A descrição da transação não pode ser vazia.");
    }

    @Test
    void shouldThrowExceptionWhenCategoryIsNull() {
        assertThatThrownBy(() -> new Transaction("Gasto sem categoria", 1000, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A categoria da transação é obrigatória.");
    }
}
