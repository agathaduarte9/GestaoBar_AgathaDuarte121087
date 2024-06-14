import java.io.File


fun readOrderHistory() {
    try {
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

        // Imprimir los detalles formateados como tabla
        println("+----------------------+-------------------------+-------------+")
        println("\u001B[30m\u001B[46m| ID                   | DATE & TIME             | TOTAL       |\u001B[0m")
        println("+----------------------+-------------------------+-------------+")

        orderHistoryList.forEach { orderHistory ->
            println("| ${orderHistory.orderId.padEnd(20)} | ${orderHistory.timestamp.padEnd(23)} | ${orderHistory.total.toString().padEnd(11)} |")
            println("+----------------------+-------------------------+-------------+")
        }

    } catch (e: Exception) {
        println("An error occurred while processing the file: ${e.message}")
    }
}

fun parseProductsJson(jsonString: String): List<OrderItem> {
    val products = mutableListOf<OrderItem>()

    // Remover corchetes externos y espacios en blanco adicionales
    val jsonContent = jsonString.trim('[', ']').replace(" ", "")

    // Dividir por '},' para obtener cada elemento de la lista
    val items = jsonContent.split("},")

    items.forEach { item ->
        // Remover '{' y '}' al inicio y final de cada elemento
        val trimmedItem = item.trim('{', '}')

        // Dividir por ',' para obtener los pares clave-valor
        val keyValuePairs = trimmedItem.split(",")

        var productName = ""
        var quantity = 0

        keyValuePairs.forEach { pair ->
            // Dividir por ':' para obtener la clave y el valor
            val parts = pair.split(":")
            if (parts.size == 2) {
                val key = parts[0].trim('"')
                val value = parts[1].trim('"')

                when (key) {
                    "productName" -> productName = value
                    "quantity" -> quantity = value.toIntOrNull() ?: 0
                }
            }
        }

        // Agregar el producto a la lista
        if (productName.isNotEmpty()) {
            products.add(OrderItem(productName, quantity))
        }
    }

    return products
}