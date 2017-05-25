package matrix

interface valuable {
    val det : Double?
    fun det() : Double?
    fun cofactor(row : Int, col : Int) : Matrix
}