# ============================================================================
# PetJournal - Camada DATABASE (Room)
# ============================================================================

# ---------- Room Library -------------------------------------------------
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# ---------- Entidades e DAOs ---------------------------------------------
# Impede que o R8 renomeie as classes de entidade e seus campos,
# pois os nomes dos campos são usados como nomes de colunas no SQLite.
-keep class com.petjournal.database.** { *; }
-keepclassmembers class com.petjournal.database.** { *; }

# Mantém os nomes das classes que implementam as DAOs (geradas pelo Room)
-keep class * extends androidx.room.RoomDatabase { *; }
-keep class * implements androidx.room.Entity { *; }

# ---------- Suporte a Kotlin Serialization / Gson (se usado em TypeConverters)
-keepattributes Signature, *Annotation*

# ---------- TypeConverters ----------------------------------------------
# Se você usa conversores de tipo personalizados (ex: Date para Long),
# eles precisam ser preservados para o Room encontrá-los.
-keep class * {
    @androidx.room.TypeConverter <methods>;
}

# ---------- Repositories da Camada Database -----------------------------
# Como as implementações de repositórios que usam o banco de dados ficam aqui,
# precisamos garantir que o Koin consiga instanciá-las.
-keep class com.petjournal.database.repository.** { *; }