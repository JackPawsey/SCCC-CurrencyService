/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrencyService;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Jack
 */
public class CurrencyServiceMain {
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        getExchangeRates();
        getCurrencies();
        System.out.println("Got rates and currencies");
        readCurrenciesFile();
        readRatesFile();
        System.out.println("Loaded rates and currencies");
    }
    
    // HTTP GET request
    public static void getExchangeRates() throws Exception {
        URL obj = new URL("https://openexchangerates.org/api/latest.json?app_id=98dfc9ba1594468388ca81a9e5c21e92");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        String inputLine;
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            System.out.println("Exchange rates GET request successful");
            
            writeJSONFile(response, "exchangeRates.json");     
        } else {
                System.out.println("Exchange rates GET request failed");
        }
    }
    
    // HTTP GET request
    public static void getCurrencies() throws Exception {
        URL obj = new URL("https://openexchangerates.org/api/currencies.json");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        String inputLine;
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            System.out.println("Currencies GET request successful");
            
            writeJSONFile(response, "C:\\Users\\Jack\\Documents\\NetBeansProjects\\CurrencyService\\currencies.json");     
        } else {
                System.out.println("Currencoes GET request failed");
        }
    }
    
    //Write JSON file
    private static void writeJSONFile(StringBuilder response, String fileName) throws FileNotFoundException, IOException, ParseException {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(response.toString());
            file.flush();

        } catch (IOException e) {
        }
    }
    
    // Load currency data from file
    public static ArrayList<String> readCurrenciesFile() throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        ArrayList<String> currencyList = new ArrayList<>();
       
        try {
                Object obj = parser.parse(new FileReader("C:\\Users\\Jack\\Documents\\NetBeansProjects\\CurrencyService\\currencies.json"));

                JSONObject jsonObject = (JSONObject) obj;
                
                jsonObject.keySet().forEach(keyStr -> {
                    Object keyvalue = jsonObject.get(keyStr);
                    currencyList.add(keyStr + " - " + keyvalue);
                });
                
            } catch (IOException e) {
        }
        return currencyList;
    }
    
    // Load exhcnage rate data from file
    public static ArrayList<ArrayList<String>> readRatesFile() throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        ArrayList<ArrayList<String>> exchangeRates = new ArrayList<>();
        
        try {
                Object obj = parser.parse(new FileReader("C:\\Users\\Jack\\Documents\\NetBeansProjects\\CurrencyService\\exchangeRates.json"));

                JSONObject jsonObject = (JSONObject) obj;
                
                JSONObject rates = (JSONObject) jsonObject.get("rates");
                
                rates.keySet().forEach(keyStr -> {
                    Object keyvalue = rates.get(keyStr);
                    ArrayList<String> singleList = new ArrayList<>();
                    singleList.add(keyStr.toString());
                    singleList.add(keyvalue.toString());
                    exchangeRates.add(singleList);
                });
                
            } catch (IOException e) {
        }
        return exchangeRates;
    }
}
