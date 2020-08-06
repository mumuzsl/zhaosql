package app.mumu.zhaosql.model.dto;

import app.mumu.zhaosql.model.entity.Commodity;
import lombok.Data;

@Data
public class OrderDetailDTO {

    private Integer num;

    private CommodityDTO commodity;

}
