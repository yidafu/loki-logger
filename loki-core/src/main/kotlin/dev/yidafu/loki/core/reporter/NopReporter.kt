package dev.yidafu.loki.core.reporter

class NopReporter(reportInterval: Long) : IntervalReporter(reportInterval) {
    override fun report(logList: List<String>) {
    }

    override fun report() {
        report(listOf())
    }
}
