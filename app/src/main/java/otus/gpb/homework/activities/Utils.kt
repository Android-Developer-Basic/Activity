package otus.gpb.homework.activities

@OptIn(ExperimentalStdlibApi::class)
fun Any.logInfo() = "${this::class.simpleName} ${hashCode().toHexString()}"
