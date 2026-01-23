package com.zzan.common.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@SQLRestriction("deleted_at IS NULL")
abstract class AuditableEntity(
    id: String
) : BaseEntity(id) {
    @CreatedDate
    @Column(name = "created_at")
    var createdAt: Instant? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant? = null

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
}
