import java.io.File


fun leitorCocktails() {
    try {
        val filename = "Cocktails.csv" // Nombre del archivo CSV
        val cocktailsList = mutableListOf<Cocktails>() // Lista para almacenar objetos Cocktails
        var index = 0 // Contador de índice

        // Leer el archivo CSV y crear objetos Cocktails
        File(filename).forEachLine { line ->
            if (index != 0) { // Saltar la primera línea que contiene los encabezados
                val columns = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()) // Dividir la línea en columnas considerando las comas dentro de las comillas
                if (columns.size >= 4) {
                    val id = columns[0].toInt()
                    val name = columns[1]
                    val bebidasJson = columns[3].trim('"').replace("[", "").replace("]", "").split(",").map { it.trim() }.toMutableList()
                    val preco = columns[2].toDouble()
                    cocktailsList.add(Cocktails(false, bebidasJson, name, preco, id))
                }
            }
            index++
        }

        // Imprimir los detalles formateados
        val titulo = "COCKTAIL MENU"
        val longitudTotal = 67
        val tituloCentrado = titulo.padStart((longitudTotal - titulo.length) / 2 + titulo.length).padEnd(longitudTotal)
        println()
        println("\u001B[30m\u001B[46m${tituloCentrado}\u001B[0m")
        println()
        cocktailsList.forEach { cocktail ->
            val cocktailNomeJustificado = String.format("%-30s", cocktail.nome.toUpperCase())
            val precoCocktailJustificado = String.format("%.2f", cocktail.preco)
            println("    $cocktailNomeJustificado............................$precoCocktailJustificado$")
            println("\u001B[36m    Ingredients: ${cocktail.bebidas.joinToString(", ")}\u001B[0m")
            println()
        }
    } catch (e: Exception) {
        println("An error occurred while processing the file: ${e.message}")
    }
}