# https://medium.com/@dfs.techblog/code-obfuscations-in-kotlin-multiplatform-kmp-494d2cd0a416

# Keep Room KMP
-keep class * extends androidx.room.RoomDatabase { <init>(); }
