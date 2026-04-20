package converter.controller;

import converter.model.ConverterModel;
import converter.util.InputValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConverterController implements Initializable {

    private static final double MAX_INPUT_VALUE = 1_000_000_000.0;
    private static final int HISTORY_LIMIT = 10;

    @FXML
    private TextField valueField;

    @FXML
    private ComboBox<String> fromComboBox;

    @FXML
    private ComboBox<String> toComboBox;

    @FXML
    private Label resultLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private ListView<String> historyListView;

    private final ConverterModel model = new ConverterModel();
    private final List<String> historyItems = new LinkedList<String>();
    private final DecimalFormat formatter;

    public ConverterController() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        formatter = new DecimalFormat("#,##0.########", symbols);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.refreshRates();

        List<String> units = model.getSupportedUnits();
        fromComboBox.setItems(FXCollections.observableArrayList(units));
        toComboBox.setItems(FXCollections.observableArrayList(units));

        if (!units.isEmpty()) {
            fromComboBox.setValue("USD");
            toComboBox.setValue("RUB");
        }

        historyListView.setItems(FXCollections.observableArrayList(historyItems));
        statusLabel.setText("Rates loaded. If API is unavailable, built-in values are used.");
    }

    @FXML
    private void handleConvert(ActionEvent event) {
        String input = valueField.getText();

        if (InputValidator.isEmpty(input)) {
            showError("Enter a value to convert.");
            return;
        }

        if (!InputValidator.isNumeric(input)) {
            showError("Enter a valid number. Decimal values are allowed.");
            return;
        }

        if (!InputValidator.isPositive(input)) {
            showError("The number must be greater than zero.");
            return;
        }

        if (!InputValidator.isWithinMax(input, MAX_INPUT_VALUE)) {
            showError("The number is too large. Maximum: " + formatter.format(MAX_INPUT_VALUE));
            return;
        }

        if (fromComboBox.getValue() == null || toComboBox.getValue() == null) {
            showError("Select conversion units.");
            return;
        }

        double value = Double.parseDouble(input.trim().replace(',', '.'));
        double convertedValue = model.convert(value, fromComboBox.getValue(), toComboBox.getValue());
        String message = formatter.format(value) + " " + fromComboBox.getValue()
                + " = " + formatter.format(convertedValue) + " " + toComboBox.getValue();

        resultLabel.setText(message);
        statusLabel.setText("Conversion completed successfully.");
        addToHistory(message);
    }

    @FXML
    private void handleRefreshRates(ActionEvent event) {
        model.refreshRates();
        statusLabel.setText("Rates refreshed or restored from local defaults.");
    }

    private void showError(String message) {
        resultLabel.setText("Error");
        statusLabel.setText(message);
    }

    private void addToHistory(String entry) {
        historyItems.add(0, entry);
        while (historyItems.size() > HISTORY_LIMIT) {
            historyItems.remove(historyItems.size() - 1);
        }
        historyListView.setItems(FXCollections.observableArrayList(historyItems));
    }
}
