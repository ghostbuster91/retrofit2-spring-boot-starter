package io.ghostbuster91.spring.boot.retrofit2

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.env.Environment
import org.springframework.core.type.AnnotationMetadata
import org.springframework.core.type.StandardAnnotationMetadata
import org.springframework.core.type.filter.AnnotationTypeFilter

class AutoRetrofitInterfaceScanner : ImportBeanDefinitionRegistrar, EnvironmentAware {
    private var environment: Environment? = null

    override fun setEnvironment(environment: Environment) {
        this.environment = environment
    }

    override fun registerBeanDefinitions(metadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        val annotationAttributes = metadata.getAnnotationAttributes(EnableAutoRetrofit::class.java.canonicalName)
        if (annotationAttributes != null) {
            val basePackages = getBasePackageFromAnnotatedClass(metadata)
            val provider = createComponentProvider()
            provider.addIncludeFilter(AnnotationTypeFilter(AutoRetrofit::class.java))
            registerBeans(basePackages, provider, registry)
        }
    }

    private fun createComponentProvider(): ClassPathScanningCandidateComponentProvider {
        return object : ClassPathScanningCandidateComponentProvider(false, environment!!) {
            override fun isCandidateComponent(beanDefinition: AnnotatedBeanDefinition): Boolean {
                val beanDefMetadata = beanDefinition.metadata
                return beanDefMetadata.isInterface
            }
        }
    }

    private fun registerBeans(basePackages: Array<String>, provider: ClassPathScanningCandidateComponentProvider, registry: BeanDefinitionRegistry) {
        for (basePackage in basePackages) {
            for (beanDefinition in provider.findCandidateComponents(basePackage)) {
                registry.registerBeanDefinition(beanDefinition.beanClassName!!, beanDefinition)
            }
        }
    }

    private fun getBasePackageFromAnnotatedClass(metadata: AnnotationMetadata) =
            arrayOf((metadata as StandardAnnotationMetadata).introspectedClass.getPackage().name)
}