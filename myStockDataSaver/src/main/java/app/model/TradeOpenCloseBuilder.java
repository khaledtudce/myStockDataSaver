package app.model;

import java.util.Date;

public class TradeOpenCloseBuilder {
	String symbol;
	double openPrice;
	Date openDate;
	double previousDayClose;
	
	public TradeOpenCloseBuilder setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}
	
	public TradeOpenCloseBuilder setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
		return this;
	}
	
	public TradeOpenCloseBuilder setOpenTime(Date openTime) {
		this.openDate = openTime;
		return this;
	}
	
	public TradeOpenCloseBuilder setPreviousDayClose(double previousDayClose) {
		this.previousDayClose = previousDayClose;
		return this;
	}
	
	public TradeOpenCloseDto geOpenCloseDto() {
		return new TradeOpenCloseDto(symbol, openPrice, openDate, previousDayClose);
	}
}
