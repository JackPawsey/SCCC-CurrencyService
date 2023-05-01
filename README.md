Currency converter communicates to the Shares Brokering Service via the SOAP protocol.

It provides two web services:  
“GetCurrencyCodes()” returns a list of currency codes and their plain text names e.g. “USD – United States Dollar”   
“GetConversionRate()” method returns the exchange rate for the parsed currency against USD. 

To serve this data, the service uses external API’s to retrieve up to date data every 5 minutes. 

These responses are cached into two JSON files, one for each of the methods so that an external API call is not needed every time the service is called.

When a request to the service is received, the corresponding file is read and its contents returned. The Shares Brokering Client does not directly call this service, only the Brokering Service does. 
