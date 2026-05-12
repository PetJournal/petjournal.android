# Preservar a assinatura de tipos genéricos (essencial para o Retrofit no módulo data)
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod

# Impedir a ofuscação das interfaces de API que o Retrofit usa para criar os Proxies
-keep @retrofit2.http.* interface * { <methods>; }

# Manter as classes de modelo (DTOs) que recebem o JSON
# Garante que o nome dos campos não mude, senão o mapeamento do JSON falha
-keep class com.soujunior.data.model.** { *; }
-keepclassmembers class com.soujunior.data.model.** { *; }

# Se você usa Moshi neste módulo, mantenha as anotações
-keepclassmembers class * {
    @com.squareup.moshi.Json <fields>;
}

# Manter as classes de resposta de rede e adaptadores
-keep class com.soujunior.data.remote.** { *; }