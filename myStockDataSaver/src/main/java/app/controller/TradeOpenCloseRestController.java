package app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import app.helperPackage.DateConverter;
import app.model.TradeOpenCloseDto;
import app.repository.TradeOpenCloseRepository;

@RestController
public class TradeOpenCloseRestController {

	@Autowired
	TradeOpenCloseRepository tradeOpenCloseRepository;
	
	@RequestMapping("/tradeOpenClose/findBySymbolAndOpenTime")
	public String findBySymbolAndOpenTime(@RequestParam("symbol") String symbol, @RequestParam("openTime") String openTime) {
		
		Date dateStartTime = new Date();  
		try {
			dateStartTime = new SimpleDateFormat("dd.MM.yyyy").parse(openTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		Date dateEndTime = DateConverter.generateEndOfDay(dateStartTime);
		
		TradeOpenCloseDto tradeOpenCloseDto = tradeOpenCloseRepository.findBySymbolAndOpenDateBetween(symbol, dateStartTime, dateEndTime);
		
		return new Gson().toJson(tradeOpenCloseDto);
	}
}
