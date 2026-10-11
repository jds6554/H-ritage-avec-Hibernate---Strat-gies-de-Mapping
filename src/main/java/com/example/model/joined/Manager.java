package com.example.model.joined;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager extends Employe {

    private int nombreEquipes;

    public Manager() {
        super();
    }

    public Manager(String nom, int nombreEquipes) {
        super(nom);
        this.nombreEquipes = nombreEquipes;
    }

    public int getNombreEquipes() {
        return nombreEquipes;
    }

    public void setNombreEquipes(int nombreEquipes) {
        this.nombreEquipes = nombreEquipes;
    }

    @Override
    public String toString() {
        return "Manager [id=" + getId() + ", nom=" + getNom() + ", nombreEquipes=" + nombreEquipes + "]";
    }
}
