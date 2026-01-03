package com.lincoln4791.ecommerce.model.responses.order

import com.lincoln4791.ecommerce.model.enums.OrderStatusEnum
import com.lincoln4791.ecommerce.model.enums.PaymentMethodEnum
import com.lincoln4791.ecommerce.model.responses.delivery.DeliveryTrackingResponse

data class OrderResponse(
    val id: Long = 0,
    val userId: Long,
    val totalAmount: Double,
    val items: MutableList<OrderItemResponse> = mutableListOf(),
    var deliveryStatus: String= OrderStatusEnum.Pending.name,
    val paymentMethod: String= PaymentMethodEnum.COD.name,
)