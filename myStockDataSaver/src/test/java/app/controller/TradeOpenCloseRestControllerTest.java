package app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.helperPackage.DateConverter;
import app.model.TradeOpenCloseBuilder;
import app.model.TradeOpenCloseDto;
import app.repository.TradeOpenCloseRepository;

class TradeOpenCloseRestControllerTest {

	@InjectMocks
	TradeOpenCloseRestController tradeOpenCloseRestController = new TradeOpenCloseRestController();
	
	@Mock
	TradeOpenCloseRepository tradeOpenCloseRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void should_return_data_as_json_when_asked_with_quote_and_open_time() throws ParseException {
		Date dateStartTime = new SimpleDateFormat("dd.MM.yyyy").parse("26.04.2020");
		Date dateEndTime = DateConverter.generateEndOfDay(dateStartTime);
		
		TradeOpenCloseDto tradeOpenCloseDto = new TradeOpenCloseBuilder()
				.setSymbol("AAPL")
				.setOpenTime(dateStartTime)
				.setOpenPrice(100.50)
				.setPreviousDayClose(99.0)
				.geOpenCloseDto();
		when(tradeOpenCloseRepository.findBySymbolAndOpenDateBetween("AAPL", dateStartTime, dateEndTime)).thenReturn(tradeOpenCloseDto);
		
		String jsonResult = "{\"symbol\":\"AAPL\",\"openPrice\":100.5,\"openDate\":\"Apr 26, 2020 12:00:00 AM\",\"previousDayClose\":99.0}";
		assertThat(tradeOpenCloseRestController.findBySymbolAndOpenTime("AAPL", "26.04.2020")).isEqualTo(jsonResult);
	}

}
