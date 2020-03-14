package org.springframework.samples.petclinic.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Book extends BaseEntity {

    @Column(name = "start")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate start;

    @Column(name = "finish")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate finish;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
