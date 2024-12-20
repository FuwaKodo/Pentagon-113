package interface_adapters.view_stock;

import interface_adapters.ViewManagerModel;
import use_cases.StockDataInterface;
import use_cases.SymbolNameDataAccessInterface;
import use_cases.view_stock.ViewStockInputBoundary;
import use_cases.view_stock.ViewStockInteractor;
import use_cases.view_stock.ViewStockOutputBoundary;

/**
 * Factory class for creating instances of the ViewStockUseCase components,
 * including the controller, presenter, view model, and interactor.
 */
public class ViewStockUseCaseFactory {
    /**
     * Creates a new instance of ViewStockController with all its dependencies.
     *
     * @param viewManagerModel the model responsible for managing the view
     * @param viewStockViewModel the view model for view stock
     * @param dataAccessObject the data access interface for fetching stock data
     * @param symbolDataAccessObject the data access interface for fetching stock symbol and company
     * @return a configured ViewStockController instance
     */
    public static ViewStockController create(
            ViewManagerModel viewManagerModel,
            ViewStockViewModel viewStockViewModel,
            StockDataInterface dataAccessObject,
            SymbolNameDataAccessInterface symbolDataAccessObject) {

        // Create the presenter that will handle presentation logic, using the view model
        final ViewStockOutputBoundary viewStockPresenter = new ViewStockPresenter(
                viewManagerModel,
                viewStockViewModel
        );

        // Create the interactor which contains the business logic for viewing stocks
        final ViewStockInputBoundary viewStockInteractor = new ViewStockInteractor(
                viewStockPresenter,
                dataAccessObject,
                symbolDataAccessObject
        );

        // Create and return the controller that ties all components together
        return new ViewStockController(viewStockInteractor);
    }
}
