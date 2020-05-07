package app.IEXStockDataRetrieve;

public class StockQuote {
	private String symbol;
	private String companyName;
	private String primaryExchange;
	private String calculationPrice;
	private float open;
	private String openTime;
	private String openSource;
	private float close;
	private String closeTime;
	private String closeSource;
	private float high;
	private String highTime;
	private String highSource;
	private float low;
	private String lowTime;
	private String lowSource;
	private double latestPrice;
	private String latestSource;
	private String latestTime;
	private String latestUpdate;
	private float latestVolume;
	private float iexRealtimePrice;
	private float iexRealtimeSize;
	private float iexLastUpdated;
	private float delayedPrice;
  	private String delayedPriceTime;
  	private float oddLotDelayedPrice;
  	private float oddLotDelayedPriceTime;
  	private float extendedPrice;
  	private float extendedChange;
  	private float extendedChangePercent;
  	private String extendedPriceTime;
  	private float previousClose;
  	private float previousVolume;
  	private float change;
  	private float changePercent;
  	private float volume;
  	private float iexMarketPercent;
  	private float iexVolume;
  	private float avgTotalVolume;
  	private float iexBidPrice;
  	private float iexBidSize;
  	private float iexAskPrice;
  	private float iexAskSize;
  	private String iexOpen;
  	private String iexOpenTime;
  	private float iexClose;
  	private String iexCloseTime;
  	private float marketCap;
  	private float peRatio;
  	private float week52High;
  	private float week52Low;
  	private float ytdChange;
  	private String lastTradeTime;
    private boolean isUSMarketOpen;
    
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCompanyName() {
		return companyName;
	}
	void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	String getPrimaryExchange() {
		return primaryExchange;
	}
	void setPrimaryExchange(String primaryExchange) {
		this.primaryExchange = primaryExchange;
	}
	String getCalculationPrice() {
		return calculationPrice;
	}
	void setCalculationPrice(String calculationPrice) {
		this.calculationPrice = calculationPrice;
	}
	float getOpen() {
		return open;
	}
	void setOpen(float open) {
		this.open = open;
	}
	String getOpenTime() {
		return openTime;
	}
	void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	String getOpenSource() {
		return openSource;
	}
	void setOpenSource(String openSource) {
		this.openSource = openSource;
	}
	float getClose() {
		return close;
	}
	void setClose(float close) {
		this.close = close;
	}
	String getCloseTime() {
		return closeTime;
	}
	void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	String getCloseSource() {
		return closeSource;
	}
	void setCloseSource(String closeSource) {
		this.closeSource = closeSource;
	}
	float getHigh() {
		return high;
	}
	void setHigh(float high) {
		this.high = high;
	}
	String getHighTime() {
		return highTime;
	}
	void setHighTime(String highTime) {
		this.highTime = highTime;
	}
	String getHighSource() {
		return highSource;
	}
	void setHighSource(String highSource) {
		this.highSource = highSource;
	}
	float getLow() {
		return low;
	}
	void setLow(float low) {
		this.low = low;
	}
	String getLowTime() {
		return lowTime;
	}
	void setLowTime(String lowTime) {
		this.lowTime = lowTime;
	}
	String getLowSource() {
		return lowSource;
	}
	void setLowSource(String lowSource) {
		this.lowSource = lowSource;
	}
	public double getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(double latestPrice) {
		this.latestPrice = latestPrice;
	}
	String getLatestSource() {
		return latestSource;
	}
	void setLatestSource(String latestSource) {
		this.latestSource = latestSource;
	}
	String getLatestTime() {
		return latestTime;
	}
	void setLatestTime(String latestTime) {
		this.latestTime = latestTime;
	}
	public String getLatestUpdate() {
		return latestUpdate;
	}
	public void setLatestUpdate(String latestUpdate) {
		this.latestUpdate = latestUpdate;
	}
	float getLatestVolume() {
		return latestVolume;
	}
	void setLatestVolume(float latestVolume) {
		this.latestVolume = latestVolume;
	}
	float getIexRealtimePrice() {
		return iexRealtimePrice;
	}
	void setIexRealtimePrice(float iexRealtimePrice) {
		this.iexRealtimePrice = iexRealtimePrice;
	}
	float getIexRealtimeSize() {
		return iexRealtimeSize;
	}
	void setIexRealtimeSize(float iexRealtimeSize) {
		this.iexRealtimeSize = iexRealtimeSize;
	}
	float getIexLastUpdated() {
		return iexLastUpdated;
	}
	void setIexLastUpdated(float iexLastUpdated) {
		this.iexLastUpdated = iexLastUpdated;
	}
	float getDelayedPrice() {
		return delayedPrice;
	}
	void setDelayedPrice(float delayedPrice) {
		this.delayedPrice = delayedPrice;
	}
	String getDelayedPriceTime() {
		return delayedPriceTime;
	}
	void setDelayedPriceTime(String delayedPriceTime) {
		this.delayedPriceTime = delayedPriceTime;
	}
	float getOddLotDelayedPrice() {
		return oddLotDelayedPrice;
	}
	void setOddLotDelayedPrice(float oddLotDelayedPrice) {
		this.oddLotDelayedPrice = oddLotDelayedPrice;
	}
	float getOddLotDelayedPriceTime() {
		return oddLotDelayedPriceTime;
	}
	void setOddLotDelayedPriceTime(float oddLotDelayedPriceTime) {
		this.oddLotDelayedPriceTime = oddLotDelayedPriceTime;
	}
	float getExtendedPrice() {
		return extendedPrice;
	}
	void setExtendedPrice(float extendedPrice) {
		this.extendedPrice = extendedPrice;
	}
	float getExtendedChange() {
		return extendedChange;
	}
	void setExtendedChange(float extendedChange) {
		this.extendedChange = extendedChange;
	}
	float getExtendedChangePercent() {
		return extendedChangePercent;
	}
	void setExtendedChangePercent(float extendedChangePercent) {
		this.extendedChangePercent = extendedChangePercent;
	}
	String getExtendedPriceTime() {
		return extendedPriceTime;
	}
	void setExtendedPriceTime(String extendedPriceTime) {
		this.extendedPriceTime = extendedPriceTime;
	}
	float getPreviousClose() {
		return previousClose;
	}
	void setPreviousClose(float previousClose) {
		this.previousClose = previousClose;
	}
	float getPreviousVolume() {
		return previousVolume;
	}
	void setPreviousVolume(float previousVolume) {
		this.previousVolume = previousVolume;
	}
	float getChange() {
		return change;
	}
	void setChange(float change) {
		this.change = change;
	}
	float getChangePercent() {
		return changePercent;
	}
	void setChangePercent(float changePercent) {
		this.changePercent = changePercent;
	}
	float getVolume() {
		return volume;
	}
	void setVolume(float volume) {
		this.volume = volume;
	}
	float getIexMarketPercent() {
		return iexMarketPercent;
	}
	void setIexMarketPercent(float iexMarketPercent) {
		this.iexMarketPercent = iexMarketPercent;
	}
	float getIexVolume() {
		return iexVolume;
	}
	void setIexVolume(float iexVolume) {
		this.iexVolume = iexVolume;
	}
	float getAvgTotalVolume() {
		return avgTotalVolume;
	}
	void setAvgTotalVolume(float avgTotalVolume) {
		this.avgTotalVolume = avgTotalVolume;
	}
	float getIexBidPrice() {
		return iexBidPrice;
	}
	void setIexBidPrice(float iexBidPrice) {
		this.iexBidPrice = iexBidPrice;
	}
	float getIexBidSize() {
		return iexBidSize;
	}
	void setIexBidSize(float iexBidSize) {
		this.iexBidSize = iexBidSize;
	}
	float getIexAskPrice() {
		return iexAskPrice;
	}
	void setIexAskPrice(float iexAskPrice) {
		this.iexAskPrice = iexAskPrice;
	}
	float getIexAskSize() {
		return iexAskSize;
	}
	void setIexAskSize(float iexAskSize) {
		this.iexAskSize = iexAskSize;
	}
	String getIexOpen() {
		return iexOpen;
	}
	void setIexOpen(String iexOpen) {
		this.iexOpen = iexOpen;
	}
	String getIexOpenTime() {
		return iexOpenTime;
	}
	void setIexOpenTime(String iexOpenTime) {
		this.iexOpenTime = iexOpenTime;
	}
	float getIexClose() {
		return iexClose;
	}
	void setIexClose(float iexClose) {
		this.iexClose = iexClose;
	}
	String getIexCloseTime() {
		return iexCloseTime;
	}
	void setIexCloseTime(String iexCloseTime) {
		this.iexCloseTime = iexCloseTime;
	}
	float getMarketCap() {
		return marketCap;
	}
	void setMarketCap(float marketCap) {
		this.marketCap = marketCap;
	}
	float getPeRatio() {
		return peRatio;
	}
	void setPeRatio(float peRatio) {
		this.peRatio = peRatio;
	}
	float getWeek52High() {
		return week52High;
	}
	void setWeek52High(float week52High) {
		this.week52High = week52High;
	}
	float getWeek52Low() {
		return week52Low;
	}
	void setWeek52Low(float week52Low) {
		this.week52Low = week52Low;
	}
	float getYtdChange() {
		return ytdChange;
	}
	void setYtdChange(float ytdChange) {
		this.ytdChange = ytdChange;
	}
	String getLastTradeTime() {
		return lastTradeTime;
	}
	void setLastTradeTime(String lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}
	public boolean isUSMarketOpen() {
		return isUSMarketOpen;
	}
	public void setUSMarketOpen(boolean isUSMarketOpen) {
		this.isUSMarketOpen = isUSMarketOpen;
	}
	  

	  
}