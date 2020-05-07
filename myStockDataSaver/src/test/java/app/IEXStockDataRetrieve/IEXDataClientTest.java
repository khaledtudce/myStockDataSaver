package app.IEXStockDataRetrieve;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.IEXStockDataRetrieve.IEXDataClient;

class IEXDataClientTest {

	IEXDataClient iexDataClient = new IEXDataClient();
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void shouldReturnSomething() throws Exception {
		assertNotNull(iexDataClient.getQuoteJson("AAPL"));
	}

	@Test
	public void should_return_data_from_asked_quote() throws Exception {
		assertThat(iexDataClient.getQuoteJson("AAPL").getCompanyName()).isEqualTo("Apple, Inc.");
		assertThat(iexDataClient.getQuoteJson("AAPL").getSymbol()).isEqualTo("AAPL");
	}
}
