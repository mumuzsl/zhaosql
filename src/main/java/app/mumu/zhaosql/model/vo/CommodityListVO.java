package app.mumu.zhaosql.model.vo;

import app.mumu.zhaosql.model.dto.CommodityDTO;
import lombok.Data;

import java.util.List;

@Data
public class CommodityListVO {

    private Integer totalPage;

    private Integer page;

    private List<CommodityDTO> commoditys;

}
