package app.stockDataStore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import app.IEXStockDataRetrieve.IEXDataClient;

@Component
public class StockRetrieveScheduler {
	
	private static Logger logger = LogManager.getLogger(IEXDataClient.class);
	
	String symbol = "NotSetYet";
	public SingleStockRunner singleStockRunner;
	Timer mainTimer;
	public Boolean isMainTimerRunning = false;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	public void setStockRetrieveScheduler(String symbol, SingleStockRunner singleStockRunner) {
		this.symbol = symbol;
		this.singleStockRunner = singleStockRunner;
		mainTimer = new Timer(); // timer to run other scheduler, repeat everyday at 9 am
	}

	public void stopMainTimer() {
		singleStockRunner.stopRunnerThread();
		mainTimer.purge();
		mainTimer.cancel();
		isMainTimerRunning = false;
	}
	
	public void schuduleStockToStart(int timeGapMilis) {
		
		TimerTask timerTask = new TimerTask(){
			public void run(){ 	//task
				isMainTimerRunning = true;
				long msTimeToStop = 7 * 60 * 60 *1000; // stop after 7 hour ** need to check again
				Timer jobTimer = new Timer(); // timer to start the job
				TimerTask jobTimerTask = jobTimerTask(jobTimer, msTimeToStop, timeGapMilis);
				jobTimer.schedule(jobTimerTask, decideWhenToStart(), msTimeToStop); // start now or tomorrow at 9 and stop after 7 hour 
				logger.info("Stock Scheduler: main timer started. For the symbol " + symbol 
						+ " stock data downloading is/will be started at " + dateFormat.format(decideWhenToStart()));
			}
		};
		mainTimer.schedule(timerTask, new Date(), thisDayHourMintute(1, 8, 59).getTime()); // start immediately/repeat every next day 8.59 am
	}

	private TimerTask jobTimerTask(Timer jobTimer, long msTimeToStop, int timeGapMilis) {
		TimerTask tt = new TimerTask(){
			Boolean isTimerCloseable = false;
			public void run(){
				if (isTimerCloseable == true) {
					singleStockRunner.stopRunnerThread();
					cancel();
					jobTimer.purge();
					jobTimer.cancel();
					logger.info("Stock Scheduler: sub timer is closed for the Symbol: " + symbol);
				}else {
					singleStockRunner.startDownloadingAndSavingFor(symbol, timeGapMilis); // task
					isTimerCloseable = true;
					logger.info("Stock Scheduler: sub timer started. For the symbol " + symbol + " stock data downloading is started.");
				}
			}
		};
		return tt;
	}
	
	public Date decideWhenToStart() {
		if(singleStockRunner.isMarketOpen(symbol))
			return new Date();
		
		return thisDayHourMintute(1, 9, 0);
	}

	public Date thisDayHourMintute(int day, int hour, int minute) {
	    Calendar today = new GregorianCalendar();
	    today.add(Calendar.DATE, day);
	    Calendar result = new GregorianCalendar(today.get(Calendar.YEAR),
	        today.get(Calendar.MONTH), today.get(Calendar.DATE), hour, minute);
	    return result.getTime();
	}
}
