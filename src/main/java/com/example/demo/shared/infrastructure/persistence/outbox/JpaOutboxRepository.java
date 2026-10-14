package com.example.demo.shared.infrastructure.persistence.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaOutboxRepository extends JpaRepository<OutboxEventEntity,String> {
    @Query(value = """
    SELECT * FROM outbox_events
    WHERE status = 'PENDING'
    ORDER BY created_at ASC
    LIMIT :batchSize
    FOR UPDATE SKIP LOCKED
""",nativeQuery = true)
    List<OutboxEventEntity> findPendingForProcessing(@Param("batchSize") int batchSize);
}
