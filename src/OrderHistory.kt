data class OrderHistory(
    val orderId: String,
    val timestamp: String,
    val products: List<OrderItem>,
    val total: Double
)