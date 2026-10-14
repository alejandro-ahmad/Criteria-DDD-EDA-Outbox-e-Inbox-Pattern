package com.example.demo.shared.infrastructure.persistence.outbox;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@NoArgsConstructor
public class OutboxEventEntity {
    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime processedAt;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    public OutboxEventEntity(String id,String aggregateId,String eventType,String payload){
        this.id = id;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = OutboxStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void markAsCompleted(){
        this.status = OutboxStatus.COMPLETED;
        this.processedAt = LocalDateTime.now();
        this.errorMessage = null;
    }

    public void markAsFailed(String error){
        this.status = OutboxStatus.FAILED;
        this.processedAt = LocalDateTime.now();
        this.errorMessage = error;
    }
}
