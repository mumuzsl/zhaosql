package app.mumu.zhaosql.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@ToString
@Table(name = "order_detail")
@NoArgsConstructor
public class OrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "num", nullable = false)
    private Integer num;

    @Column(name = "sum", nullable = false)
    private Float sum;
}
