package interface_adapters.view_stock;

import use_cases.view_stock.ViewStockInputBoundary;
import use_cases.view_stock.ViewStockInputData;

/**
 * Controller for the view stock use case and favorites management.
 */
public class ViewStockController {

    private final ViewStockInputBoundary viewStockUseCaseInteractor;

    public ViewStockController(
            ViewStockInputBoundary viewStockUseCaseInteractor) {
        this.viewStockUseCaseInteractor = viewStockUseCaseInteractor;
    }

    /**
     * Executes the view stock use case.
     * @param stockSymbol the symbol of the stock
     */
    public void execute(String stockSymbol) {
        final ViewStockInputData viewStockInputData = new ViewStockInputData(stockSymbol);
        viewStockUseCaseInteractor.execute(viewStockInputData);
    }

}
