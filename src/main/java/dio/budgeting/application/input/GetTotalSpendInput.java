package dio.budgeting.application.input;

import dio.budgeting.domain.Category;
import org.springframework.ai.tool.annotation.ToolParam;

public record GetTotalSpendInput(
        @ToolParam(description = "Categoria opcional para filtrar os gastos (ex: AUTO, GROCERIES, PHARMA, ENTERTAINMENT, UTILITIES, DINING_OUT, OTHERS)") Category category
) {
}
