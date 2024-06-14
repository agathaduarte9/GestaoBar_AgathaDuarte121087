import java.io.File

fun leitorShots() {
    try {
        val filename = "Shots.csv" // Nombre del archivo CSV
        val shotsList = mutableListOf<Shots>() // Lista para almacenar objetos Shots
        var index = 0 // Contador de índice

        // Leer el archivo CSV y crear objetos Shots
        File(filename).forEachLine { line ->
            if (index != 0) { // Saltar la primera línea que contiene los encabezados
                val columns = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()) // Dividir la línea en columnas considerando las comas dentro de las comillas
                if (columns.size >= 4) {
                    val id = columns[0].toInt()
                    val name = columns[1]
                    val bebidasJson = columns[3].trim('"').replace("[", "").replace("]", "").split(",").map { it.trim() }.toMutableList()
                    val preco = columns[2].toDouble()
                    shotsList.add(Shots(false, bebidasJson, name, preco, id))
                }
            }
            index++
        }

        // Imprimir los detalles formateados
        val titulo = "SHOT MENU"
        val longitudTotal = 60
        val tituloCentrado = titulo.padStart((longitudTotal - titulo.length) / 2 + titulo.length).padEnd(longitudTotal)
        println()
        println("\u001B[30m\u001B[46m${tituloCentrado}\u001B[0m")
        println()
        shotsList.forEach { shot ->
            val shotNomeJustificado = String.format("%-30s", shot.nome.toUpperCase())
            val precoShotJustificado = String.format("%.2f", shot.preco)
            println("    $shotNomeJustificado.....................$precoShotJustificado$")
            println("\u001B[36m    Ingredients: ${shot.bebidas.joinToString(", ")}\u001B[0m")
            println()
        }
    } catch (e: Exception) {
        println("An error occurred while processing the file: ${e.message}")
    }
}