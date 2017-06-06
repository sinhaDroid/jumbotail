# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/deepanshu/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

#Start Dto#
-keep public class only.sinha.android.mausam.app.module.model.** {
  *;
}
#End Dto#

#Start Jackson#
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keepnames class com.fasterxml.jackson.** { *; }
-keepattributes *Annotation*,EnclosingMethod,Signature
-dontwarn java.beans.Transient
-dontwarn java.beans.ConstructorProperties
#End Jackson#

#Start Okhttp#
-dontwarn java.nio.file.**
-dontwarn java.lang.invoke.**
-dontwarn java.lang.reflect.Method
#End Okhttp#

#Start Retrofit#
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
#End Retrofit#
