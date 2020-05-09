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
@Table(name = "tradeopenclose")
public class TradeOpenCloseDto implements Serializable{
	
	private static final long serialVersionUID = -1973748365490543408L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "symbol", nullable = false)
	private String symbol;
	
	@Column(name = "open_price")
	private double openPrice;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "open_time")
	private Date openDate;
	
	@Column(name = "previous_day_close")
	private double previousDayClose;

	public TradeOpenCloseDto(String symbol, double openPrice, Date openTime, double previousDayClose) {
		super();
		this.symbol = symbol;
		this.openPrice = openPrice;
		this.openDate = openTime;
		this.previousDayClose = previousDayClose;
	}

	@Override
	public String toString() {
		return "TradeOpenCloseDto [symbol=" + symbol + ", openPrice=" + openPrice + ", openDate=" + openDate
				+ ", previousDayClose=" + previousDayClose + "]";
	}

}
