package app.mumu.zhaosql.model.dto;

import app.mumu.zhaosql.model.dto.base.OutputConverter;
import app.mumu.zhaosql.model.entity.Commodity;
import lombok.Data;

@Data
public class CommodityDTO implements OutputConverter<CommodityDTO, Commodity> {

    private Integer id;

    private String code;

    private String name;

    private Integer size;

    private String type;

    private String measure;

    private Float marketPrice;

    private Float sellingPrice;

    private Float costPrice;

    private int num;

    private String img;

    private String about;

    private String brandName;

    private Integer brandId;

    private String supplierName;

    private Integer supplierId;

    private String classifyName;

    private Integer classifyId;
}
