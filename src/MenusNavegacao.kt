fun mainMenu() {
    var continuar = true
    val menuTitle = "MAIN MENU"
    val options = listOf(
        "(1) \uD83C\uDF7ASee Bar Menus",
        "(2) \uD83D\uDCDDCreate Order",
        "(3) \uD83D\uDD52View Order History",
        "(4) \uD83D\uDCB5View Total Sales",
        "\u001B[35m(5) Exit Cash Register\u001B[0m"
    )

    fun String.adjustToWidth(width: Int, center: Boolean = false): String {
        val strippedLength = this.replace(Regex("\\u001B\\[[;\\d]*m"), "").length
        return if (center) {
            val padding = (width - strippedLength) / 2
            " ".repeat(padding) + this + " ".repeat(width - strippedLength - padding)
        } else {
            this + " ".repeat(width - strippedLength)
        }
    }

    val longestOptionLength = options.maxByOrNull { it.replace(Regex("\\u001B\\[[;\\d]*m"), "").length }?.length ?: 0
    val boxWidth = longestOptionLength + 8 // 8 para los bordes izquierdo y derecho y el espacio adicional

    while (continuar) {
        // Imprimir la línea superior de la caja
        println("╔${"═".repeat(boxWidth - 2)}╗")

        // Imprimir el título del menú centrado
        println("\u001B[45m║ ${menuTitle.adjustToWidth(boxWidth - 4, true)} ║\u001B[0m")

        // Imprimir las opciones del menú
        for (option in options) {
            println("║ ${option.adjustToWidth(boxWidth - 4)} ║")
        }

        // Imprimir la línea inferior de la caja
        println("╚${"═".repeat(boxWidth - 2)}╝")

        val opcao = readLine()?.toIntOrNull()

        when (opcao) {
            1 -> {
                barMenus()
            }

            2 -> {
                createOrder()
            }

            3 -> {
                viewOrderHistoryMenu()
            }

            4 -> {
                imprimirTotalVentas()
            }

            5 -> {
                println("\u001B[33mExiting Cash Register ☺\uFE0F\u001B[0m")
                continuar = false
            }

            else -> {
                println("\u001B[41mINVALID OPTION! Please, try again\u001B[0m")
            }
        }
    }
}

fun barMenus() {
    var continuar = true
    val menuTitle = "BAR MENUS"
    val options = listOf(
        "(1) \uD83C\uDF77See Drink Menu",
        "(2) \uD83C\uDF79See Cocktail Menu",
        "(3) \uD83C\uDF76See Shot Menu",
        "(4) \uD83C\uDF64See Appetizer Menu",
        "\u001B[36m(5) Return to MAIN MENU\u001B[0m"
    )

    fun String.adjustToWidth(width: Int, center: Boolean = false): String {
        val strippedLength = this.replace(Regex("\\u001B\\[[;\\d]*m"), "").length
        return if (center) {
            val padding = (width - strippedLength) / 2
            " ".repeat(padding) + this + " ".repeat(width - strippedLength - padding)
        } else {
            this + " ".repeat(width - strippedLength)
        }
    }

    val longestOptionLength = options.maxByOrNull { it.replace(Regex("\\u001B\\[[;\\d]*m"), "").length }?.length ?: 0
    val boxWidth = longestOptionLength + 8 // 8 para los bordes izquierdo y derecho y el espacio adicional

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
                leitorBebidasTodas()
            }

            2 -> {
                leitorCocktails()
            }

            3 -> {
                leitorShots()
            }

            4 -> {
                leitorSnacks()
            }

            5 -> {
                println("\u001B[33mReturning to MAIN MENU ☺\uFE0F\u001B[0m")
                continuar = false
            }

            else -> {
                println("\u001B[41mINVALID OPTION! Please, try again\u001B[0m")
            }
        }
    }
}


fun viewOrderHistoryMenu() {
    var continuar = true
    val menuTitle = "VIEW ORDER HISTORY"
    val options = listOf(
        "(1) \uD83D\uDD52Order History",
        "(2) \uD83D\uDD0ESee Order in Detail",
        "(3) ⛔\uFE0FDelete Order",
        "\u001B[36m(4) Return to MAIN MENU\u001B[0m"
    )

    fun String.adjustToWidth(width: Int, center: Boolean = false): String {
        val strippedLength = this.replace(Regex("\\u001B\\[[;\\d]*m"), "").length
        return if (center) {
            val padding = (width - strippedLength) / 2
            " ".repeat(padding) + this + " ".repeat(width - strippedLength - padding)
        } else {
            this + " ".repeat(width - strippedLength)
        }
    }

    val longestOptionLength = options.maxByOrNull { it.replace(Regex("\\u001B\\[[;\\d]*m"), "").length }?.length ?: 0
    val boxWidth = longestOptionLength + 8 // 8 para los bordes izquierdo y derecho y el espacio adicional

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
                readOrderHistory()
            }

            2 -> {
                orderInDetail()
            }

            3 -> {
                eliminarOrden()
            }

            4 -> {
                println("\u001B[33mReturning to MAIN MENU ☺\uFE0F\u001B[0m")
                continuar = false
            }

            else -> {
                println("\u001B[41mINVALID OPTION! Please, try again\u001B[0m")
            }
        }
    }
}


