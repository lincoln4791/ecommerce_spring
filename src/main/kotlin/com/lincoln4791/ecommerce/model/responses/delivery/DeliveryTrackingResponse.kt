package com.lincoln4791.ecommerce.model.responses.delivery

import com.fasterxml.jackson.annotation.JsonBackReference
import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.model.enums.OrderStatusEnum
import com.lincoln4791.ecommerce.model.responses.order.OrderResponse
import jakarta.persistence.*

data class DeliveryTrackingResponse(
    val id: Long = 0,
    val status: String=OrderStatusEnum.Pending.name,
    val timestamp: Long = System.currentTimeMillis()
){

}
