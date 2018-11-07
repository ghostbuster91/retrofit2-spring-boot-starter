package io.ghostbuster91.spring.boot.retrofit2

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(AutoRetrofitInterfaceScanner::class)
class RegisterAutoConfiguration
