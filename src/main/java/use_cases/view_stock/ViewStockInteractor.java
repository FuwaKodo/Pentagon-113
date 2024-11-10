package main.java.use_cases.view_stock;

import java.util.ArrayList;
import java.util.List;

import main.java.Constants;
import main.java.entities.Stock;

/**
 * Interactor of the use case.
 */
public class ViewStockInteractor implements ViewStockInputBoundary {

    private final ViewStockOutputBoundary viewStockPresenter;
    private final ViewStockDataAccessInterface viewStockDataAccessObject;

    public ViewStockInteractor(ViewStockOutputBoundary viewStockPresenter,
                               ViewStockDataAccessInterface viewStockDataAccessInterface) {
        this.viewStockPresenter = viewStockPresenter;
        this.viewStockDataAccessObject = viewStockDataAccessInterface;
    }

    /**
     * Executes the use case.
     *
     * @param viewStockInputData input data for the use case.
     */
    @Override
    public void execute(ViewStockInputData viewStockInputData) {
        final Stock stock = viewStockDataAccessObject.getStock(viewStockInputData.getSymbol());

        final String company = stock.getCompany();
        final String symbol = stock.getSymbol();
        final List<Double> sharePrices = new ArrayList<Double>();
        // final List<Double> earnings = new ArrayList<Double>();

        for (int i = 0; i < Constants.FIVE_YEARS; i++) {
            sharePrices.add(stock.getSharePrice(Constants.FIVE_YEARS - i));
        }

        final ViewStockOutputData viewStockOutputData = new ViewStockOutputData(company, symbol, sharePrices);

        viewStockPresenter.displayStock(viewStockOutputData);
    }
}
