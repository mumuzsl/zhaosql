package app.mumu.zhaosql.repository;

import app.mumu.zhaosql.model.entity.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface OrderRepository extends BaseRepository<Order, Long> {

    @Query(value = "select  " +
            "max(`total_price`) as `total_price_max`, " +
            "min(`total_price`) as `total_price_min`, " +
            "avg(`total_price`) as `total_price_avg`, " +
            "count(*) as `count` " +
            "from `order`", nativeQuery = true)
    Map<String, Object> getMaxMin();

}
