#====================== kotlin 混淆规则======================
-dontwarn kotlin.**
-keep class org.jetbrains.** { *; }
-keep interface org.jetbrains.** { *; }
-dontwarn org.jetbrains.**
-dontwarn com.ted.floo.**
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keepclasseswithmembers @kotlin.Metadata class * { *; }
-keepclassmembers class **.WhenMappings {
    <fields>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

-keep class *$Companion{
    *;
}
-keep @kotlin.Metadata class *
-keepclasseswithmembers @kotlin.Metadata class * { *; }#主要是这一行代码其作用

#====================== app 混淆规则======================

-keep class com.xy.network.** { *; }
-keep class androidx.databinding.** {*;}

#====================== Gson 混淆规则======================
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson 下面替换成自己的实体类
-keep class com.easy.framework.bean.** { *; }
-keep class com.easy.app.ui.*.*$$Inject* { *; }