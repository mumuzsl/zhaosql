package app.mumu.zhaosql.repository;

import app.mumu.zhaosql.model.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends BaseRepository<Brand, Integer> {

    @Override
    Page<Brand> findAll(Pageable pageable);

    @Query(value = "select brand_name from brand where id = :id", nativeQuery = true)
    String selectName(@Param("id") Integer id);
}
