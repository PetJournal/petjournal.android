# ============================================================================
# Módulo :data - Consumer ProGuard Rules
# ============================================================================
# Estas regras são automaticamente aplicadas ao módulo :app quando consome :data.

# Interfaces Retrofit (usadas por Proxy/reflexão)
-keep interface com.soujunior.data.remote.AuthDataSource { *; }
-keep interface com.soujunior.data.remote.RemoteDataSource { *; }

# Adapters customizados do Retrofit
-keep class com.soujunior.data.remote.adapters.** { *; }

# Implementações de repositório (instanciados via Koin)
-keep class com.soujunior.data.repository.** { *; }

# Mock data provider
-keep class com.soujunior.data.mock.** { *; }

# Utilitários do módulo data
-keep class com.soujunior.data.util.** { *; }
