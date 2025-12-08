package com.lincoln4791.ecommerce.model.responses

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
data class PageResponse<T> @JsonCreator constructor(
    @JsonProperty("items") val items: List<T>,
    @JsonProperty("page") val page: Int,
    @JsonProperty("size") val size: Int,
    @JsonProperty("total") val total: Long,
    @JsonProperty("totalPages") val totalPages: Int,
    @JsonProperty("last") val last: Boolean
) : Serializable