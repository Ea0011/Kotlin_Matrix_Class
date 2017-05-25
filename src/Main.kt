import matrix.*

fun main(args : Array<String>) {
    val a : Matrix = Matrix(2, 2)
    a.initialize()
    a.deleteRow(0, true)
    a.format()
}