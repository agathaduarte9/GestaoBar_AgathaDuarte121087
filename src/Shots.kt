class Shots(
    var personalizado: Boolean = false,
    var bebidas: MutableList<String> = mutableListOf(),
    nome: String = "",
    preco: Double = 0.0,
    id: Int = 0
) : Produto(nome, preco, id)