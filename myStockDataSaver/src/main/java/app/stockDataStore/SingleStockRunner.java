package app.stockDataStore;

import java.util.Date;
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
	
    public Boolean isRunning = false;
    Timer stockDownloadTimer = new Timer();
    Thread t1;
	
	public void startDownloadingAndSavingFor(String symbol, int repeatInMiliSecond) {
		t1 = new Thread(new Runnable() { // this timer running always in same thread, the right thread does not stop
			@Override
			public void run() {
				TimerTask timerTask = new TimerTask(){
					public void run(){ 
						isRunning = true;
						stockDataSaver.storeStockDataFor(symbol);
					}
				};
				stockDownloadTimer.schedule(timerTask, new Date(), repeatInMiliSecond);
				logger.info("Thread: " + t1.getId() + " started");
				logger.info("SingleStockRunner: Scheduler started for the " + symbol + " . It will repeat at every " + repeatInMiliSecond + " millisecond");
			}
		});
		
		t1.start();
		
	}
	
	public void stopDownloading() {
		isRunning = false;
		stockDownloadTimer.purge();
		stockDownloadTimer.cancel();
	}
	
	public void stopRunnerThread() {
		if(t1.isAlive()) {
			t1.interrupt();
			logger.info("Thread: " + t1.getId() + " stopped");
		}
	}
	
	public Boolean isMarketOpen(String symbol) {
		return stockDataSaver.isUsMarketOpen(symbol);
	}
}
