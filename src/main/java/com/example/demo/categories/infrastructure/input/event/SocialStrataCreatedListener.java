package com.example.demo.categories.infrastructure.input.event;

import com.example.demo.categories.infrastructure.output.persistence.CachedSocialStrataEntity;
import com.example.demo.categories.infrastructure.output.persistence.JpaCachedSocialStrataRepository;
import com.example.demo.social_strata.domain.event.SocialStrataCreatedDomainEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SocialStrataCreatedListener {
    private final JpaCachedSocialStrataRepository cachedSocialStrataRepository;

    public SocialStrataCreatedListener(JpaCachedSocialStrataRepository jpaCachedSocialStrataRepository){
        this.cachedSocialStrataRepository = jpaCachedSocialStrataRepository;
    }

    @EventListener
    public void on(SocialStrataCreatedDomainEvent event){
        cachedSocialStrataRepository.save(new CachedSocialStrataEntity(event.aggregateId(),event.name()));
    }
}
