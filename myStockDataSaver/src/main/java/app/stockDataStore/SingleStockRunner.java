package app.stockDataStore;

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
	
    public Boolean isRunning = true;
    Thread t1;
	
	public void startDownloadingAndSavingFor(String symbol, int timeDistanceInMiliSecond) {
		t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(isRunning) {
					stockDataSaver.storeStockDataFor(symbol);
					try {
						Thread.sleep(timeDistanceInMiliSecond);
					} catch (InterruptedException e) {
						logger.info("Could not run thread of: " + symbol + ". " +e.getMessage());
						e.printStackTrace();
						isRunning = false;
					}
				}	
			}
		});
	}
	
	public void stopRunnerThread() {
		isRunning = false;
		if(t1.isAlive())
			t1.interrupt();
	}
	
	public Boolean isMarketOpen(String symbol) {
		return stockDataSaver.isUsMarketOpen(symbol);
	}
}
