package com.jerryokafor.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import com.jerryokafor.core.database.dao.AccountsDao
import com.jerryokafor.core.database.dao.TagsDao
import com.jerryokafor.core.database.entity.AccountEntity
import com.jerryokafor.core.database.entity.TagEntity
import com.jerryokafor.core.database.entity.TagGroupEntity
import com.jerryokafor.core.database.entity.UserProfileEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

expect val ApplicationDispatcher: CoroutineDispatcher

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(
    entities = [AccountEntity::class, UserProfileEntity::class, TagEntity::class, TagGroupEntity::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountsDao
    abstract fun tagsDao(): TagsDao
}

object INIT_DB : Migration(0, 1) {
    override fun migrate(connection: SQLiteConnection) {
    }
}

val MIGRATIONS = arrayOf(INIT_DB)

fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase = builder
    .addMigrations(*MIGRATIONS)
    .fallbackToDestructiveMigrationOnDowngrade(true)
    .addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(connection: SQLiteConnection) {
            super.onCreate(connection)
            ioThread {
                println("Creating database connection")
            }
        }
    })
    .build()


fun ioThread(block: suspend () -> Unit) {
    CoroutineScope(ApplicationDispatcher).launch {
        block()
    }
}

val tagGroups = listOf(
    "Fitness" to listOf(
        "fitness",
        "gym",
        "workout",
        "fitlife",
        "gains",
        "healthy",
        "crossfit",
        "bodygoals",
        "fitspo",
        "strong"
    ),
    "Travel" to listOf(
        "travel",
        "wanderlust",
        "adventure",
        "vacation",
        "explore",
        "globetrotter",
        "roadtrip",
        "beachlife",
        "nature",
        "travelgram"
    ),
    "Food" to listOf(
        "food",
        "foodie",
        "foodporn",
        "delicious",
        "yummy",
        "instafood",
        "homemade",
        "foodgasm",
        "healthyfood",
        "foodblogger"
    ),
    "Fashion" to listOf(
        "fashion",
        "style",
        "ootd",
        "outfit",
        "trends",
        "streetstyle",
        "fashionblogger",
        "menswear",
        "womensfashion",
        "lookbook"
    ),
    "Photography" to listOf(
        "photography",
        "photooftheday",
        "instapic",
        "snapshot",
        "picofday",
        "DSLR",
        "lensculture",
        "portrait",
        "streetphotography",
        "visualsoflife"
    ),
    "Motivation" to listOf(
        "motivation",
        "goals",
        "mindset",
        "success",
        "positivevibes",
        "inspire",
        "hustle",
        "ambition",
        "growthmindset",
        "stayfocused"
    ),
    "Pets" to listOf(
        "pets",
        "dogsofinstagram",
        "catsofinstagram",
        "animals",
        "petstagram",
        "petlover",
        "doglover",
        "puppy",
        "kitten",
        "cutepets"
    ),
    "Beauty" to listOf(
        "beauty",
        "makeup",
        "skincare",
        "glam",
        "beautyroutine",
        "cosmetics",
        "beautyblogger",
        "selfcare",
        "glowup",
        "makeupartist"
    ),
    "Music" to listOf(
        "music",
        "musician",
        "newmusic",
        "beats",
        "singer",
        "instamusic",
        "concert",
        "producer",
        "soundcloud",
        "djlife"
    ),
    "Tech" to listOf(
        "tech",
        "gadgets",
        "innovation",
        "coding",
        "programmer",
        "AI",
        "startup",
        "devlife",
        "software",
        "technews"
    ),
    "Gaming" to listOf(
        "gaming",
        "gamer",
        "gameplay",
        "stream",
        "twitch",
        "esports",
        "gamelife",
        "controller",
        "gameon",
        "fpsgames"
    ),
    "Parenting" to listOf(
        "parenting",
        "momlife",
        "dadlife",
        "parenthood",
        "familyfirst",
        "baby",
        "toddlers",
        "kids",
        "motherhood",
        "fatherhood"
    ),
    "Education" to listOf(
        "education",
        "studygram",
        "learning",
        "studentlife",
        "onlinelearning",
        "edtech",
        "knowledge",
        "studymotivation",
        "elearning",
        "schoolgoals"
    ),
    "Entrepreneurship" to listOf(
        "entrepreneur",
        "startup",
        "business",
        "bosslife",
        "solopreneur",
        "passiveincome",
        "sidehustle",
        "founder",
        "leadership",
        "hustlehard"
    ),
    "Art & Design" to listOf(
        "art",
        "design",
        "artist",
        "drawing",
        "illustration",
        "digitalart",
        "sketch",
        "creatives",
        "artwork",
        "graphicdesign"
    )
)
