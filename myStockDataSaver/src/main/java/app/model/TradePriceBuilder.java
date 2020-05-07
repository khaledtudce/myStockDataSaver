package app.model;

import java.util.Date;

public class TradePriceBuilder {
	
	String symbol;
	double price;
	Date dateTime;
	boolean isUsMarketOpen;
	
	public TradePriceBuilder setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	public TradePriceBuilder setPrice(double price) {
		this.price = price;
		return this;
	}

	public TradePriceBuilder setTime(Date dateTime) {
		this.dateTime = dateTime;
		return this;
	}

	public TradePriceBuilder setUsMarketOpen(boolean isUsMarketOpen) {
		this.isUsMarketOpen = isUsMarketOpen;
		return this;
	}
	
	public TradePriceDto getTradePrice() {
		return new TradePriceDto(symbol, price, dateTime, isUsMarketOpen);
	}
}
