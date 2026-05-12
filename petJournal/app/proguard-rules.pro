# ============================================================================
# PetJournal - ProGuard / R8 Rules
# ============================================================================
# Estas regras impedem o R8 de remover ou renomear classes usadas por
# reflection (Retrofit, Moshi, Room, Koin, Gson, WorkManager, etc.).
# ============================================================================

# ---------- Debugging ---------------------------------------------------
# Preserva informações de linha para stack traces legíveis no Crashlytics / logcat.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserva assinaturas genéricas (necessário para Retrofit/Moshi/Gson).
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes Exceptions

# ---------- Kotlin -------------------------------------------------------
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
# kotlin.reflect é necessário para Moshi KotlinJsonAdapterFactory
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.reflect.jvm.internal.**

# ---------- Moshi (Reflection-based com KotlinJsonAdapterFactory) ---------
-keep class com.squareup.moshi.** { *; }
-keepclassmembers class com.squareup.moshi.** { *; }
-keep class com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory { *; }
-dontwarn com.squareup.moshi.**

# Mantém TODOS os data classes do domain usados pela serialização Moshi/Gson.
-keep class com.soujunior.domain.model.** { *; }
-keepclassmembers class com.soujunior.domain.model.** { *; }

# ---------- Gson ----------------------------------------------------------
-keep class com.google.gson.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
# Garante que campos com @SerializedName não sejam removidos/renomeados.
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ---------- Retrofit 2 ----------------------------------------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Interfaces de API do Retrofit (usadas por reflexão via Proxy)
-keep,allowobfuscation interface com.soujunior.data.remote.AuthDataSource { *; }
-keep,allowobfuscation interface com.soujunior.data.remote.RemoteDataSource { *; }

# Mantém classes que estendem Call/CallAdapter do Retrofit
-keep class com.soujunior.data.remote.adapters.** { *; }

# ---------- OkHttp --------------------------------------------------------
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep class okio.** { *; }

# ---------- Room ----------------------------------------------------------
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**
# Entidades e DAOs do Room
-keep class com.petjournal.database.** { *; }
-keepclassmembers class com.petjournal.database.** { *; }

# ---------- Koin (Injeção de Dependência) ---------------------------------
-keep class org.koin.** { *; }
-dontwarn org.koin.**
# Mantém os módulos DI
-keep class com.soujunior.petjournal.di.** { *; }

# ---------- WorkManager ---------------------------------------------------
-keep class androidx.work.** { *; }
-dontwarn androidx.work.**
# Workers do projeto
-keep class com.soujunior.petjournal.infrastructure.worker.** { *; }

# ---------- Coil (Image Loading) ------------------------------------------
-dontwarn coil.**

# ---------- Glide ---------------------------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule { <init>(...); }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-dontwarn com.bumptech.glide.**

# ---------- Accompanist / AndroidX Compose --------------------------------
-dontwarn com.google.accompanist.**
-dontwarn androidx.compose.**

# ---------- AndroidX DataStore --------------------------------------------
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# ---------- Repositories do projeto ---------------------------------------
# Implementações de repositórios no módulo :data (instanciados via Koin)
-keep class com.soujunior.data.repository.** { *; }

# Implementações de repositórios no módulo :database (instanciados via Koin)
-keep class com.petjournal.database.repository.** { *; }

# Interfaces de repositórios no módulo :domain
-keep interface com.soujunior.domain.repository.** { *; }

# ---------- Use Cases do projeto ------------------------------------------
-keep class com.soujunior.domain.use_case.** { *; }

# ---------- Mock Data Provider (se acessado via reflexão) -----------------
-keep class com.soujunior.data.mock.** { *; }

# ---------- ViewModels (instanciados via Koin viewModel<>) ----------------
-keep class * extends androidx.lifecycle.ViewModel { *; }

# ---------- Application class ---------------------------------------------
-keep class com.soujunior.petjournal.** extends android.app.Application { *; }

# ---------- AndroidX Security Crypto -------------------------------------
-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**

# ---------- Enums (necessário para Gson/Moshi) ----------------------------
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ---------- Parcelable ---------------------------------------------------
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# ---------- Serializable -------------------------------------------------
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ---------- R8 Full Mode ajustes -----------------------------------------
# Impede R8 de remover campos/métodos não referenciados diretamente mas
# usados por frameworks via reflexão.
-keepclassmembers,allowshrinking class * {
    public <init>(...);
}
