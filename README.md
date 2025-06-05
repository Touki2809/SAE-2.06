# SAÉ 2.01, 2.02, 2.05 : MPM  
IUT Le Havre - Département Informatique  
Année 2024-2025

## Présentation

Ce projet met en œuvre la **Méthode des Potentiels et antécédents Métra (MPM)**, une technique de planification et d’ordonnancement utilisée pour gérer des projets complexes.  
L’application permet de déterminer la durée minimale d’un projet, les dates de début et de fin optimales pour chaque tâche, et d’identifier le chemin critique.

### Fonctionnalités principales

- Lecture d’un fichier texte décrivant les tâches et leurs dépendances.
- Calcul automatique des dates au plus tôt et au plus tard pour chaque tâche.
- Visualisation CUI (console) et GUI (graphique) du graphe des tâches.
- Calcul et affichage du chemin critique.
- Simulation progressive du calcul des dates (boutons "+ tôt" et "+ tard").
- Gestion graphique des tâches (déplacement, ajout, suppression, modification de durée).

## Arborescence du projet

```
E:.
│   compile.list
│   run.sh
│
├───class
├───data
│       test.data
│
└───src
    │   Controleur.java
    │
    ├───ihm
    │       FrameMPM.java
    │       Lien.java
    │       PanelBtn.java
    │       PanelMPM.java
    │       PanelTache.java
    │
    └───metier
            CheminCritique.java
            DateFr.java
            MPM.java
            Tache.java 
```

## Structure du code

- **src/metier/** : Logique métier (calculs MPM, gestion des tâches, dates, chemin critique)
- **src/ihm/** : Interface graphique (fenêtre principale, panels de tâches, boutons, liens)
- **src/Controleur.java** : Point d’entrée, gestion des interactions
- **data/** : Fichiers de données (exemples de projets)
- **class/** : Dossier de compilation

## Fonctionnement général

1. **Lecture des tâches** :  
   Les tâches sont décrites dans un fichier texte sous la forme :  
   `nom tâche|durée tâche|liste des tâches précédentes immédiates`

2. **Calculs** :  
   - Construction du graphe des tâches (arcs = dépendances)
   - Calcul des dates au plus tôt et au plus tard
   - Détermination du chemin critique (tâches à marge nulle)

3. **Visualisation** :  
   - **CUI** : Affichage détaillé des tâches, dates, marges, antériorités et postériorités
   - **GUI** : Graphe interactif, déplacement des tâches, simulation du calcul des dates, affichage du chemin critique

## Exemples de données

```
A|2|
B|4|
C|3|A
D|2|B
E|1|C,D
```

## Scénarios de test

- Calcul des dates et marges pour chaque tâche
- Affichage du chemin critique
- Simulation progressive avec les boutons "+ tôt" et "+ tard"
- Manipulation graphique des tâches

## Version actuelle

- Jusqu'au 5 inclus
- Gestion avec des `PanelTaches`

--

**Auteurs** :  
Projet réalisé dans le cadre des SAÉ 2.01, 2.02, 2.05 à l’IUT du Havre, année 2024-2025.
- HERMILLY Joshua    C2
- BLEOU    Kessin    B2
- HARRIS   Audric    A1
- LUCAS    Alexandre B1

