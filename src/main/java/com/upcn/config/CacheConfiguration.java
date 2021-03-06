package com.upcn.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.upcn.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.upcn.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.upcn.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Inspeccion.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Inspeccion.class.getName() + ".anomaliaMedidors", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Inspeccion.class.getName() + ".inmuebles", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Inmueble.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Inmueble.class.getName() + ".inspeccions", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Inspeccion.class.getName() + ".trabajos", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Anomalia.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Anomalia.class.getName() + ".inspeccions", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Estado.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Estado.class.getName() + ".inspeccions", jcacheConfiguration);
            cm.createCache(com.upcn.domain.TipoInmueble.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.TipoInmueble.class.getName() + ".inspeccions", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Trabajo.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Trabajo.class.getName() + ".materials", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Trabajo.class.getName() + ".inspeccions", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Etapa.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Etapa.class.getName() + ".inspeccions", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Material.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Material.class.getName() + ".trabajos", jcacheConfiguration);
            cm.createCache(com.upcn.domain.Insumo.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Medidor.class.getName(), jcacheConfiguration);
            cm.createCache(com.upcn.domain.Precinto.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
