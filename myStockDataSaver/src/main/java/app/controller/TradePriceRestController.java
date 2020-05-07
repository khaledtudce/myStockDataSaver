package app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import app.IEXStockDataRetrieve.IEXDataClient;
import app.helperPackage.DateConverter;
import app.model.TradePriceBuilder;
import app.model.TradePriceDto;
import app.repository.TradePriceRepository;

@RestController
public class TradePriceRestController {
	
	private static Logger logger = LogManager.getLogger(IEXDataClient.class);
	
	@Autowired
	private TradePriceRepository tradePriceRepository;
	
	@RequestMapping("/tradePrice/findBySymbolAndDate")
	public String findTradeBySymbolAndDate(@RequestParam("symbol") String symbol, @RequestParam("date") String dateStr) {
		
	    Date dateStartTime = new Date();
		try {
			dateStartTime = new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
		} catch (ParseException e) {
			logger.info("Could not parse date: " +e.getMessage());
			e.printStackTrace();
		}  
		
		Date dateEndTime = DateConverter.generateEndOfDay(dateStartTime);
		
		List<TradePriceDto> tradePriceList =  tradePriceRepository.findBySymbolAndDateTimeBetween(symbol, dateStartTime, dateEndTime);
		
		return new Gson().toJson(tradePriceList);
	}
	
	@RequestMapping("/tradePrice/findTradeBySymbolAndDateBetween")
	public String findTradeBySymbolAndDateBetween(@RequestParam("symbol") String symbol, @RequestParam("dateStart") String startDateStr, 
			@RequestParam("dateEnd") String endDateStr) {
		
		Date queryStartTime = new Date();
		Date queryEndTimeWithEndMinute = new Date();
		try {
			queryStartTime = new SimpleDateFormat("dd.MM.yyyy").parse(startDateStr);
			queryEndTimeWithEndMinute = DateConverter.generateEndOfDay(new SimpleDateFormat("dd.MM.yyyy").parse(endDateStr));
		} catch (ParseException e) {
			logger.info("Could not parse date: " +e.getMessage());
			e.printStackTrace();
		}
		
		List<TradePriceDto> tradePriceList =  tradePriceRepository
				.findBySymbolAndDateTimeBetween(symbol, queryStartTime, queryEndTimeWithEndMinute);
		
		return new Gson().toJson(tradePriceList);
	}
	
	@RequestMapping("/tradePrice/findBySymbol")
	public String findTradeBySymbol(@RequestParam("symbol") String symbol) {
		
		List<TradePriceDto> tradePriceList =  tradePriceRepository.findBySymbol(symbol);
		
		return new Gson().toJson(tradePriceList);
	}
	
	@RequestMapping("/tradePrice/saveTest")
	public String save() {
		
		TradePriceDto tradePrice = new TradePriceBuilder()
				.setSymbol("AAPL")
				.setPrice(120.3)
				.setTime(new Date())
				.setUsMarketOpen(false)
				.getTradePrice();
		
		tradePriceRepository.save(tradePrice);
		
		return "Successfully save AAPL quote into db for test";
	}

}
