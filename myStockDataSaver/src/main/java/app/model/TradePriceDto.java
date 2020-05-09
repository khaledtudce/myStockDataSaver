package app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tradeprice")
public class TradePriceDto implements Serializable{
	
	private static final long serialVersionUID = 7933873533400147808L;	
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "symbol")
	private String symbol;
	
	@Column(name = "price")
	private double price;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_time", columnDefinition= "TIMESTAMP WITH TIME ZONE")
	private Date dateTime;
	
	@Column(name = "is_us_market_open")
	private boolean isUsMarketOpen;
	
	public TradePriceDto() {}

	public TradePriceDto(String symbol, double price, Date dateTime, boolean isUsMarketOpen) {
		super();
		this.symbol = symbol;
		this.price = price;
		this.dateTime = dateTime;
		this.isUsMarketOpen = isUsMarketOpen;
	}

	@Override
	public String toString() {
		return "TradePriceDto [symbol=" + symbol + ", price=" + price + ", dateTime=" + dateTime + ", isUsMarketOpen="
				+ isUsMarketOpen + "]";
	}

}
