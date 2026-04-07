package converter.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RateProvider {

    private static final int TIMEOUT = 4000;

    public Map<String, Double> loadRates() {
        Map<String, Double> rates = new LinkedHashMap<String, Double>();

        try {
            rates.putAll(loadFiatRates());
        } catch (Exception ignored) {
        }

        try {
            rates.putAll(loadMetalRates());
        } catch (Exception ignored) {
        }

        try {
            rates.putAll(loadCryptoRates());
        } catch (Exception ignored) {
        }

        return rates;
    }

    private Map<String, Double> loadFiatRates() throws Exception {
        String json = readUrl("https://open.er-api.com/v6/latest/USD");
        Map<String, Double> rates = new LinkedHashMap<String, Double>();
        rates.put("USD", 1.0);
        rates.put("EUR", findNestedNumber(json, "EUR"));
        rates.put("RUB", findNestedNumber(json, "RUB"));
        return convertQuoteToUsdBase(rates);
    }

    private Map<String, Double> loadCryptoRates() throws Exception {
        String json = readUrl("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=usd");
        Map<String, Double> rates = new LinkedHashMap<String, Double>();
        rates.put("BTC", findNestedObjectNumber(json, "bitcoin", "usd"));
        rates.put("ETH", findNestedObjectNumber(json, "ethereum", "usd"));
        return rates;
    }

    private Map<String, Double> loadMetalRates() throws Exception {
        String json = readUrl("https://api.metals.live/v1/spot");
        Map<String, Double> rates = new LinkedHashMap<String, Double>();
        rates.put("XAU", findFlatArrayValue(json, "gold"));
        rates.put("XAG", findFlatArrayValue(json, "silver"));
        return rates;
    }

    private Map<String, Double> convertQuoteToUsdBase(Map<String, Double> quoteRates) {
        Map<String, Double> usdBased = new LinkedHashMap<String, Double>();

        for (Map.Entry<String, Double> entry : quoteRates.entrySet()) {
            String unit = entry.getKey();
            double quote = entry.getValue();
            if ("USD".equals(unit)) {
                usdBased.put(unit, 1.0);
            } else {
                usdBased.put(unit, 1.0 / quote);
            }
        }

        return usdBased;
    }

    private String readUrl(String urlString) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        InputStream stream = connection.getResponseCode() >= 400
                ? connection.getErrorStream()
                : connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();
        connection.disconnect();
        return builder.toString();
    }

    private double findNestedNumber(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        throw new IllegalStateException("Key not found: " + key);
    }

    private double findNestedObjectNumber(String json, String objectName, String key) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(objectName) + "\"\\s*:\\s*\\{[^}]*\"" + Pattern.quote(key) + "\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        throw new IllegalStateException("Object key not found: " + objectName + "." + key);
    }

    private double findFlatArrayValue(String json, String key) {
        Pattern pattern = Pattern.compile("\\{\\s*\"" + Pattern.quote(key) + "\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)\\s*\\}");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        throw new IllegalStateException("Array value not found: " + key);
    }
}
