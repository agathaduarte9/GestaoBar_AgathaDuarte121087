import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

fun generateOrderId(): Int {
    return Random.nextInt(100000, 1000000) // Genera un número aleatorio entre 10000 y 99999 (5 dígitos)
}

fun generateCocktailId(): Int {
    return Random.nextInt(10000, 100000) // Genera un número aleatorio entre 10000 y 99999 (5 dígitos)
}

fun generateShotId(): Int {
    return Random.nextInt(1000, 10000) // Genera un número aleatorio entre 1000 y 9999 (4 dígitos)
}

fun String.adjustToWidth(width: Int, center: Boolean = false): String {
    val strippedLength = this.replace(Regex("\\u001B\\[[;\\d]*m"), "").length
    return if (center) {
        val padding = (width - strippedLength) / 2
        " ".repeat(padding) + this + " ".repeat(width - strippedLength - padding)
    } else {
        this + " ".repeat(width - strippedLength)
    }
}

fun loadProducts(fileNames: List<String>): List<Produto> {
    val products = mutableListOf<Produto>()
    for (fileName in fileNames) {
        val lines = File(fileName).readLines()
        for (line in lines.drop(1)) {
            val parts = line.split(",")
            if (parts.size >= 3) {
                val id = parts[0].trim().toIntOrNull() ?: 0
                val nome = parts[1].trim()
                val preco = parts[2].trim().toDoubleOrNull() ?: 0.0
                products.add(object : Produto(nome, preco, id) {})
            }
        }
    }
    return products
}

