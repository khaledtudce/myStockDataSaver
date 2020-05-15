package app.stockDataStore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockRetrieveScheduler {
	
	private static Logger logger = LogManager.getLogger(StockRetrieveScheduler.class);
	
	@Autowired
	SingleStockRunner singleStockRunner;
	
	Timer mainTimer;
	public Boolean isMainTimerRunning = false;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public void stopMainTimer(String symbol) {
		singleStockRunner.stopRunnerTimer(symbol);
		mainTimer.purge();
		mainTimer.cancel();
		isMainTimerRunning = false;
		logger.info("Timer stopped for the Symbol: " + symbol);
	}
	
	public void schuduleStockToStart(String symbol, int timeGapMilis) {
		mainTimer = new Timer(); // timer to run other scheduler, repeat everyday at 15.30 am
		
		TimerTask timerTask = new TimerTask(){
			public void run(){ 	//task 9.30 to 4pm, DE 15.30 to 10 pm
				isMainTimerRunning = true;
				long msTimeToStop = 6 * 60 * 60 *1000 + 30 * 60 * 1000; // stop after 6.50 hour which is at 10.00 pm
				Timer jobTimer = new Timer(); // timer to start the job
				TimerTask jobTimerTask = jobTimerTask(jobTimer, symbol, msTimeToStop, timeGapMilis);
				Date startTime = decideWhenToStart(symbol);
				long whenToStop = decideWhenToStop(startTime, msTimeToStop);
				jobTimer.schedule(jobTimerTask, startTime, whenToStop); // start now or tomorrow at 15.30 and stop after 6.50 hour 
				logger.info("Scheduler: sub timer scheduled. For the symbol " + symbol 
						+ " stock data downloading is/will be started at " + dateFormat.format(startTime)
						+ ". It will be stopped at " + dateFormat.format(whenToStop + startTime.getTime())); 
			}
		};
		mainTimer.schedule(timerTask, new Date(), thisDayHourMintute(1, 14, 29).getTime()); // start immediately/repeat every next day 14.29 am
		logger.info("Scheduler: main timer started at " + dateFormat.format(new Date()) + ". And it is scheduled to restart at " + dateFormat.format(thisDayHourMintute(1, 8, 59)));
	}

	private TimerTask jobTimerTask(Timer jobTimer, String symbol, long msTimeToStop, int timeGapMilis) {
		TimerTask tt = new TimerTask(){
			Boolean isTimerCloseable = false;
			public void run(){
				if (isTimerCloseable == true) {
					singleStockRunner.stopRunnerTimer(symbol);
					cancel();
					jobTimer.purge();
					jobTimer.cancel();
					logger.info("Scheduler: sub timer is closed for the Symbol: " + symbol);
				}else {
					singleStockRunner.startDownloadingAndSavingFor(symbol, timeGapMilis); // task
					isTimerCloseable = true;
					logger.info("Scheduler: sub timer started. Stock data downloading for the symbol: " + symbol);
				}
			}
		};
		return tt;
	}
	
	public Date decideWhenToStart(String symbol) {
		Calendar now = new GregorianCalendar();
		
		if(singleStockRunner.isMarketOpen(symbol))
			return now.getTime();
		else if(now.get(Calendar.HOUR_OF_DAY)<15 && now.get(Calendar.MINUTE)<30)
			return thisDayHourMintute(0, 15, 30);
		else
			return thisDayHourMintute(1, 15, 30);
	}
	
	private long decideWhenToStop(Date startTime, long msTimeToStop) {
		Calendar calStartTime = new GregorianCalendar();
		calStartTime.setTime(startTime);
		
		if(calStartTime.get(Calendar.HOUR_OF_DAY)==15 && calStartTime.get(Calendar.MINUTE)==30) // return 6.30 hour if it was started at 9.30
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
