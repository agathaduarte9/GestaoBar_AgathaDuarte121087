import java.io.File


fun orderInDetail() {
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
        println()

        // Solicitar al usuario que ingrese el ID de la orden a ver en detalle
        print("\u001B[36mEnter Order ID to view details (or enter '0' to quit): \u001B[0m")

        val input = readLine()?.trim()
        println()

        if (input.equals("0", ignoreCase = true)) {
            return
        }

        val orderToView = orderHistoryList.find { it.orderId == input }
        if (orderToView != null) {
            // Imprimir en formato de caja con bordes ═
            val maxLength = 50 // Longitud máxima para la caja
            val title = "ORDER ID: ${orderToView.orderId}"
            val dateTime = "Date and Time: ${orderToView.timestamp}"
            val totalAmount = "Total: ${orderToView.total}$"

            println("╔${"═".repeat(maxLength - 2)}╗")
            println("║${title.center(maxLength - 2)}║")
            println("║${dateTime.center(maxLength - 2)}║")
            println("╠${"═".repeat(maxLength - 2)}╣")
            orderToView.products.forEach { orderItem ->
                val productLine = "║ ${orderItem.productName.padEnd(maxLength - 10, '.')} ${orderItem.quantity.toString().padStart(5)} ║"
                println(productLine)
            }
            println("╠${"═".repeat(maxLength - 2)}╣")
            println("║${totalAmount.padStart(maxLength - 2)}║")
            println("╚${"═".repeat(maxLength - 2)}╝")
        } else {
            println("\u001B[41mOrder ID: '$input' not found⚠\uFE0F\u001B[0m")
        }

    } catch (e: Exception) {
        println("An error occurred while processing the file: ${e.message}")
    }
}

// Función para centrar un string dentro de una longitud determinada
fun String.center(length: Int): String {
    if (this.length >= length) {
        return this
    } else {
        val leftPad = (length - this.length) / 2
        val rightPad = length - this.length - leftPad
        return " ".repeat(leftPad) + this + " ".repeat(rightPad)
    }
}

