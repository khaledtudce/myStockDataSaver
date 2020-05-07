package app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.TradePriceDto;

@Repository
public interface TradePriceRepository extends CrudRepository<TradePriceDto, Long>{
	List<TradePriceDto> findBySymbol(String symbol);
	List<TradePriceDto> findBySymbolAndDateTime(String symbol, Date dateTime);
	List<TradePriceDto> findBySymbolAndDateTimeBetween(String symbol, Date startTime, Date endTime);
}
