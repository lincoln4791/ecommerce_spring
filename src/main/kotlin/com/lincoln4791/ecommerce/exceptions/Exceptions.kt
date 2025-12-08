package com.lincoln4791.ecommerce.exceptions

class UserNotFoundException(message: String) : RuntimeException(message)

class OrderStatusNotFoundException(message: String) : RuntimeException(message)

class OrderNotFoundException(message: String) : RuntimeException(message)

class Unauthorized() : RuntimeException()