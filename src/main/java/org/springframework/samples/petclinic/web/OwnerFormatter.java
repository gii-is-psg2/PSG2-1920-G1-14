package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Controller
public class OwnerFormatter implements Formatter<Owner> {

    private final OwnerService ownerService;

    @Autowired
    public OwnerFormatter(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public Owner parse(String s, Locale locale) throws ParseException {
        Collection<Owner> owners = this.ownerService.findAllOwners();
        for(Owner o : owners) {
            if(s.equals(o.getFirstName() + " " + o.getLastName())) {
                return o;
            }
        }
        throw new ParseException("Owner not found: " + s, 0);
    }

    @Override
    public String print(Owner owner, Locale locale) {
        return owner.getFirstName() + owner.getLastName();
    }
}
