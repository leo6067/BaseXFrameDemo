
#====================== kotlin 混淆规则======================
-dontwarn kotlin.**
-keep class org.jetbrains.** { *; }
-keep interface org.jetbrains.** { *; }
-dontwarn org.jetbrains.**
-dontwarn com.ted.floo.**
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }
-keepattributes *JavascriptInterface*
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

#======================glide=======================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

#====================== app 混淆规则======================
-keep class com.xy.xframework.**
-keep class androidx.databinding.** {*;}

#====================== livedatabus 混淆规则======================
-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }

 -keep class com.gyf.immersionbar.* {*;}
 -dontwarn com.gyf.immersionbar.**

-keep class com.xy.xframework.web.** { *; }

 -obfuscationdictionary filename.txt
 -classobfuscationdictionary filename.txt
 -packageobfuscationdictionary filename.txt




 -keep class com.simple.spiderman.** { *; }
 -keepnames class com.simple.spiderman.** { *; }
 -keep public class * extends android.app.Activity
 -keep class * implements Android.os.Parcelable {
     public static final Android.os.Parcelable$Creator *;
 }
 # support
 -keep public class * extends android.support.annotation.** { *; }
 -keep public class * extends android.support.v4.content.FileProvider
 # androidx
 -keep public class * extends androidx.annotation.** { *; }
 -keep public class * extends androidx.core.content.FileProvider