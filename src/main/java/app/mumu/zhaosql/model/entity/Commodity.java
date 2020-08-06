package app.mumu.zhaosql.model.entity;

import javax.persistence.*;

import app.mumu.zhaosql.util.ServiceUtils;
import lombok.*;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

@Data
@Entity
@Table(name = "commodity")
@ToString
@EqualsAndHashCode
public class Commodity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "commodity_code", unique = true)
    private String code;

    @Column(name = "commodity_name")
    private String name;

    @Column(name = "commodity_size")
    private Integer size;

    @Column(name = "commodity_type")
    private String type;

    @Column(name = "commodity_measure")
    private String measure;

    @Column(name = "commodity_market_price")
    private Float marketPrice;

    @Column(name = "commodity_selling_price")
    private Float sellingPrice;

    @Column(name = "commodity_cost_price")
    private Float costPrice;

    @Column(name = "commodity_num")
    private Integer num;

    @Column(name = "commodity_img")
    private String img;

    @Column(name = "commodity_about")
    private String about;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "classify_id")
    private Integer classifyId;

    @Column(name = "classify_name")
    private String classifyName;
}
