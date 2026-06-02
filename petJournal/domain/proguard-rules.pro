# ============================================================================
# PetJournal - Camada DOMAIN (Versão Final Validada)
# ============================================================================

# Preservar assinaturas de tipos genéricos (Vital para evitar ClassCastException)
# Isso garante que o Retrofit entenda o que está dentro de DataResult<T>
-keepattributes Signature, InnerClasses, EnclosingMethod, *Annotation*

# ---------- Modelos de Negócio (Domain Models) ---------------------------
# Mantém os pacotes validados pelo seu comando 'find'
-keep class com.soujunior.domain.model.** { *; }
-keepclassmembers class com.soujunior.domain.model.** { *; }

# ---------- Interfaces de Repositório ------------------------------------
# Necessário para que o Koin localize os contratos durante a injeção
-keep interface com.soujunior.domain.repository.** { *; }

# ---------- Casos de Uso (Use Cases) -------------------------------------
# Protege a lógica principal e as classes base validadas no terminal
-keep class com.soujunior.domain.use_case.** { *; }
-keepclassmembers class com.soujunior.domain.use_case.** { *; }

# ---------- Infraestrutura de Base (DataResult & BaseUseCase) ------------
# Regras específicas para o pacote base onde estão seus arquivos de fluxo
-keep class com.soujunior.domain.use_case.base.** { *; }
-keepclassmembers class com.soujunior.domain.use_case.base.** { *; }

# ---------- Kotlin Coroutines / Flow -------------------------------------
# Versão compatível com R8 Full Mode que não gera erro de sintaxe
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# ---------- Metadata (Injeção de Dependência) ----------------------------
# Garante que o Koin consiga ler os construtores das suas classes Kotlin
-keep class kotlin.Metadata { *; }