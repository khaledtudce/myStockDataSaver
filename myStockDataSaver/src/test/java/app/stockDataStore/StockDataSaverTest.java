package app.stockDataStore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import app.IEXStockDataRetrieve.IEXDataClient;
import app.IEXStockDataRetrieve.StockQuote;
import app.helperPackage.DateConverter;
import app.model.TradePriceBuilder;
import app.model.TradePriceDto;
import app.repository.TradePriceRepository;
import app.stockDataStore.StockDataSaver;

class StockDataSaverTest {

	@InjectMocks
	StockDataSaver stockDataStore = new StockDataSaver();
	
	@Mock
	IEXDataClient iexDataClient;
	
	@Mock
	TradePriceRepository tradePriceRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void should_store_data__when_provided_symbol() throws Exception {
		
		String latestUpdate = "1588046400000";
		StockQuote stockQuote = getStockQuote("AAPL", latestUpdate);
		
		when(iexDataClient.getQuoteJson(Mockito.any())).thenReturn(stockQuote);
		
		TradePriceDto tradePriceDto = getTradePriceDto(latestUpdate, stockQuote);
		when(tradePriceRepository.save(Mockito.any())).thenReturn(tradePriceDto);
		
		assertThat(stockDataStore.storeStockDataFor("AAPL")).isEqualTo(tradePriceDto);
	}
	
	@Test
	void should_be_able_to_tell_if_market_open() throws Exception {
		
		String latestUpdate = "1588046400000";
		StockQuote stockQuote = getStockQuote("AAPL", latestUpdate);
		when(iexDataClient.getQuoteJson(Mockito.any())).thenReturn(stockQuote);

		assertThat(stockDataStore.isUsMarketOpen("AAPL")).isEqualTo(true);
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
