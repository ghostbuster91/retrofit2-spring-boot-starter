package io.ghostbuster91.spring.boot.retrofit2

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Import(AutoRetrofitInterfaceScanner::class)
annotation class EnableAutoRetrofit