object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val superMap = SuperMap<String, String>()
        superMap.set("h", "Hello")
        superMap.set("w", "World")
        println("${superMap.get("h")} ${superMap.get("w")}")
    }
}
