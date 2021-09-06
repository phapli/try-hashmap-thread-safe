class SuperMap<K, V> {

    private val map: MutableMap<K, V> = mutableMapOf()
    fun get(key: K): V? {
        return map.get(key)
    }

    fun set(key: K, value: V) {
        synchronized(map) {
            map.set(key, value)
        }
    }

    fun remove(key: K): V? {
        synchronized(map) {
            return map.remove(key)
        }
    }

}
