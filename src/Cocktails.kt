class Cocktails(
    var personalizado: Boolean = false,
    var bebidas: MutableList<String> = mutableListOf(),
    nome: String,
    preco: Double,
    id: Int,

    ) : Produto (nome, preco, id){


}


