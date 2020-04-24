package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class CauseFormatter implements Formatter<Cause> {

    private CauseService causeService;

    @Autowired
    public CauseFormatter(CauseService service) {
        this.causeService = service;
    }

    @Override
    public Cause parse(String stringId, Locale locale) throws ParseException {
        int id = Integer.parseInt(stringId);
        return this.causeService.findCauseById(id);
    }

    @Override
    public String print(Cause cause, Locale locale) {
        return cause.getId().toString();
    }
}
