package dev.yidafu.loki.core.appender.naming

/**
 * only one strategy in Factory.
 * + [DateFileNamingStrategy]
 */
object NamingStrategyFactory {
    private val strategyMap = mutableMapOf<String, FileNamingStrategy>()

    init {
        addStrategy(DateFileNamingStrategy())
    }

    fun getStrategy(strategy: String): FileNamingStrategy {
        return strategyMap[strategy] ?: DateFileNamingStrategy()
    }

    fun addStrategy(strategy: FileNamingStrategy) {
        strategyMap[strategy.name] = strategy
    }
}
