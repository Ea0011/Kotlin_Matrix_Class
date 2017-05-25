package matrix

open class Matrix (protected var height : Int, protected var width : Int, protected var matrix: Array<DoubleArray> = Array(height, { DoubleArray(width) })) : valuable {
    override var det : Double? = if (width == height) 0.0 else null
        get() {
            det()
            return field
        }
        protected set(value) {
            field = value
        }
    override fun det() : Double? {
        if (width != height) {
            return null
        }
        val result : Double? = compute(this)
        det = result
        return result
    }
    protected fun copy(that : Matrix) : Matrix {
        this.matrix = that.matrix
        this.height = that.height
        this.width = that.width
        this.det = that.det
        return that
    }
    private fun compute(C : Matrix) : Double {
        if (C.height == 2 && C.width == 2) {
            return (C.matrix[0][0] * C.matrix[1][1]) - (C.matrix[1][0] * C.matrix[0][1])
        } else {
            var det : Double = 0.0
            for (col in 0..C.width - 1) {
                det += C.matrix[0][col] * compute(C.cofactor(0, col)) * Math.pow(-1.0, col.toDouble())
            }
            return det
        }
    }
    fun format() : Unit {
        for (i in 0..height - 1) {
            for (j in 0..width - 1) {
                print(matrix[i][j])
                print(" ")
            }
            println()
        }
    }
    fun transpose(change : Boolean = false) : Matrix {
        val result : Matrix = Matrix(width, height)
        for (i in 0..width - 1)
            for (j in 0..height - 1) {
                result.setElement(i, j, matrix[j][i])
            }
        if (change) return copy(result) else return result
    }
    fun setElement(row : Int, col : Int, element : Double) {
        if (row > height || col > width) {
            throw error("Out of range")
        }
        matrix[row][col] = element
    }
    fun getElement(row : Int, col : Int) : Double {
        return matrix[row][col]
    }
    fun setRow(newRow : DoubleArray, index : Int) : Unit {
        if (newRow.size != width) {
            throw error("rows of this matrix should have ${width} elements")
        }
        if (index > height) {
            throw error("this matrix has only ${height} rows")
        }
        matrix[index] = newRow
    }
    fun setCol(newCol : DoubleArray, index : Int) : Unit {
        if (newCol.size != height) {
            throw error("columns of this matrix should have ${height} elements")
        }
        if (index > width) {
            throw error("this matrix has only ${width} columns")
        }
        matrix = transpose().matrix
        setRow(newCol, index)
        matrix = transpose().matrix
    }
    fun deleteRow(row : Int, change : Boolean = false) : Matrix {
        if (row > height) {
            throw error("Out of range")
        }
        val result : Matrix = Matrix(height - 1, width)
        for (i in 0..height - 1) {
            if (i < row) {
                result.setRow(matrix[i], i)
            } else if (i > row) {
                result.setRow(matrix[i], i - 1)
            }
        }
        if (change) return copy(result) else return result
    }
    fun deleteCol(col : Int, change : Boolean = false) : Matrix {
        if (col > width) {
            throw error("Out of range")
        }
        matrix = transpose().matrix
        width += height
        height = width - height
        width -= height
        val result : Matrix = deleteRow(col).transpose(true)
        matrix = transpose().matrix
        width += height
        height = width - height
        width -= height
        if (change) return copy(result) else return result
    }
    fun fill(num : Double = 0.0) {
        for (i in 0..height - 1) {
            matrix[i].fill(num)
        }
    }
    fun getRow() : Int {
        return height
    }
    fun getCol() : Int {
        return width
    }
    fun initialize() : Unit {
        for (i in 0..height - 1)
            for(j in 0..width - 1) {
                println("enter the value of row ${i} column ${j}")
                val input : Double = readLine()!!.toDouble()
                matrix[i][j] = input
            }
    }
    override fun cofactor(row: Int, col: Int): Matrix {
        var result : Matrix = this.deleteRow(row)
        result = result.deleteCol(col)
        return result
    }

    fun multiply(that: Matrix, change: Boolean = false): Matrix {
        val result : Matrix = Matrix(height, that.width)
        for (r1 in 0..height - 1)
            for (c2 in 0..that.width - 1)
                for (c1 in 0..width - 1) {
                    result.setElement(r1, c2, result.getElement(r1, c2) + (this.getElement(r1, c1) * that.getElement(c1, c2)))
                }
        if (change) return copy(result) else return result
    }
}
