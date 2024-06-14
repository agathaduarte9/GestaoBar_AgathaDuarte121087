import java.io.File

fun leitorBebidasTodas() {
    try {
        val arquivoCSV = "Bebidas.csv" // Ruta al archivo CSV de bebidas

        // Lee el contenido del archivo CSV
        val textoBebidas: String = File(arquivoCSV).readText()

        // Divide la cadena de texto en líneas
        val lineasArchivo: List<String> = textoBebidas.split("\n")

        // Mapa para almacenar bebidas clasificadas por tipo (alcohólicas o no alcohólicas)
        val bebidasPorTipo: MutableMap<String, MutableMap<String, MutableList<Bebidas>>> = mutableMapOf()

        // Itera sobre cada línea del archivo CSV (ignorando la primera línea que contiene los encabezados)
        for (linea in lineasArchivo.subList(1, lineasArchivo.size)) {

            // Divide la línea en columnas usando la coma como separador
            val columnas = linea.split(",")


            // Extrae los valores de cada columna
            val id = columnas[0].toInt()
            val nombre = columnas[1]
            val precio = columnas[2].toDouble() // Convierte el precio a Double
            val categoria = columnas[3]
            val tipoBebida = columnas[4]




            // Crea un objeto de bebida y agrégalo al mapa según su tipo (alcohólicas o no alcohólicas)
            val bebida = Bebidas(nombre, precio, id)
            bebidasPorTipo.getOrPut(tipoBebida) { mutableMapOf() }.getOrPut(categoria) { mutableListOf() }.add(bebida)
        }

        // Imprime las bebidas clasificadas por tipo
        bebidasPorTipo.forEach { (tipo, categorias) ->
            println()
            val tipoCentradoLength = tipo.length + (51 - tipo.length) / 2
            val tipoCentrado = tipo.toUpperCase().padStart(tipoCentradoLength, ' ').padEnd(51, ' ')
            println("\u001B[30m\u001B[46m${tipoCentrado}\u001B[0m")
            println()
            categorias.forEach { (categoria, bebidas) ->
                println("\u001B[36m  ${categoria.toUpperCase()}:\u001B[0m")
                bebidas.forEach { bebida ->
                    val nomeBebidaJustificado = String.format("%-30s", bebida.nome)
                    val precoBebidaJustificado = String.format("%.2f", bebida.preco)
                    println("    $nomeBebidaJustificado............$precoBebidaJustificado$")
                }
            }
        }
    } catch (e: Exception) {
        println("An error occurred while processing the file: ${e.message}")
    }
}

