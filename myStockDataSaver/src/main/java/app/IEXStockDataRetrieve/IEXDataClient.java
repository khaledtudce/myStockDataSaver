package app.IEXStockDataRetrieve;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@Component
public class IEXDataClient {
	
	private static Logger logger = LogManager.getLogger(IEXDataClient.class);
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	String BASE_URL = "https://cloud.iexapis.com";
	String TOKEN = "pk_69582fd2d26d455498bd8b56dc80ddac";
	
	public StockQuote getQuoteJson(String quote) throws Exception {
		
		Gson gson = new Gson();
		String jsonData = getQuoteAPIRequest(quote);
		StockQuote stockQuote = gson.fromJson(jsonData, StockQuote.class);
		
		return stockQuote;
	}

	private String getQuoteAPIRequest(String quote) throws Exception {

		//https://cloud.iexapis.com/stable/stock/AAPL/quote?token=pk_69582fd2d26d455498bd8b56dc80ddac
        String apiURL = BASE_URL+"/stable/stock/"+quote+"/quote?token="+TOKEN;
		HttpGet request = new HttpGet(apiURL);
        logger.info("Requesting for the URL: " + apiURL);
        String result = "Sorry, could not received anything";
        
        // add request headers
        request.addHeader("TOKEN", TOKEN);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            
            if (entity != null) {
                result = EntityUtils.toString(entity);
//                logger.info("Received Stock information for " + quote + ": " + result);
            }
        }
        
		return result;
    }
}
