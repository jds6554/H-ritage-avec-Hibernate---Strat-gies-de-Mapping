package com.example.model.singletable;

import javax.persistence.*;

@Entity
@Table(name = "vehicule_single_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicule_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;

    // Constructeur sans argument requis par JPA
    public Vehicule() {
    }

    public Vehicule(String marque) {
        this.marque = marque;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    @Override
    public String toString() {
        return "Vehicule [id=" + id + ", marque=" + marque + "]";
    }
}
