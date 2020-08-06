package app.mumu.zhaosql.model.entity;

import app.mumu.zhaosql.util.ServiceUtils;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@ToString
@Table(name = "brand")
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "brand_name")
    private String name;

    @Column(name = "brand_url")
    private String url;

    @Column(name = "brand_icon")
    private String icon;

}
