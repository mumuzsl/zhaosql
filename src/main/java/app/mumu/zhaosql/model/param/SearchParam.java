package app.mumu.zhaosql.model.param;

import app.mumu.zhaosql.model.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SearchParam {

    private String value;

    private String field;

    private Integer page;

}
