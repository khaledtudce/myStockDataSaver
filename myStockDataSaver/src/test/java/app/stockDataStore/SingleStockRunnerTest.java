package app.stockDataStore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import app.IEXStockDataRetrieve.StockQuote;
import app.helperPackage.DateConverter;
import app.model.TradePriceBuilder;
import app.model.TradePriceDto;
import app.stockDataStore.SingleStockRunner;
import app.stockDataStore.StockDataSaver;

class SingleStockRunnerTest {
	
	@InjectMocks
	SingleStockRunner singleStockRunner = new SingleStockRunner();
	
	@Mock
	StockDataSaver StockDataSaver;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void should_download_start_for_a_symbol_and_stop() throws Exception {
		String latestUpdate = "1588046400000";
		StockQuote stockQuote = getStockQuote("AAPL", latestUpdate);
		
		TradePriceDto tradePriceDto = getTradePriceDto(latestUpdate, stockQuote);
		when(StockDataSaver.storeStockDataFor(Mockito.any())).thenReturn(tradePriceDto);
		
		singleStockRunner.startDownloadingAndSavingFor("AAPL", 500);
		assertThat(singleStockRunner.isRunning).isEqualTo(true);
		singleStockRunner.stopRunnerThread();
		assertThat(singleStockRunner.isRunning).isEqualTo(false);
	}
	
	private StockQuote getStockQuote(String symbol, String latestUpdate) {
		StockQuote stockQuote = new StockQuote();
		stockQuote.setSymbol(symbol);
		stockQuote.setLatestPrice(122.3f);
		stockQuote.setLatestUpdate(latestUpdate);
		stockQuote.setUSMarketOpen(true);
		
		return stockQuote;
	}
	
	private TradePriceDto getTradePriceDto(String latestUpdateTime, StockQuote stockQuote) {
		return new TradePriceBuilder().setSymbol(stockQuote.getSymbol())
				.setPrice(stockQuote.getLatestPrice())
				.setTime(DateConverter.convertDateFromEpoch(latestUpdateTime))
				.setUsMarketOpen(stockQuote.isUSMarketOpen()).getTradePrice();
	}

}
