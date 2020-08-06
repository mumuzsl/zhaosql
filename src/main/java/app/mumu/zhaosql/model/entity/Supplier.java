package app.mumu.zhaosql.model.entity;

import app.mumu.zhaosql.util.ServiceUtils;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@ToString
@Table(name = "supplier")
public class Supplier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "supplier_name")
    private String name;

    @Column(name = "supplier_contact")
    private String contact;

    @Column(name = "supplier_contact_phone")
    private String contactPhone;

    @Column(name = "supplier_about")
    private String about;

}
