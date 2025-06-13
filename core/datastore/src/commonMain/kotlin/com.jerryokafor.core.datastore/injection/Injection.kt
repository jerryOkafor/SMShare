import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun dataStoreModule() = module {
    single { Json { ignoreUnknownKeys = true } }
}
