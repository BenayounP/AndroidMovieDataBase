package android.util


// This class is needed to avoid crash in tests with logs 🤷
object Log {
    @JvmStatic
    fun v(tag: String, msg: String): Int {
        println("VERBOSE: $tag: $msg")
        return 0
    }

    @JvmStatic
    fun d(tag: String, msg: String): Int {
        println("DEBUG: $tag: $msg")
        return 0
    }

    @JvmStatic
    fun e(tag: String, msg: String): Int {
        println("ERROR: $tag: $msg")
        return 0
    }
}