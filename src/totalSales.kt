import java.io.File

fun imprimirTotalVentas() {
    try {
        val filename = "orderHistory.csv"
        val orderHistoryList = mutableListOf<OrderHistory>()
        var index = 0
        var totalSales = 0.0

        File(filename).forEachLine { line ->
            if (index != 0) { // Saltar la primera línea que contiene los encabezados
                val columns = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                if (columns.size >= 4) {
                    val total = columns[3].toDouble()
                    totalSales += total
                }
            }
            index++
        }

        // Construir el texto con el total de ventas centrado dentro del cuadro
        val title = "TOTAL SALES: ${totalSales}$"

        // Determinar la longitud máxima necesaria para el cuadro
        val maxLength = 30

        // Construir el cuadro con bordes
        val borderLine = "═".repeat(maxLength)
        val emptyLine = " ".repeat(maxLength)

        println("╔$borderLine╗")
        println("║$emptyLine║")
        println("║ ${title.center(maxLength - 2, ' ')} ║")
        println("║$emptyLine║")
        println("╚$borderLine╝")

    } catch (e: Exception) {
        println("An error occurred while processing the file: ${e.message}")
    }
}

// Función para centrar un string dentro de una longitud determinada con un caracter de relleno especificado
fun String.center(length: Int, fillChar: Char): String {
    if (this.length >= length) {
        return this
    } else {
        val leftPad = (length - this.length) / 2
        val rightPad = length - this.length - leftPad
        return "${fillChar}".repeat(leftPad) + this + "${fillChar}".repeat(rightPad)
    }
}

