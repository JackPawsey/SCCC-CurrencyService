/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrencyService;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import static CurrencyService.CurrencyServiceMain.*;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Jack
 */
@WebService(serviceName = "ConverterService")
public class CurrencyService { 
    /**
     * Web service operation
     * @return 
     * @throws org.json.simple.parser.ParseException 
     */
    @WebMethod(operationName = "GetCurrencyCodes")
    public ArrayList<String> GetCurrencyCodes() throws ParseException {
        return readCurrenciesFile();
    }

    /**
     * Web service operation
     * @param selectedCurrencySymbol
     * @return 
     * @throws org.json.simple.parser.ParseException 
     */
    @WebMethod(operationName = "GetConversionRate")
    public double GetConversionRate(String selectedCurrencySymbol) throws ParseException {
        ArrayList<ArrayList<String>> exchangeRates = readRatesFile();
        
        double rate = 0;
            
        try {
            for (int x = 0; x < exchangeRates.size(); x++) {
                    if (exchangeRates.get(x).get(0).equals(selectedCurrencySymbol)) {
                        rate = Double.parseDouble(exchangeRates.get(x).get(1));
                    }
                }
            return rate;
        }

        catch (IllegalArgumentException iae) {
            return -1;
        }
    }
}
