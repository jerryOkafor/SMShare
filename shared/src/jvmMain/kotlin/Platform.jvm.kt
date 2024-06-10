actual class Platform actual constructor() {
    actual val name: String
        get() = "Java ${System.getProperty("java.version")}"
}
