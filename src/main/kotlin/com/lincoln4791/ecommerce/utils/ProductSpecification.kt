package com.lincoln4791.ecommerce.utils

import com.lincoln4791.ecommerce.model.entities.Product
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object ProductSpecification {

    fun filter(
        categoryIds: List<Long>?,
        brandId: Long?,
        modelId: Long?
    ): Specification<Product> {
        return Specification { root, query, cb ->

            val predicates = mutableListOf<Predicate>()

            if (!categoryIds.isNullOrEmpty()) {
                predicates += root.get<Long>("categoryId").`in`(categoryIds)
            }

            if (brandId != null) {
                predicates += cb.equal(root.get<Long>("brandId"), brandId)
            }

            if (modelId != null) {
                predicates += cb.equal(root.get<Long>("modelId"), modelId)
            }

            cb.and(*predicates.toTypedArray())
        }
    }
}