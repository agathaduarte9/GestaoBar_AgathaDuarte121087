import java.io.File


fun leitorSnacks() {
    try {
        val filename = "Snacks.csv" // Nombre del archivo CSV
        val snacksList = mutableListOf<Snacks>() // Lista para almacenar objetos Snacks
        var index = 0 // Contador de índice

        // Leer el archivo CSV y crear objetos Snacks
        File(filename).forEachLine { line ->
            if (index != 0) { // Saltar la primera línea que contiene los encabezados
                val columns = line.split(",") // Dividir la línea en columnas
                if (columns.size >= 4) {
                    val id = columns[0].toInt()
                    val name = columns[1]
                    val price = columns[2].toDouble()
                    val description = columns[3]
                    snacksList.add(Snacks(name, price, id, description))
                }
            }
            index++
        }

        // Imprimir los detalles formateados
        val titulo = "APPETIZER MENU"
        val longitudTotal = 60
        val tituloCentrado = titulo.padStart((longitudTotal - titulo.length) / 2 + titulo.length).padEnd(longitudTotal)
        println()
        println("\u001B[30m\u001B[46m${tituloCentrado}\u001B[0m")
        println()
        snacksList.forEach { snack ->
            val snackNomeJustificado = String.format("%-30s", snack.nome.toUpperCase())
            val precoSnackJustificado = String.format("%.2f", snack.preco)
            println("    $snackNomeJustificado.....................$precoSnackJustificado$")
            println("\u001B[36m    (${snack.description})\u001B[0m")
            println()
        }
    } catch (e: Exception) {
        println("An error occurred while processing the file: ${e.message}")
    }
}



