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
		singleStockRunner.stopDownloadingAndSaving();
		mainTimer.purge();
		mainTimer.cancel();
		isMainTimerRunning = false;
		if(!singleStockRunner.isRunning)
			singleStockRunner.stopRunnerThread(); // may be wait a bit?
	}
	
	public void schuduleStockToStart(int timeGapMilis) {
		
		TimerTask timerTask = new TimerTask(){
			public void run(){ 	//task 9.30 to 4pm, DE 15.30 to 10 pm
				isMainTimerRunning = true;
				long msTimeToStop = 6 * 60 * 60 *1000 + 30 * 60 * 1000; // stop after 6.50 hour which is at 10.00 pm
				Timer jobTimer = new Timer(); // timer to start the job
				TimerTask jobTimerTask = jobTimerTask(jobTimer, msTimeToStop, timeGapMilis);
				Date startTime = decideWhenToStart();
				jobTimer.schedule(jobTimerTask, startTime, decideWhenToStop(startTime, msTimeToStop)); // start now or tomorrow at 9 and stop after 7 hour 
				logger.info("Scheduler: main timer started. For the symbol " + symbol 
						+ " stock data downloading is/will be started at " + dateFormat.format(startTime)
						+ ". It will be stopped today at " + dateFormat.format(msTimeToStop + startTime.getTime())); 
			}
		};
		mainTimer.schedule(timerTask, new Date(), thisDayHourMintute(1, 8, 59).getTime()); // start immediately/repeat every next day 8.59 am
		logger.info("Scheduler: main timer started at " + dateFormat.format(new Date()) + ". And it is scheduled to restart at " + dateFormat.format(thisDayHourMintute(1, 8, 59)));
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
					logger.info("Scheduler: sub timer started. Stock data downloading for the symbol " + symbol + " is started.");
				}
			}
		};
		return tt;
	}
	
	public Date decideWhenToStart() {
		Calendar now = new GregorianCalendar();
		
		if(singleStockRunner.isMarketOpen(symbol))
			return now.getTime();
		else if(now.get(Calendar.HOUR)<15 && now.get(Calendar.MINUTE)<30)
			return thisDayHourMintute(0, 15, 30);
		else
			return thisDayHourMintute(1, 15, 30);
	}
	
	private long decideWhenToStop(Date startTime, long msTimeToStop) {
		Calendar calStartTime = new GregorianCalendar();
		calStartTime.setTime(startTime);
		
		if(calStartTime.get(Calendar.HOUR)==9 && calStartTime.get(Calendar.MINUTE)==30) // return 6.30 hour if it was started at 9.30
			return msTimeToStop;
		else {
			return thisDayHourMintute(0, 22, 0).getTime() - startTime.getTime(); // return the difference between 10 pm and now in millisecond if was started at any time of the day
		}
	}

	public Date thisDayHourMintute(int day, int hour, int minute) {
	    Calendar today = new GregorianCalendar();
	    today.add(Calendar.DATE, day);
	    Calendar result = new GregorianCalendar(today.get(Calendar.YEAR),
	        today.get(Calendar.MONTH), today.get(Calendar.DATE), hour, minute);
	    return result.getTime();
	}
}
