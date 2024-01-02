# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.strayalphaca.travel_diary.map.model.* {*;}
-keep class com.strayalphaca.travel_diary.data.login.model.* {*;}
-keep class com.strayalphaca.travel_diary.data.calendar.models.* {*;}
-keep class com.strayalphaca.travel_diary.data.diary.model.* {*;}
-keep class com.strayalphaca.travel_diary.data.file.model.* {*;}
-keep class com.strayalphaca.travel_diary.data.map.model.* {*;}
-keep class com.strayalphaca.travel_diary.core.data.model.* {*;}