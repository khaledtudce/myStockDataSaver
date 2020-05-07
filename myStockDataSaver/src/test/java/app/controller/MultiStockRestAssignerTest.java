package app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import app.SpringContext;
import app.controller.MultiStockRestAssigner;
import app.stockDataStore.SingleStockRunner;
import app.stockDataStore.StockRetrieveScheduler;

@Ignore
class MultiStockRestAssignerTest {
	
	@InjectMocks
	MultiStockRestAssigner multiStockAssigner = new MultiStockRestAssigner();
	
	@Mock
	SingleStockRunner singleStockRunner;
	
	@Mock
	StockRetrieveScheduler stockRetrieveScheduler;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void should_start_downloading_by_given_stock_symbol_and_distance_by_rest() {
		
//		doNothing().when(ArgumentMatchers.any(StockRetrieveScheduler.class)).schuduleStockToStart(500);
		
		assertThat(multiStockAssigner.startDownload("AAPL", 500)).isEqualTo("Scheduler started");
		assertThat(multiStockAssigner.getCurrentDownloadingList())
		.isEqualTo(new ArrayList<String>() {{ add("AAPL");}});
//		multiStockAssigner.stopDownload("AAPL");
	}
	
	@Test
	void should_not_add_stock_symbol_downloading_list_when_already_exists() {
		
		doNothing().when(ArgumentMatchers.any(StockRetrieveScheduler.class)).schuduleStockToStart(500);
		multiStockAssigner.startDownload("AAPL", 500);
		multiStockAssigner.startDownload("AAPL", 500);
		assertThat(multiStockAssigner.getCurrentDownloadingList())
		.isEqualTo(new ArrayList<String>() {{ add("AAPL");}});
		multiStockAssigner.stopDownload("AAPL");
	}
	
	@Test
	void should_stop_downloading_by_given_stock_symbol_by_rest() {
		
		multiStockAssigner.startDownload("AAPL", 500);
		
		assertThat(multiStockAssigner.stopDownload("AAPL")).isEqualTo("Scheduler stoped for AAPL");
	}
	
	@Test
	void should_tell_if_stop_downloading_by_given_wrong_stock_symbol_called() {
		
		doNothing().when(ArgumentMatchers.any(StockRetrieveScheduler.class)).schuduleStockToStart(500);
		
		assertThat(multiStockAssigner.stopDownload("AAPL")).isEqualTo("AAPL was not exist in the stock downloading list");
	}	
	
	@Test
	void should_be_able_to_tell_list_of_symbol_currently_downloading_by_rest() {
		multiStockAssigner.startDownload("AAPL", 500);
		multiStockAssigner.startDownload("MSFT", 500);
		
		assertThat(multiStockAssigner.getCurrentDownloadingList())
		.isEqualTo(new ArrayList<String>() {{ add("AAPL"); add("MSFT"); }});
		multiStockAssigner.stopAll();
	}
	
	@Test
	void should_be_able_to_stop_the_whole_app_by_rest() {
		multiStockAssigner.startDownload("AAPL", 500);
		multiStockAssigner.stopAll();
		
		assertThat(multiStockAssigner.getCurrentDownloadingList())
		.isEqualTo(new ArrayList<String>() {{ add("AAPL"); }});
	}
	
	@Test
	void should_be_able_to_start_the_whole_app_by_rest() {
		multiStockAssigner.startDownload("AAPL", 500);
		multiStockAssigner.startDownload("MSFT", 500);
		multiStockAssigner.stopAll();
		multiStockAssigner.startAll(500);
		
		assertThat(multiStockAssigner.getCurrentDownloadingList())
		.isEqualTo(new ArrayList<String>() {{ add("AAPL"); add("MSFT"); }});
		multiStockAssigner.stopAll();
	}

}
