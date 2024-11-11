package main.java.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.Constants;
import main.java.interface_adapters.ViewManagerModel;
import main.java.interface_adapters.view_stock.ViewStockController;
import main.java.interface_adapters.view_stock.ViewStockViewModel;
import main.java.view.StockDataView;
import main.java.view.ViewManager;

/**
 * View for the application.
 */
public class ViewStockView {
    private JPanel mainPanel;
    private JButton compareButton;
    private JComboBox<String> stockDropdown;
    private JButton buyButton;
    private final StockDataView stockViewObject;

    private ViewStockViewModel viewStockViewModel;
    private ViewStockController viewStockController;

    public ViewStockView(ViewStockViewModel viewStockViewModel,
                         ViewStockController viewStockController) {
        this.viewStockViewModel = viewStockViewModel;
        this.viewStockController = viewStockController;

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Placeholder panel for when no stock is selected
        final JPanel defaultBox = new JPanel(new BorderLayout());
        final JLabel placeholderLabel = new JLabel(Constants.PLACE_HOLDER, SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 24));
        defaultBox.add(placeholderLabel, BorderLayout.CENTER);

        // Initialize StockDataView with sample data
        stockViewObject = new StockDataView();
        stockViewObject.generatePanel();

        // Generate sample data for testing
        List<Double> samplePrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            samplePrices.add(100.0 + Math.random() * 20);
        }
        stockViewObject.setSharePrices(samplePrices);

        // CardLayout panel for displaying panels according to stocks selected
        final CardLayout cardLayout = new CardLayout();
        final JPanel stockViews = new JPanel(cardLayout);
        stockViews.add(defaultBox, Constants.SELECT_STOCK);
        stockViews.add(stockViewObject.getStockView(), Constants.VIEW_STOCK);

        // Make sure the stock view panel has a preferred size
        stockViewObject.getStockView().setPreferredSize(new Dimension(700, 500));

        mainPanel.add(stockViews, BorderLayout.CENTER);

        // Manages which panel in stockViews is displayed
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(stockViews, cardLayout, viewManagerModel);

        // Bottom panel to hold buttons and dropdown
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Dropdown menu for selecting stocks
        stockDropdown = new JComboBox<>(new String[]{Constants.SELECT_STOCK, "Stock A", "Stock B", "Stock C"});
        bottomPanel.add(stockDropdown);

        // Response to selecting a stock in dropdown menu
        stockDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String symbol = Objects.requireNonNull(stockDropdown.getSelectedItem()).toString();
                if (symbol.equals(Constants.SELECT_STOCK)) {
                    cardLayout.show(stockViews, Constants.SELECT_STOCK);
                }
                else {
                    // Update the stock view with new data, only for when controller is not usable
                    stockViewObject.setSymbol(symbol);
                    stockViewObject.setCompany("Company " + symbol);

                    // Generate new random data for the selected stock
                    List<Double> prices = new ArrayList<>();
                    double basePrice = switch (symbol) {
                        case "Stock A" -> 100.0;
                        case "Stock B" -> 150.0;
                        case "Stock C" -> 200.0;
                        default -> 100.0;
                    };

                    for (int i = 0; i < 10; i++) {
                        prices.add(basePrice + Math.random() * 20);
                    }
                    stockViewObject.setSharePrices(prices);

                    // Show the stock view, only for when controller is not usable
                    cardLayout.show(stockViews, Constants.VIEW_STOCK);
                    viewManagerModel.setState(Constants.VIEW_STOCK);
                    viewManagerModel.firePropertyChanged();

                    // Ensure the panel is revalidated and repainted
                    stockViewObject.getStockView().revalidate();
                    stockViewObject.getStockView().repaint();
                }
            }
        });

        // Button to compare stocks
        compareButton = new JButton("Compare Stocks");
        bottomPanel.add(compareButton);

        // Button to buy stock
        buyButton = new JButton("Buy Stock");
        bottomPanel.add(buyButton);

        // Add bottom panel to the main panel
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getter for the main panel
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Additional getters if needed for event handling
    public JButton getCompareButton() {
        return compareButton;
    }

    public JComboBox<String> getStockDropdown() {
        return stockDropdown;
    }

    public JButton getBuyButton() {
        return buyButton;
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}