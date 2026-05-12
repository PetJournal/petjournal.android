# ============================================================================
# Módulo :database - Consumer ProGuard Rules
# ============================================================================
# Estas regras são automaticamente aplicadas ao módulo :app quando consome :database.

# Room: Entidades, DAOs, Database e Converters
-keep class com.petjournal.database.** { *; }
-keepclassmembers class com.petjournal.database.** { *; }
