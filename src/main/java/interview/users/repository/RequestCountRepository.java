package interview.users.repository;

import interview.users.model.entity.RequestCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static interview.users.model.entity.RequestCountEntity.NAME;

@Repository
public interface RequestCountRepository extends JpaRepository<RequestCountEntity, Long> {

    String DEFAULT_SCHEMA = "{h-schema}";
    String TABLE_NAME = DEFAULT_SCHEMA + NAME;

    String UPSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (login, request_count) VALUES (:login, 1)"
        + " ON CONFLICT (login) DO UPDATE SET request_count = " + TABLE_NAME + ".request_count + 1";

    @Modifying
    @Query(nativeQuery = true,
           value = UPSERT_QUERY)
    @Transactional
    void upsertRequestCount(@Param("login") final String login);
}
