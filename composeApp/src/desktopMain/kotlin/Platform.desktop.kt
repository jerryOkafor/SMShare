actual class Platform actual constructor() {
    actual val platform: String
        get() = "Java ${System.getProperty("java.version")}"
}