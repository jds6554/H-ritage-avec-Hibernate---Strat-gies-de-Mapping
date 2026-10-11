# TP JPA / Hibernate - Gestion de l'Héritage

Ce projet illustre et compare les trois stratégies de mapping d'héritage en **JPA / Hibernate** avec une base **H2** en mémoire :
1. `SINGLE_TABLE`
2. `JOINED`
3. `TABLE_PER_CLASS`

---

## 1. Prérequis & Environnement
- **Java** : JDK 8 ou supérieur
- **Maven** : 3.6+
- **Bibliothèques principales** :
  - `javax.persistence-api` (2.2)
  - `hibernate-core` (5.4.30.Final)
  - `h2` (1.4.200)

---

## 2. Structure du projet

```text
src/
├── main/
│   ├── java/com/example/
│   │   ├── App.java                               # Classe principale (tests CRUD et requêtes JPQL)
│   │   └── model/
│   │       ├── singletable/                       # Stratégie 1 : SINGLE_TABLE
│   │       │   ├── Vehicule.java (classe abstraite parente)
│   │       │   ├── Voiture.java
│   │       │   └── Moto.java
│   │       ├── joined/                            # Stratégie 2 : JOINED
│   │       │   ├── Employe.java (classe abstraite parente)
│   │       │   ├── Developpeur.java
│   │       │   └── Manager.java
│   │       └── tableperclass/                     # Stratégie 3 : TABLE_PER_CLASS
│   │           ├── Produit.java (classe abstraite parente)
│   │           ├── Livre.java
│   │           └── Electronique.java
│   └── resources/
│       └── META-INF/
│           └── persistence.xml                    # Unité de persistance, pilote H2 et config Hibernate
```

---

## 3. Synthèse des Stratégies d'Héritage

### A. SINGLE_TABLE (`@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`)
- **Principe** : Une table unique pour toute la hiérarchie (`vehicule_single_table`).
- **Discrimination** : Utilisation de `@DiscriminatorColumn` et `@DiscriminatorValue` pour typer les lignes.
- **SQL généré** : `SELECT` direct sans jointure.
- **Compromis** : Lecture ultra-rapide, mais colonnes spécifiques obligatoirement `NULL` pour les types non concernés.

### B. JOINED (`@Inheritance(strategy = InheritanceType.JOINED)`)
- **Principe** : Une table pour le parent (`employe`) et une table par sous-classe (`developpeur`, `manager`).
- **Liaison** : Clé primaire de l'enfant = Clé étrangère pointant vers la table mère.
- **SQL généré** : Double `INSERT` à la création ; `LEFT OUTER JOIN` lors des lectures polymorphiques.
- **Compromis** : Modèle relationnel normalisé (3NF) et intégrité stricte, au prix de jointures plus lourdes.

### C. TABLE_PER_CLASS (`@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)`)
- **Principe** : Une table indépendante par classe concrète (`livre`, `electronique`), aucune table pour la classe abstraite `Produit`.
- **SQL généré** : `UNION ALL` obligatoire lors des requêtes polymorphiques (`SELECT p FROM Produit p`).
- **Compromis** : Autonomie des tables filles, mais requêtes globales très coûteuses et complexité de génération des IDs.

---

## 4. Bonnes pratiques en production

1. **Choix de la stratégie** :
   - Préférer `SINGLE_TABLE` pour les hiérarchies simples avec peu d'attributs distincts (performances de lecture optimales).
   - Préférer `JOINED` pour les modèles d'entreprise riches nécessitant des contraintes relationnelles fortes (`NOT NULL`, FK).
   - Éviter `TABLE_PER_CLASS` dès que des requêtes polymorphiques sur la classe parente sont régulières.
2. **Optimisation SQL** :
   - Indexer la colonne discriminante en `SINGLE_TABLE` pour accélérer les requêtes ciblant un sous-type particulier.
3. **Audit et Migrations** :
   - Toujours conserver `show_sql` et `format_sql` actifs pendant la phase de dev pour valider les plans de requêtes générés.
   - Bannir `hibernate.hbm2ddl.auto = create-drop` en production au profit d'outils de migration versionnés comme **Liquibase** ou **Flyway**.

---

## 5. Exécution

### En ligne de commande Maven :
```bash
mvn clean compile exec:java
```

### Depuis l'IDE :
Ouvrir `src/main/java/com/example/App.java` et lancer la méthode `main`.
