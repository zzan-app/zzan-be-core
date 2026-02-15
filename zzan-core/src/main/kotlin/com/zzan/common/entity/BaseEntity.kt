package com.zzan.common.entity

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable

@MappedSuperclass
abstract class BaseEntity(
    @Id
    @Column(name = "id", length = 26, updatable = false, nullable = false)
    private val id: String
) : Persistable<String>, Serializable {

    @Transient
    private var _isNew = true

    override fun isNew(): Boolean = _isNew
    override fun getId(): String = id

    @PostLoad
    @PostPersist
    fun markNotNew() {
        this._isNew = false
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other === this) return true

        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass

        if (thisEffectiveClass != oEffectiveClass) return false

        return other is BaseEntity && this.id == other.id
    }

    override fun hashCode(): Int {
        return if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()
    }

    override fun toString(): String {
        val className = if (this is HibernateProxy) {
            this.hibernateLazyInitializer.persistentClass.simpleName
        } else {
            this.javaClass.simpleName
        }

        return "$className(id=$id)"
    }
}
