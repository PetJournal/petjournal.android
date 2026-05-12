# ============================================================================
# Módulo :domain - Consumer ProGuard Rules
# ============================================================================
# Estas regras são automaticamente aplicadas ao módulo :app quando consome :domain.

# Mantém todos os model/DTO classes (usados por Moshi/Gson via reflexão)
-keep class com.soujunior.domain.model.** { *; }
-keepclassmembers class com.soujunior.domain.model.** { *; }

# Mantém interfaces de repositório (resolvidas via Koin)
-keep interface com.soujunior.domain.repository.** { *; }

# Mantém use cases (instanciados via Koin)
-keep class com.soujunior.domain.use_case.** { *; }
