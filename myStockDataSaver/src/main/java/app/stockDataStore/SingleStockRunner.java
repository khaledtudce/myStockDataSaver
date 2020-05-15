package app.stockDataStore;

import java.util.Date;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.IEXStockDataRetrieve.IEXDataClient;

@Component
public class SingleStockRunner{
	
	private static Logger logger = LogManager.getLogger(IEXDataClient.class);
	
	@Autowired
	StockDataSaver stockDataSaver;
	
	Hashtable<String, Timer> timerList = new Hashtable<String, Timer>(); // later try with getId

	public void startDownloadingAndSavingFor(String symbol, int timeDistanceInMiliSecond) {
		Timer stockDownloadTimer = new Timer();
		
		TimerTask timerTask = new TimerTask(){
			public void run(){ 
				stockDataSaver.storeStockDataFor(symbol);
			}
		};	
		stockDownloadTimer.schedule(timerTask, new Date(), timeDistanceInMiliSecond);
		timerList.put(symbol, stockDownloadTimer);
		logger.info("Started timer Thread: "+ Thread.currentThread().getName()+ " for the Symbol: " + symbol);
	}
	
	public void stopRunnerTimer(String symbol) {	
		Timer t1 = timerList.get(symbol);
		t1.purge();
		t1.cancel();
		logger.info("Stopped timer Thread: "+ Thread.currentThread().getName()+ " for the Symbol: " + symbol);
	}
	
	public Boolean isMarketOpen(String symbol) {
		return stockDataSaver.isUsMarketOpen(symbol);
	}

}
