package app.mumu.zhaosql.repository;

import app.mumu.zhaosql.model.entity.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CommodityRepository extends BaseRepository<Commodity, Integer>, JpaSpecificationExecutor<Commodity> {

    //Optional<Commodity> findByCode(@NonNull String code);

    boolean existsByCode(@NonNull String code);
}
