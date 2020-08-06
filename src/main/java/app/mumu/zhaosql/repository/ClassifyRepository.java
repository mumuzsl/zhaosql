package app.mumu.zhaosql.repository;

import app.mumu.zhaosql.model.entity.Classify;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassifyRepository extends BaseRepository<Classify, Integer> {

    @Query(value = "select classify_name from classify where id =:id", nativeQuery = true)
    String selectName(@Param("id") Integer id);
}
