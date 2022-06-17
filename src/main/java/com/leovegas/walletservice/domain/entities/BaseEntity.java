package com.leovegas.walletservice.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * Base entity. Describes base columns for all entities.
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    /**
     * Identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Created date time.
     */
    @Column(name = "created_at")
    private LocalDateTime created;

    /**
     * Updated date time.
     */
    @Column(name = "updated_at")
    private LocalDateTime updated;

    @PrePersist
    void prePersist() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
