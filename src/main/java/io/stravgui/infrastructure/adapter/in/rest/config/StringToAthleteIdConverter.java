package io.stravgui.infrastructure.adapter.in.rest.config;

import io.stravgui.domain.athlete.AthleteId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class StringToAthleteIdConverter implements Converter<String, AthleteId> {
    @Override
    public AthleteId convert(String source) {
        return AthleteId.of(source);
    }
}