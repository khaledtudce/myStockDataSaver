package app.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.SpringContext;
import app.IEXStockDataRetrieve.IEXDataClient;
import app.stockDataStore.SingleStockRunner;
import app.stockDataStore.StockRetrieveScheduler;

@RestController
public class MultiStockRestAssigner {
	
	private static Logger logger = LogManager.getLogger(IEXDataClient.class);
	
	Hashtable<String, StockRetrieveScheduler> stockDownloadList = new Hashtable<String, StockRetrieveScheduler>();
	
	@RequestMapping("/multiStockAssigner/downloadBySymbolAndTimeGap")
	public String startDownload(@RequestParam("symbol") String symbol, @RequestParam("timeGapMilis") int timeGapMilis) {
		if(!stockDownloadList.containsKey(symbol)) {
			SingleStockRunner singleStockRunner = SpringContext.getBean(SingleStockRunner.class);
			StockRetrieveScheduler stockRetrieveScheduler = SpringContext.getBean(StockRetrieveScheduler.class);
			stockRetrieveScheduler.setStockRetrieveScheduler(symbol, singleStockRunner);			
			stockRetrieveScheduler.schuduleStockToStart(timeGapMilis);
			stockDownloadList.put(symbol, stockRetrieveScheduler);
			logger.info("multiStockAssigner: Stock Symbol " + symbol + " is requested to download.");
		}
		
		return "Scheduler started for the symbol:" + symbol;
	}

	@RequestMapping("/multiStockAssigner/stopDownloadBySymbol")
	public String stopDownload(@RequestParam("symbol") String symbol) {
		if(stockDownloadList.containsKey(symbol)) {
			stockDownloadList.get(symbol).stopMainTimer();
			stockDownloadList.remove(symbol);
			return "Scheduler stopped for the symbol: " + symbol;
		}	
		
		return symbol + " was not exist in the stock downloading list";
	}

	@RequestMapping("/multiStockAssigner/getDownloadingSymbolList")
	public List<String> getCurrentDownloadingList() {
		return new ArrayList<String>(stockDownloadList.keySet());
	}

	@RequestMapping("/multiStockAssigner/startAll")
	public List<String> startAll(@RequestParam("timeGapMilis") int timeGapMilis) {
		stockDownloadList.forEach((symbol, value) -> {
			SingleStockRunner singleStockRunner = SpringContext.getBean(SingleStockRunner.class);
			StockRetrieveScheduler stockRetrieveScheduler = SpringContext.getBean(StockRetrieveScheduler.class);
			stockRetrieveScheduler.setStockRetrieveScheduler(symbol, singleStockRunner);			
			stockRetrieveScheduler.schuduleStockToStart(timeGapMilis);
			logger.info("Stock Symbol: " + symbol + " is started to download from history.");
		});

		return new ArrayList<String>(stockDownloadList.keySet());
	}
	
	@RequestMapping("/multiStockAssigner/stopAll")
	public List<String> stopAll() {

		stockDownloadList.forEach((symbol, value) -> { 
			stockDownloadList.get(symbol).stopMainTimer();
			logger.info("Stock Symbol " + symbol + " is stopped downloading.");
        });
		
		return new ArrayList<String>(stockDownloadList.keySet());
	}

}





