package converter.model;

import converter.service.RateProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConverterModel {

    private final Map<String, Double> ratesToUsd = new LinkedHashMap<String, Double>();
    private final RateProvider rateProvider;

    public ConverterModel() {
        this(new RateProvider());
    }

    public ConverterModel(RateProvider rateProvider) {
        this.rateProvider = rateProvider;
        loadDefaultRates();
    }

    private void loadDefaultRates() {
        ratesToUsd.clear();
        ratesToUsd.put("USD", 1.0);
        ratesToUsd.put("EUR", 1.08);
        ratesToUsd.put("RUB", 0.0108);
        ratesToUsd.put("BTC", 68000.0);
        ratesToUsd.put("ETH", 3400.0);
        ratesToUsd.put("XAU", 2320.0);
        ratesToUsd.put("XAG", 27.5);
    }

    public double convert(double value, String from, String to) {
        validateUnit(from);
        validateUnit(to);

        double fromRate = ratesToUsd.get(from);
        double toRate = ratesToUsd.get(to);
        double valueInUsd = value * fromRate;
        return valueInUsd / toRate;
    }

    public void refreshRates() {
        Map<String, Double> loadedRates = rateProvider.loadRates();
        if (loadedRates != null && !loadedRates.isEmpty()) {
            ratesToUsd.putAll(loadedRates);
        }
    }

    public List<String> getSupportedUnits() {
        return new ArrayList<String>(ratesToUsd.keySet());
    }

    public Map<String, Double> getRatesSnapshot() {
        return new LinkedHashMap<String, Double>(ratesToUsd);
    }

    public List<String> getUnitDescriptions() {
        return Arrays.asList(
                "USD - доллар США",
                "EUR - евро",
                "RUB - российский рубль",
                "BTC - Bitcoin",
                "ETH - Ethereum",
                "XAU - золото (тройская унция)",
                "XAG - серебро (тройская унция)"
        );
    }

    private void validateUnit(String unit) {
        if (!ratesToUsd.containsKey(unit)) {
            throw new IllegalArgumentException("Неизвестная единица: " + unit);
        }
    }

    public static void main(String[] args) {
        ConverterModel model = new ConverterModel();

        double result1 = model.convert(10, "USD", "EUR");
        System.out.println("10 USD = " + result1 + " EUR");

        double result2 = model.convert(100, "EUR", "USD");
        System.out.println("100 EUR = " + result2 + " USD");

        if (Math.abs(result1 - 9.26) < 0.2) {
            System.out.println("Test 1 passed");
        } else {
            System.out.println("Test 1 failed");
        }
    }
}
