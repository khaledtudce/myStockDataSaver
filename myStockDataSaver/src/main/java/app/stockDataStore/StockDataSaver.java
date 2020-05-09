package app.stockDataStore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.IEXStockDataRetrieve.IEXDataClient;
import app.IEXStockDataRetrieve.StockQuote;
import app.helperPackage.DateConverter;
import app.model.TradePriceBuilder;
import app.model.TradePriceDto;
import app.repository.TradePriceRepository;

@Component
public class StockDataSaver {
	
	private static Logger logger = LogManager.getLogger(IEXDataClient.class);
	
	@Autowired
	IEXDataClient iexDataClient;
	
	@Autowired
	TradePriceRepository tradePriceRepository;

	public TradePriceDto storeStockDataFor(String symbol) {
		StockQuote stockQuote= getQuote(symbol);
		
		logger.info("Saving data for the Symbol: " + symbol + " . ");
		return tradePriceRepository.save(getTradePriceDto(stockQuote));
	}

	private StockQuote getQuote(String symbol) {
		StockQuote stockQuote= new StockQuote();
		try {
			stockQuote = iexDataClient.getQuoteJson(symbol);
		} catch (Exception e) {
			logger.info("Could not receive data from RestAPI: " +e.getMessage());
			e.printStackTrace();
		}
		return stockQuote;
	}

	private TradePriceDto getTradePriceDto(StockQuote stockQuote) {
		
		TradePriceDto tradePriceDto = new TradePriceBuilder().setSymbol(stockQuote.getSymbol())
				.setPrice(stockQuote.getLatestPrice())
				.setTime(DateConverter.convertDateFromEpoch(stockQuote.getLatestUpdate()))
				.setUsMarketOpen(stockQuote.isUSMarketOpen()).getTradePrice();
		
		return tradePriceDto;
	}

	public Boolean isUsMarketOpen(String symbol) {
		return getQuote(symbol).isUSMarketOpen();
	}	

}
