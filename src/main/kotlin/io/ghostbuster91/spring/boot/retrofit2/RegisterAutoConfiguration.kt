package io.ghostbuster91.spring.boot.retrofit2

import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import retrofit2.Retrofit

@Configuration
@Import(AutoRetrofitInterfaceScanner::class)
class RegisterAutoConfiguration{

    @Bean
    fun retrofitAnnotationProcessor(beanFactory: AutowireCapableBeanFactory, retrofit: Retrofit) =
            RetrofitAnnotationProcessor(beanFactory, retrofit)
}
