package app.mumu.zhaosql.model.entity;

import app.mumu.zhaosql.util.ServiceUtils;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@ToString
@Table(name = "classify")
public class Classify extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "classify_name")
    private String name;

    @Column(name = "classify_icon")
    private String icon;

    @Column(name = "classify_parent")
    private Integer parent;

    @Column(name = "classify_level")
    private Integer level;

}
