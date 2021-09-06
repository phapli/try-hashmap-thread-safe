import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


internal class SuperMapTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun threadSafeSet() {
        val superMap = SuperMap<String, Int>()
        val threadCounts = 10
        val actionPerThread = 3000
        largeInsert(superMap, threadCounts, actionPerThread)
        for (i in 1..threadCounts * actionPerThread) {
            assert(superMap.get("id$i") == 1)
        }
    }

    @Test
    fun threadSafeRemove() {
        val superMap = SuperMap<String, Int>()
        val threadCounts = 10
        val actionPerThread = 3000
        for (i in 1..threadCounts * actionPerThread) {
            superMap.set("id$i", 1)
        }
        largeRemove(superMap, threadCounts, actionPerThread)
        for (i in 1..threadCounts * actionPerThread) {
            assert(superMap.get("id$i") == null)
        }
    }

    private fun largeInsert(map: SuperMap<String, Int>, threadCounts: Int, actionPerThread: Int) {
        val executorService = Executors.newFixedThreadPool(threadCounts)
        for (i in 0 until threadCounts) {
            executorService.execute {
                for (k in 1..actionPerThread) {
                    map.set("id" + (i * actionPerThread + k), 1)
                }
            }
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    private fun largeRemove(map: SuperMap<String, Int>, threadCounts: Int, actionPerThread: Int) {
        val executorService = Executors.newFixedThreadPool(threadCounts)
        for (i in 0 until threadCounts) {
            executorService.execute {
                for (k in 1..actionPerThread) {
                    map.remove("id" + (i * actionPerThread + k))
                }
            }
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

}
