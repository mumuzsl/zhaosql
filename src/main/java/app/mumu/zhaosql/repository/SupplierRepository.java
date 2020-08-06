package app.mumu.zhaosql.repository;

import app.mumu.zhaosql.model.entity.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends BaseRepository<Supplier, Integer> {
    @Query(value = "select supplier_name from supplier where id = :id", nativeQuery = true)
    String selectName(@Param("id") Integer id);
}