fun createOrder() {
    val productFiles = listOf("Bebidas.csv", "Snacks.csv", "Shots.csv", "Cocktails.csv")
    val products = loadProducts(productFiles)
    val orders = mutableListOf<Order>()
    var continuar = true
    val menuTitle = "CREATE ORDER"
    val options = listOf(
        "(1) ++ Add Product",
        "(2) -- Remove Product",
        "(3) \uD83C\uDF76Add Customized Shot",
        "(4) \uD83C\uDF79Add Customized Cocktail",
        "(5) \uD83D\uDCDDFinish order",
        "\u001B[36m(6) Return to MAIN MENU\u001B[0m"
    )

    val longestOptionLength = options.maxByOrNull { it.replace(Regex("\\u001B\\[[;\\d]*m"), "").length }?.length ?: 0
    val boxWidth = longestOptionLength + 6 // 6 para los bordes izquierdo y derecho y el espacio adicional

    while (continuar) {
        // Imprimir la línea superior de la caja
        println("╔${"═".repeat(boxWidth - 2)}╗")

        // Imprimir el título del menú centrado
        println("\u001B[46m║ ${menuTitle.adjustToWidth(boxWidth - 4, true)} ║\u001B[0m")

        // Imprimir las opciones del menú
        for (option in options) {
            println("║ ${option.adjustToWidth(boxWidth - 4)} ║")
        }

        // Imprimir la línea inferior de la caja
        println("╚${"═".repeat(boxWidth - 2)}╝")

        val opcao = readLine()?.toIntOrNull()

        when (opcao) {
            1 -> {
                println("\u001B[30m\u001B[46mClick on the link to visualize the available products with their IDs:\u001B[0m")
                println("➡\uFE0F https://n9.cl/menuproductsguide")
                println()
                print("\u001B[36mEnter the product ID: \u001B[0m")
                val productId = readLine()?.trim()?.toIntOrNull()
                print("\u001B[36mEnter the quantity: \u001B[0m")
                val quantity = readLine()?.toIntOrNull() ?: 1

                val product = products.find { it.id == productId }
                if (product != null) {
                    orders.add(Order(product, quantity))
                    println("Added ${product.nome} x$quantity to the order \u2705")
                    val total = orders.sumOf { it.product.preco * it.quantity }
                    println()
                    printCurrentOrder(orders, total)
                } else {
                    println("\u001B[41mProduct ID not found \u26A0\uFE0F\u001B[0m")
                }
            }

            2 -> {
                printCurrentOrder(orders, orders.sumOf { it.product.preco * it.quantity })
                println()

                print("\u001B[36mEnter the product ID to remove: \u001B[0m")
                val productIdToRemove = readLine()?.trim()?.toIntOrNull()
                print("\u001B[36mEnter the quantity to remove: \u001B[0m")
                val quantityToRemove = readLine()?.toIntOrNull() ?: 1

                val productToRemove = orders.find { it.product.id == productIdToRemove }
                if (productToRemove != null) {
                    if (productToRemove.quantity <= quantityToRemove) {
                        orders.remove(productToRemove)
                        println("Removed ${productToRemove.product.nome} completely from the order ✅")
                        println()
                    } else {
                        productToRemove.quantity -= quantityToRemove
                        println("Removed $quantityToRemove ${productToRemove.product.nome}(s) from the order✅")
                    }
                    val total = orders.sumOf { it.product.preco * it.quantity }
                    printCurrentOrder(orders, total)
                } else {
                    println("\u001B[41mProduct ID not found in the order ⚠\uFE0F\u001B[0m")
                }
            }

            3 -> {
                var numberOfDrinks: Int? = null
                while (numberOfDrinks == null || numberOfDrinks !in 1..4) {
                    println("\u001B[30m\u001B[46mClick on the link to visualize the drinks that you can select for a personalized shot:\u001B[0m")
                    println("➡\uFE0F https://n9.cl/customshot")
                    println()
                    print("How many drinks do you want in your shot? (1, 2, 3, or 4): ")
                    numberOfDrinks = readLine()?.toIntOrNull()
                    if (numberOfDrinks !in 1..4) {
                        println("\u001B[41mInvalid number of drinks for a customized shot! Please try again ⚠️\u001B[0m")
                        println()
                    }
                }

                val shotId = generateShotId() // Genera un ID aleatorio de 4 dígitos
                val shot = Shots(personalizado = true, id = shotId)
                repeat(numberOfDrinks) {
                    var productId: Int?
                    var product: Produto?
                    do {
                        print("\u001B[36mEnter the product ID for drink ${shot.bebidas.size + 1} (Within 116-136 excluding 120 and 121): \u001B[0m")
                        productId = readLine()?.trim()?.toIntOrNull()
                        product = products.find { it.id == productId && it.id in 116..136 && it.id !in listOf(120, 121) }
                        if (product == null) {
                            println("\u001B[41mInvalid product ID. Please try again ⚠️\u001B[0m")
                            println()
                        }
                    } while (product == null)

                    shot.bebidas.add(product.nome)
                    shot.preco += product.preco
                }

                // Calcular el precio final por bebida del shot
                shot.preco /= numberOfDrinks
                shot.preco -= 2.5 // ajustar precio al calculo

                println()
                println("Added Customized Shot to the order ✅")
                println("Price: $${shot.preco}")
                orders.add(Order(shot, 1))

                // imprimir current order despues de agregar el shot
                val totalAfterShot = orders.sumOf { it.product.preco * it.quantity }
                printCurrentOrder(orders, totalAfterShot)
            }

            4 ->  {
                var numberOfDrinks: Int? = null
                while (numberOfDrinks == null || numberOfDrinks !in 2..4) {
                    println("\u001B[30m\u001B[46mClick on the link to visualize the drinks that you can select for a personalized cocktail:\u001B[0m")
                    println("➡\uFE0F https://n9.cl/customcocktail")
                    println()
                    print("How many drinks do you want in your cocktail? (2, 3, or 4): ")
                    numberOfDrinks = readLine()?.toIntOrNull()
                    if (numberOfDrinks !in 2..4) {
                        println("\u001B[41mInvalid number of drinks for a customized cocktail! Please try again ⚠️\u001B[0m")
                        println()
                    }
                }

                val cocktailId = generateCocktailId()
                val cocktail = Cocktails(true, mutableListOf(), "Customized Cocktail", 0.0, cocktailId)

// lista de los precios de las bebidas
                val drinkPrices = mutableListOf<Double>()

                repeat(numberOfDrinks) {
                    var productId: Int?
                    var product: Produto?
                    do {
                        print("\u001B[36mEnter the product ID for drink ${cocktail.bebidas.size + 1} (Within 116-149 excluding 120, 121, and 141): \u001B[0m")
                        productId = readLine()?.trim()?.toIntOrNull()
                        product = products.find { it.id == productId && it.id in 116..149 && it.id !in listOf(120, 121, 141) }
                        if (product == null) {
                            println("\u001B[41mInvalid product ID. Please try again ⚠️\u001B[0m")
                            println()
                        }
                    } while (product == null)

                    cocktail.bebidas.add(product!!.nome)
                    drinkPrices.add(product!!.preco)
                }

// calcular el precio final de cada bebida en el cocktail
                val totalDrinkPrices = drinkPrices.sum()
                val pricePerDrink = when (numberOfDrinks) {
                    2 -> totalDrinkPrices / 2
                    3 -> totalDrinkPrices / 3
                    4 -> totalDrinkPrices / 4
                    else -> 0.0
                }

                cocktail.preco = pricePerDrink - 0.5 // ajustar el precio a los calculos

                println()
                println("Added Customized Cocktail to the order ✅")
                println("Price: $${cocktail.preco}")
                orders.add(Order(cocktail, 1))

// imprimir la current order despues de agregar el cocktail
                val totalAfterCocktail = orders.sumOf { it.product.preco * it.quantity }
                printCurrentOrder(orders, totalAfterCocktail)

            }

            5 -> {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentDate = sdf.format(Date())
                val total = orders.sumOf { it.product.preco * it.quantity }
                val orderId = generateOrderId()
                val orderDetails = orders.map {
                    val productName = when {
                        it.product is Shots && it.product.personalizado -> "Customized Shot"
                        it.product is Cocktails && it.product.personalizado -> "Customized Cocktail"
                        else -> it.product.nome
                    }
                    "{\"productName\":\"$productName\",\"quantity\":${it.quantity}}"
                }.joinToString(",")
                val csvData = "$orderId,$currentDate,\"[$orderDetails]\",$total\n"
                File("orderHistory.csv").appendText(csvData)

                println("\u001B[36mOrder completed with ID $orderId ✅\u001B[0m")
                println("ORDER DETAILS:")
                printCurrentOrder(orders, total)
                continuar = false
            }

            6 -> {
                println("\u001B[33mReturning to MAIN MENU ☺\uFE0F\u001B[0m")
                continuar = false
            }

            else -> {
                println("\u001B[41mINVALID OPTION! Please, try again\u001B[0m")
            }
        }
    }
}

