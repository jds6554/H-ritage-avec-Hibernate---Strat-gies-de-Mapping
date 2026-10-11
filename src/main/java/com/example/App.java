package com.example;

import com.example.model.joined.Developpeur;
import com.example.model.joined.Employe;
import com.example.model.joined.Manager;
import com.example.model.singletable.Moto;
import com.example.model.singletable.Vehicule;
import com.example.model.singletable.Voiture;
import com.example.model.tableperclass.Electronique;
import com.example.model.tableperclass.Livre;
import com.example.model.tableperclass.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    public static void main(String[] args) {
        // Initialisation de la fabrique d'EntityManager via l'unité définie dans persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-inheritance");
        EntityManager em = emf.createEntityManager();

        System.out.println("=================================================");
        System.out.println("1. INSERTION DES DONNEES DANS LES 3 HIERARCHIES");
        System.out.println("=================================================");

        em.getTransaction().begin();

        // 1. SINGLE_TABLE (Vehicule -> Voiture, Moto)
        Voiture voiture = new Voiture("Toyota", 4);
        Moto moto = new Moto("Harley", true);
        em.persist(voiture);
        em.persist(moto);

        // 2. JOINED (Employe -> Developpeur, Manager)
        Developpeur dev = new Developpeur("Alice", "Java");
        Manager manager = new Manager("Bob", 3);
        em.persist(dev);
        em.persist(manager);

        // 3. TABLE_PER_CLASS (Produit -> Livre, Electronique)
        Livre livre = new Livre("Clean Code", "Robert C. Martin");
        Electronique electronique = new Electronique("Smartphone", 24);
        em.persist(livre);
        em.persist(electronique);

        em.getTransaction().commit();

        System.out.println("\n=================================================");
        System.out.println("2. LECTURE POLYMORPHIQUE VIA REQUETES JPQL");
        System.out.println("=================================================");

        // Lecture polymorphique : SINGLE_TABLE
        System.out.println("\n--- Liste des véhicules (SINGLE_TABLE) ---");
        em.createQuery("SELECT v FROM Vehicule v", Vehicule.class)
                .getResultList()
                .forEach(v -> System.out.println("-> Type instancié: " + v.getClass().getSimpleName() + " | " + v));

        // Lecture polymorphique : JOINED
        System.out.println("\n--- Liste des employés (JOINED) ---");
        em.createQuery("SELECT e FROM Employe e", Employe.class)
                .getResultList()
                .forEach(e -> System.out.println("-> Type instancié: " + e.getClass().getSimpleName() + " | " + e));

        // Lecture polymorphique : TABLE_PER_CLASS
        System.out.println("\n--- Liste des produits (TABLE_PER_CLASS) ---");
        em.createQuery("SELECT p FROM Produit p", Produit.class)
                .getResultList()
                .forEach(p -> System.out.println("-> Type instancié: " + p.getClass().getSimpleName() + " | " + p));

        // Clôture des ressources JPA
        em.close();
        emf.close();
        System.out.println("\nFin de l'application - Ressources fermées.");
    }
}
