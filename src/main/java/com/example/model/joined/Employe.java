package com.example.model.joined;

import javax.persistence.*;

@Entity
@Table(name = "employe")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    public Employe() {
    }

    public Employe(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Employe [id=" + id + ", nom=" + nom + "]";
    }
}