fun printCurrentOrder(orders: List<Order>, total: Double) {
    val boxWidth = 50 // Ajusta el ancho de la caja según sea necesario

    // Imprimir la línea superior de la caja
    println("╔${"═".repeat(boxWidth - 2)}╗")

    // Imprimir el título "Current Order" centrado
    println("║ ${"CURRENT ORDER".adjustToWidth(boxWidth - 4, true)} ║")

    if (orders.isEmpty()) {
        println("║ No items in the current order ║")
    } else {
        // Imprimir cada producto en el formato deseado
        for (order in orders) {
            val productName = when {
                order.product is Shots && order.product.personalizado -> "Customized Shot"
                order.product is Cocktails && order.product.personalizado -> "Customized Cocktail"
                else -> order.product.nome
            }

            val productLine = "${order.product.id}   $productName x${order.quantity}"
            val dots = ".".repeat(boxWidth - 4 - productLine.length - "$${"%.2f".format(order.product.preco * order.quantity)}".length)
            val price = "$${"%.2f".format(order.product.preco * order.quantity)}"
            println("║ $productLine$dots$price ║")
        }
    }

    // Imprimir el total
    val totalLine = "Total: $${"%.2f".format(total)}".padStart(boxWidth - 4)
    println("║ $totalLine ║")

    // Imprimir la línea inferior de la caja
    println("╚${"═".repeat(boxWidth - 2)}╝")
}
