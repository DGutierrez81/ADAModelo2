package Modelo

data class Producto(val id: Int, val nombre: String, var precio: Float) {

    /**
     * Contiene la información del producto.
     * @return Devuelve un String con el contenido.
     */
    override fun toString(): String {
        return "El producto con id: $id es $nombre con precio $precio€"
    }
}