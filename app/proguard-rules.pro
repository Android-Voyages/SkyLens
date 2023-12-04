# Rule  main Rules

-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
-keep class * extends androidx.** { *; }
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class **.R$* {
    <fields>;
}

-dontwarn com.sun.jna.**
-dontwarn java.awt.**
-dontwarn java.beans.**
-dontwarn java.lang.management.**
-dontwarn java.rmi.**
-dontwarn javax.annotation.**
-dontwarn javax.mail.**
-dontwarn javax.management.**
-dontwarn javax.naming.**
-dontwarn javax.script.**
-dontwarn javax.servlet.**
-dontwarn javax.swing.**
-dontwarn javax.tools.**
-dontwarn javax.xml.stream.**
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**
-dontwarn com.sun.source.**
-dontwarn com.sun.tools.javac.**
-dontwarn sun.reflect.ReflectionFactory
-dontwarn org.jetbrains.annotations.**
-dontwarn org.codehaus.janino.**
-dontwarn javax.lang.model.AnnotatedConstruct
-dontwarn javax.lang.model.element.AnnotationMirror
-dontwarn javax.lang.model.element.AnnotationValue
-dontwarn javax.lang.model.element.Element
-dontwarn javax.lang.model.element.ElementKind
-dontwarn javax.lang.model.element.ExecutableElement
-dontwarn javax.lang.model.element.Modifier
-dontwarn javax.lang.model.element.Name
-dontwarn javax.lang.model.element.PackageElement
-dontwarn javax.lang.model.element.TypeElement
-dontwarn javax.lang.model.element.TypeParameterElement
-dontwarn javax.lang.model.element.VariableElement
-dontwarn javax.lang.model.type.ArrayType
-dontwarn javax.lang.model.type.DeclaredType
-dontwarn javax.lang.model.type.NoType
-dontwarn javax.lang.model.type.TypeKind
-dontwarn javax.lang.model.type.TypeMirror
-dontwarn javax.lang.model.type.TypeVariable
-dontwarn javax.lang.model.type.WildcardType
-dontwarn org.jetbrains.kotlin.com.google.common.annotations.VisibleForTesting
-dontwarn org.jetbrains.kotlin.com.google.errorprone.annotations.CanIgnoreReturnValue
-dontwarn org.jetbrains.kotlin.com.google.errorprone.annotations.CompatibleWith
-dontwarn org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock
-dontwarn org.jetbrains.kotlin.com.google.errorprone.annotations.ForOverride
-dontwarn org.jetbrains.kotlin.com.google.errorprone.annotations.Immutable
-dontwarn org.jetbrains.kotlin.com.google.errorprone.annotations.concurrent.GuardedBy
-dontwarn org.jetbrains.kotlin.com.google.errorprone.annotations.concurrent.LazyInit
-dontwarn org.jetbrains.kotlin.com.google.j2objc.annotations.RetainedWith
-dontwarn org.jetbrains.kotlin.com.google.j2objc.annotations.Weak
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE

# Rule for Dagger 2

-keepattributes SourceFile,LineNumberTable
-keep class * {
    @com.google.dagger.Provides <methods>;
}

-keep class * {
    @javax.inject.Inject <init>(...);
}

-keepclasseswithmembers class * {
    @javax.inject.Inject <fields>;
}

-keepclasseswithmembers class * {
    @javax.inject.Inject <methods>;
}

# Rule for RxKotlin

-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }
-keep class io.reactivex.schedulers.Schedulers { public static <methods>; }
-keep class io.reactivex.schedulers.Scheduler { public <methods>; }
-keep class io.reactivex.Observable { public *; }
