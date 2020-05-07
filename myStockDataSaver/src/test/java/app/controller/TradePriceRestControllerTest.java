package app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.helperPackage.DateConverter;
import app.model.TradePriceBuilder;
import app.model.TradePriceDto;
import app.repository.TradePriceRepository;

class TradePriceRestControllerTest {
	
	@InjectMocks
	TradePriceRestController tradePriceService = new TradePriceRestController();
	
	@Mock
	TradePriceRepository tradePriceRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void should_get_stock_data_based_on_quote_and_date() throws ParseException {
		Date oneDateTime = DateConverter.generateDateWithFormat("26.04.2020 02:20:30");
		Date anotherDateTime = DateConverter.generateDateWithFormat("26.04.2020 04:30:30");
		
		ArrayList<TradePriceDto> tradePriceDtoList = new ArrayList<TradePriceDto>();
		tradePriceDtoList.add(generateTradePriceDto("AAPL", 120.3, oneDateTime, false));
		tradePriceDtoList.add(generateTradePriceDto("AAPL", 122.3, anotherDateTime, true));
		
		String dateStrForAssert = "26.04.2020";
		Date queryStartTime = new SimpleDateFormat("dd.MM.yyyy").parse(dateStrForAssert);
		Date queryEndTime = DateConverter.generateEndOfDay(queryStartTime);
		when(tradePriceRepository.findBySymbolAndDateTimeBetween("AAPL", queryStartTime, queryEndTime)).thenReturn(tradePriceDtoList);
		
		String expectedResult = "[{\"symbol\":\"AAPL\",\"price\":120.3,\"dateTime\":\"Apr 26, 2020 2:20:30 AM\",\"isUsMarketOpen\":false},{\"symbol\":\"AAPL\",\"price\":122.3,\"dateTime\":\"Apr 26, 2020 4:30:30 AM\",\"isUsMarketOpen\":true}]";
		assertThat(tradePriceService.findTradeBySymbolAndDate("AAPL", dateStrForAssert)).isEqualTo(expectedResult);
	}

	@Test
	void should_return_data_as_json_for_multiple_days_when_given_start_date_and_end_date() throws ParseException {
		Date oneDateTime = DateConverter.generateDateWithFormat("26.04.2020 02:20:30");
		Date anotherDateTime = DateConverter.generateDateWithFormat("27.04.2020 04:30:30");
		Date thirdDateTime = DateConverter.generateDateWithFormat("28.04.2020 04:30:30");
		
		ArrayList<TradePriceDto> tradePriceDtoList = new ArrayList<TradePriceDto>();
		tradePriceDtoList.add(generateTradePriceDto("AAPL", 120.3, oneDateTime, false));
		tradePriceDtoList.add(generateTradePriceDto("AAPL", 122.3, anotherDateTime, true));
		tradePriceDtoList.add(generateTradePriceDto("AAPL", 125.8, thirdDateTime, true));
		
		String startDateStrForAssert = "26.04.2020";
		String endDateStrForAssert = "28.04.2020";
		Date queryStartTime = new SimpleDateFormat("dd.MM.yyyy").parse(startDateStrForAssert);
		Date queryEndTime = new SimpleDateFormat("dd.MM.yyyy").parse(endDateStrForAssert);
		Date queryEndTimeWithEndMinute = DateConverter.generateEndOfDay(queryEndTime);
		
		when(tradePriceRepository.findBySymbolAndDateTimeBetween("AAPL", queryStartTime, queryEndTimeWithEndMinute)).thenReturn(tradePriceDtoList);

		String expectedResult = "[{\"symbol\":\"AAPL\",\"price\":120.3,\"dateTime\":\"Apr 26, 2020 2:20:30 AM\",\"isUsMarketOpen\":false},{\"symbol\":\"AAPL\",\"price\":122.3,\"dateTime\":\"Apr 27, 2020 4:30:30 AM\",\"isUsMarketOpen\":true},{\"symbol\":\"AAPL\",\"price\":125.8,\"dateTime\":\"Apr 28, 2020 4:30:30 AM\",\"isUsMarketOpen\":true}]";
		assertThat(tradePriceService.findTradeBySymbolAndDateBetween("AAPL", startDateStrForAssert, endDateStrForAssert)).isEqualTo(expectedResult);
	}
	
	@Test
	void should_return_data_as_json__when_quote_is_given() throws ParseException {
		Date oneDateTime = DateConverter.generateDateWithFormat("26.04.2020 02:20:30");
		
		ArrayList<TradePriceDto> tradePriceDtoList = new ArrayList<TradePriceDto>();
		tradePriceDtoList.add(generateTradePriceDto("AAPL", 120.3, oneDateTime, false));
		
		when(tradePriceRepository.findBySymbol("AAPL")).thenReturn(tradePriceDtoList);
		String expectedResult = "[{\"symbol\":\"AAPL\",\"price\":120.3,\"dateTime\":\"Apr 26, 2020 2:20:30 AM\",\"isUsMarketOpen\":false}]";
		assertThat(tradePriceService.findTradeBySymbol("AAPL")).isEqualTo(expectedResult);
	}
	
	private TradePriceDto generateTradePriceDto(String quote, double price, Date dateTime, Boolean isMarketOpen) {
		return new TradePriceBuilder().setSymbol(quote).setPrice(price)
				.setTime(dateTime).setUsMarketOpen(isMarketOpen).getTradePrice();
	}
}
