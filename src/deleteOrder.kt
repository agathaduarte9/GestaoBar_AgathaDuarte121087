import java.io.File

fun eliminarOrden() {
    try {
        readOrderHistory()
        val filename = "orderHistory.csv"
        val orderHistoryList = mutableListOf<OrderHistory>()
        var index = 0

        File(filename).forEachLine { line ->
            if (index != 0) { // Saltar la primera línea que contiene los encabezados
                val columns = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                if (columns.size >= 4) {
                    val id = columns[0]
                    val dataHora = columns[1]
                    val productsJson = columns[2].trim('"')

                    // Parsear el JSON manualmente
                    val products = parseProductsJson(productsJson)
                    val total = columns[3].toDouble()

                    orderHistoryList.add(OrderHistory(id, dataHora, products, total))
                }
            }
            index++
        }

        // Solicitar al usuario que ingrese el ID de la orden a eliminar
        print("\u001B[36mEnter Order ID to view details (or enter '0' to quit): \u001B[0m")
        val input = readLine()?.trim()
        println()

        if (input.equals("0", ignoreCase = true)) {
            return
        }

        val orderToDelete = orderHistoryList.find { it.orderId == input }
        if (orderToDelete != null) {
            orderHistoryList.remove(orderToDelete)
            println("Order ID '${orderToDelete.orderId}' deleted successfully✅")
        } else {
            println("\u001B[41mOrder ID: '$input' not found⚠\uFE0F\u001B[0m")
        }

        // Escribir la lista actualizada de órdenes de historial de pedidos de vuelta al archivo CSV
        File(filename).bufferedWriter().use { writer ->
            writer.write("id,dataHora,Produtos,Total\n")
            orderHistoryList.forEach { order ->
                val productsJson = order.products.joinToString(",") { "{\"productName\":\"${it.productName}\",\"quantity\":${it.quantity}}" }
                val line = "${order.orderId},${order.timestamp},\"[$productsJson]\",${order.total}\n"
                writer.write(line)
            }
        }

    } catch (e: Exception) {
        println("An error occurred while processing the file: ${e.message}")
    }
}