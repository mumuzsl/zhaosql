package app.mumu.zhaosql.model.param;

import app.mumu.zhaosql.model.dto.OrderDetailDTO;
import app.mumu.zhaosql.model.entity.Commodity;
import app.mumu.zhaosql.model.entity.Order;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OrderParam {

    private List<OrderDetailDTO> ods;

    private Order order;

    private Integer page;

}
