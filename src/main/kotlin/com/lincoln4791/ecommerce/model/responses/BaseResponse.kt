package com.lincoln4791.ecommerce.model.responses

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class BaseResponse<T> @JsonCreator constructor(
    @JsonProperty("status_code") val status_code: Int,
    @JsonProperty("message") val message: String,
    @JsonProperty("errors") val errors: Any?,
    @JsonProperty("data") val data: T?
) : Serializable