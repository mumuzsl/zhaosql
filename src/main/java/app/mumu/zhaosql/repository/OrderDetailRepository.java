package app.mumu.zhaosql.repository;

import app.mumu.zhaosql.model.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends BaseRepository<OrderDetail, Long> {

    List<OrderDetail> findAllByOrderId(Long orderId);

}
