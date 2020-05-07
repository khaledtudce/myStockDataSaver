package app.stockDataStore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import app.stockDataStore.SingleStockRunner;
import app.stockDataStore.StockRetrieveScheduler;

class StockRetrieveSchedulerTest {
	
	@Mock
	SingleStockRunner singleStockRunner;
	
	@InjectMocks
	StockRetrieveScheduler stockRetrieveScheduler = new StockRetrieveScheduler();

	Calendar now = Calendar.getInstance();
	
	public StockRetrieveSchedulerTest() {
		stockRetrieveScheduler.setStockRetrieveScheduler("AAPL", singleStockRunner);
	}
	
	@BeforeEach
	void setUp() throws Exception {
		now.setTime(new Date());
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void should_provide_minute_and_hour_Zero_when_nothing_changed() throws ParseException {		
		Calendar receivedDate = Calendar.getInstance();
		receivedDate.setTime(stockRetrieveScheduler.thisDayHourMintute(0, 0, 0));
		
		assertThat(now.get(Calendar.YEAR)).isEqualTo(receivedDate.get(Calendar.YEAR)); 
		assertThat(now.get(Calendar.MONTH)).isEqualTo(receivedDate.get(Calendar.MONTH)); 
		assertThat(now.get(Calendar.DAY_OF_WEEK)).isEqualTo(receivedDate.get(Calendar.DAY_OF_WEEK)); 
		assertThat(receivedDate.get(Calendar.HOUR)).isEqualTo(0);
		assertThat(receivedDate.get(Calendar.MINUTE)).isEqualTo(0);
		assertThat(receivedDate.get(Calendar.SECOND)).isEqualTo(0); 
	}
	
	@Test
	void should_provide_day_hour_minute_when_provided() throws ParseException {		
		Calendar receivedDate = Calendar.getInstance();
		receivedDate.setTime(stockRetrieveScheduler.thisDayHourMintute(1, 10, 1));
		
		now.add(Calendar.DAY_OF_MONTH, 1);
		
		assertThat(now.get(Calendar.YEAR)).isEqualTo(receivedDate.get(Calendar.YEAR)); 
		assertThat(now.get(Calendar.MONTH)).isEqualTo(receivedDate.get(Calendar.MONTH)); 
		assertThat(now.get(Calendar.DAY_OF_MONTH)).isEqualTo(receivedDate.get(Calendar.DAY_OF_MONTH)); 
		assertThat(receivedDate.get(Calendar.HOUR)).isEqualTo(10);
		assertThat(receivedDate.get(Calendar.MINUTE)).isEqualTo(1);
		assertThat(receivedDate.get(Calendar.SECOND)).isEqualTo(0); 
	}

	@Test
	void when_app_running_should_start_and_stop_timer_for_a_stock() {		
				
		stockRetrieveScheduler.schuduleStockToStart(500);
		assertThat(true).isEqualTo(stockRetrieveScheduler.isMainTimerRunning); 
		
		stockRetrieveScheduler.stopMainTimer();
		assertThat(false).isEqualTo(stockRetrieveScheduler.isMainTimerRunning); 		
	}
	
	@Test
	void when_market_is_open__should_start_immediately() {	
		when(singleStockRunner.isMarketOpen(Mockito.any())).thenReturn(true);
				
		Calendar receivedDate = Calendar.getInstance();
		receivedDate.setTime(stockRetrieveScheduler.decideWhenToStart());
		
		assertThat(now.get(Calendar.YEAR)).isEqualTo(receivedDate.get(Calendar.YEAR)); 
		assertThat(now.get(Calendar.MONTH)).isEqualTo(receivedDate.get(Calendar.MONTH)); 
		assertThat(now.get(Calendar.DAY_OF_MONTH)).isEqualTo(receivedDate.get(Calendar.DAY_OF_MONTH));
		assertThat(now.get(Calendar.HOUR)).isEqualTo(receivedDate.get(Calendar.HOUR));
	}
	
	@Test
	void when_market_is_closed__should_start_should_start_tomorrow_at_9_am() {	
		when(singleStockRunner.isMarketOpen(Mockito.any())).thenReturn(false);
				
		Calendar receivedDate = Calendar.getInstance();
		receivedDate.setTime(stockRetrieveScheduler.decideWhenToStart());
		
		now.add(Calendar.DAY_OF_MONTH, 1);
		
		assertThat(now.get(Calendar.YEAR)).isEqualTo(receivedDate.get(Calendar.YEAR)); 
		assertThat(now.get(Calendar.MONTH)).isEqualTo(receivedDate.get(Calendar.MONTH)); 
		assertThat(now.get(Calendar.DAY_OF_MONTH)).isEqualTo(receivedDate.get(Calendar.DAY_OF_MONTH));
		assertThat(receivedDate.get(Calendar.HOUR)).isEqualTo(9);
	}
}









