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

# Preservar atributos necessários para Reflexão (fundamentais para Retrofit/Moshi).
-keepattributes Signature, InnerClasses, EnclosingMethod, AnnotationDefault, *Annotation*
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

# Mantém os data classes do domain e data usados pela serialização.
-keep class com.soujunior.domain.model.** { *; }
-keepclassmembers class com.soujunior.domain.model.** { *; }
-keep class com.soujunior.data.model.** { *; }
-keepclassmembers class com.soujunior.data.model.** { *; }

# ---------- Retrofit 2 ----------------------------------------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Impedir a ofuscação de QUALQUER interface de serviço do Retrofit
-keep @retrofit2.http.* interface * { <methods>; }

# Preservar os tipos genéricos e anotações do Retrofit
# Interfaces de API do Retrofit (usadas por reflexão via Proxy)
-keep interface com.soujunior.data.remote.AuthDataSource { *; }
-keep interface com.soujunior.data.remote.RemoteDataSource { *; }

# Mantém classes que estendem Call/CallAdapter do Retrofit
-keep class com.soujunior.data.remote.adapters.** { *; }
-keepclassmembers class com.soujunior.data.remote.adapters.** { *; }

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

# ---------- Network Infrastructure (sealed classes do Retrofit adapter) ----
-keep class com.soujunior.domain.network.** { *; }
-keepclassmembers class com.soujunior.domain.network.** { *; }

# ---------- BaseUseCase / DataResult (sealed classes) ---------------------
-keep class com.soujunior.domain.use_case.base.** { *; }
-keepclassmembers class com.soujunior.domain.use_case.base.** { *; }

# ---------- Validation (instanciado via Koin) ----------------------------
-keep class com.soujunior.domain.use_case.util.** { *; }

# ---------- Infrastructure (Workers, Schedulers) -------------------------
-keep class com.soujunior.petjournal.infrastructure.** { *; }

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

# ---------- Enums (necessário para Moshi) ----------------------------
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
# Impede R8 de remover construtores usados por Koin e Moshi via reflexão.
-keepclassmembers class * {
    public <init>(...);
}

# Preserva os tipos genéricos (evita o erro de ParameterizedType)
-keepattributes Signature, InnerClasses, EnclosingMethod, AnnotationDefault, *Annotation*

# Mantém as anotações do Retrofit intactas
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Impede a renomeação das interfaces de API (Retrofit usa proxies dinâmicos)
-keep @retrofit2.http.* interface * { <methods>; }

# Preserva as classes do Retrofit para que a reflexão funcione
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# ---------- AndroidX Security / Tink (Google Crypto) ---------------------
# O Tink possui chamadas opcionais para bibliotecas HTTP e Joda-Time.
# Como validado via grep, o PetJournal não utiliza essas bibliotecas, 
# então podemos silenciar os avisos com segurança.

-dontwarn com.google.api.client.http.**
-dontwarn com.google.api.client.http.javanet.**
-dontwarn org.joda.time.**
-dontwarn com.google.crypto.tink.util.KeysDownloader