package app.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.TradeOpenCloseDto;

@Repository
public interface TradeOpenCloseRepository extends CrudRepository<TradeOpenCloseDto, Long>{
	TradeOpenCloseDto findBySymbolAndOpenDateBetween(String symbol, Date openStartTime, Date openEndTime);
}
