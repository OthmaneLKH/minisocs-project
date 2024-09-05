# Gestion de mini réseaux sociaux MiniSocs

Binôme :

* Othmane Lkhalidi
* Yasmine Aluch

## Syntaxe MarkDown

La syntaxe MarkDown de ce document est compatible avec la syntaxe
GitLab, qui est documentée dans
https://docs.gitlab.com/ee/user/markdown.html

## 1. Spécification

### 1.1. Acteurs et cas d'utilisation

La **première étape** consiste à **bien comprendre le système** à
étudier. Dans le cadre de l'exercice, cela consiste à lire
attentivement l'énoncé. Cette lecture doit permettre de *délimiter les
contours du système* à réaliser. La méthode générale consiste à
retrouver les acteurs qui interagissent avec lui. Il est très
important de fixer des frontières au problème. Ensuite, nous
recherchons les fonctionnalités du système par la définition de ses
cas d'utilisation. Dans le cadre de ce module, il s'agit de rechercher
les principales fonctions attendues du système. Nous nous limitons aux
cas d'utilisation pour atteindre les premiers objectifs indiqués dans
le cahier des charges, en prenant en considération les simplifications
énoncées dans le cahier des charges.

Pour réaliser le diagramme de cas d'utilisation à partir de l'analyse
du texte :

* rechercher les acteurs, avec les potentielles relation de
  généralisation spécialisation,
* rechercher les fonctionnalités du système accessibles aux acteurs

Pour rappel, la documentation du langage pour écrire les diagrammes
UML avec PlantUML est disponible à l'adress suivante :

- (https://plantuml.com/fr/)

Voici ci-dessous le diagramme de cas d'utilisation avec les cas
d'utilisation les plus importants (code
[source](./Diagrammes/minisocs_uml_diag_cas_utilisation.pu)).

![diagrammecasutilisation](./Diagrammes/minisocs_uml_diag_cas_utilisation.svg)

### 1.2. Priorités, préconditions et postconditions des cas d'utilisation

Les priorités des cas d'utilisation sont choisies avec les règles de
bon sens suivantes:

* pour retirer une entité du système, elle doit y être. La priorité de
  l'ajout est donc supérieure ou égale à la priorité du retrait ;

* pour lister les entités d'un type donné, elles doivent y être. La
  priorité de l'ajout est donc supérieure ou égale à la priorité du
  listage ;

* il est *a priori* possible, c.-à-d. sans raison contraire, de
  démontrer la mise en œuvre d'un sous-ensemble des fonctionnalités du
  système, et plus particulièrement la prise en compte des principales
  règles de gestion, sans les retraits ou les listages ;

* la possibilité de lister aide au déverminage de l'application
  pendant les activités d'exécution des tests de validation.

Par conséquent, les cas d'utilisation d'ajout sont *a priori* de
priorité « HAUTE », ceux de listage de priorité « Moyenne», et ceux de
retrait de priorité « basse ».

Voici les précondition et postcondition des cas d'utilisation de
priorité HAUTE.

#### Ajouter un utilisateur (HAUTE)

- précondition : \
  ∧ pseudo bien formé (non null ∧ non vide) \
  ∧ nom bien formé  (non null ∧ non vide) \
  ∧ prénom bien formé  (non null ∧ non vide) \
  ∧ courriel bien formé (respectant le standard RFC822) \
  ∧ utilisateur avec ce pseudo inexistant
- postcondition : \
  ∧ utilisateur avec ce pseudo existant \
  ∧ le compte de l'utilisateur est actif

#### Désactiver son compte (HAUTE)

- précondition : \
  ∧ pseudo bien formé (non null ∧ non vide) \
  ∧ le compte n'est pas bloqué \
  ∧ utilisateur avec ce pseudo existant
- postcondition : le compte de l'utilisateur est désactivé

NB : l'opération est idempotente.

#### Créer un réseau social (HAUTE)

- précondition : \
  ∧ pseudo_rs bien formé (non null ∧ non vide) \
  ∧ nom du réseau social bien formé (non null ∧ non vide) \
  ∧ nom du createur bien formé (non null ∧ non vide) \
  ∧ le compte du créateur n'est pas bloqué

- Postcondition : \
  ∧ le réseau social est créé \
  ∧ les utilisateurs peuvent poster des messages \
  ∧ le réseau social peut être fermé
  ∧ le réseau social est ouvert

#### Ajouter un membre à un réseau social (HAUTE)

- précondition : \
  ∧ pseudo bien formé (non null ∧ non vide) \
  ∧ utilisateur qui ajoute bien formé (non null ∧ non vide) \
  ∧ utilisateur à ajouter bien formé (non null ∧ non vide) \
  ∧ reseau social bien formé (non null ∧ non vide) \
  ∧ Aucun réseau social existant à ce nom \
  ∧ Utilisateur à ajouter à un compte actif \
  ∧ Utilisateur qui ajoute est modérateur \
  ∧ la membership n'existe pas deja \
  ∧ le compte de l'utilisateur n'est pas bloqué \
  ∧ le compte de l'utilisateur n'est pas désactivé 
- postcondition : \
  ∧ utilisateur avec ce pseudo existant dans ce reseau social \
  ∧ l'utilisateur à un role de simple membre \

#### Poster un message (HAUTE)

- précondition : \
  ∧ l'utilisateur est membre du réseau social \
  ∧ message bien formé (non null ∧ non vide) \
  ∧ message accepté par les modérateurs si pas modérateur 
- postcondition : \
  ∧ message posté \
  ∧ message peut être caché

#### Moderer message (HAUTE)

- précondition : \
  ∧ message bien formé (non null ∧ non vide) \
  ∧ message existe deja\
  ∧ etat message = non visible
  ∧ Statut message = En attente de modération
- postcondition : \
  ∧ message accepté ou non et donc statut != "en attente de modération" \
  ∧ message visible ou non

#### Autres cas d'utilisation et leur priorité respective

- Retirer un utilisateur (basse)

- Bloquer le compte d'un utilisateur (basse)

- Lister les utilisateurs (moyenne)

- Promouvoir un membre (basse)

- Fermer un réseau social (haute)

## 2. Préparation des tests de validation des cas d'utilisation

#### Ajouter un utilisateur (HAUTE)

|                                                     | 1   | 2   | 3   | 4   | 5   | 6   |
|:--------------------------------------------------- |:--- |:--- |:--- | --- | --- | --- |
| pseudo bien formé (non null ∧ non vide)             | F   | T   | T   | T   | T   | T   |
| nom bien formé  (non null ∧ non vide)               |     | F   | T   | T   | T   | T   |
| prénom bien formé  (non null ∧ non vide)            |     |     | F   | T   | T   | T   |
| courriel bien formé (respectant le standard RFC822) |     |     |     | F   | T   | T   |
| utilisateur avec ce pseudo inexistant               |     |     |     |     | F   | T   |
|                                                     |     |     |     |     |     |     |
| utilisateur avec ce pseudo existant                 | F   | F   | F   | F   | F   | T   |
| compte de l'utilisateur actif                       | F   | F   | F   | F   | F   | T   |
|                                                     |     |     |     |     |     |     |
| nombre de tests dans le jeu de tests                | 2   | 2   | 2   | 3   | 1   | 1   |

Le jeu de test 4 comporte trois tests : non null, non vide, et adresse
courriel bien formée. On aurait pu n'en faire qu'un en considérant la
bibliothèque de validation RFC822 vérifie les deux premières
conditions.

#### Désactiver son compte (HAUTE)

|                                          | 1   | 2   | 3   | 4   |
|:---------------------------------------- |:--- |:--- |:--- |:--- |
| pseudo bien formé (non null ∧ non vide)  | F   | T   | T   | T   |
| le compte n'est pas bloqué               |     | F   | T   | T   |
| utilisateur avec ce pseudo existant      |     |     | F   | T   |
|                                          |     |     |     |     |
| le compte de l'utilisateur est désactivé | F   | F   | F   | T   |
|                                          |     |     |     |     |
| nombre de tests dans le jeu de tests     | 2   | 1   | 1   | 1   |

#### Créer un réseau social (HAUTE)

|                                                       | 1   | 2   | 3   |
|:----------------------------------------------------- |:--- |:--- |:--- |
| nom du réseau social bien formé (non null ∧ non vide) | F   | T   | T   |
| Compte du créateur non bloqué                         |     | F   | T   |
|                                                       |     |     |     |
| le réseau social est créé                             | F   | F   | T   |
| les utilisateurs peuvent poster des messages          | F   | F   | T   |
| le réseau social peut être fermé                      | F   | F   | T   |
| nombre de tests dans le jeu de tests                  | 2   | 1   | 1   |

#### Ajouter un membre à un réseau social (HAUTE)

|                                                | 1   | 2   | 3   | 4   | 5   |
|:---------------------------------------------- |:--- |:--- |:--- | --- | --- |
| pseudo bien formé (non null ∧ non vide)        | F   | T   | T   | T   | T   |
| pseudo inexistant dans ce réseau social        |     | F   | T   | T   | T   |
| le compte de l'utilisateur n'est pas bloqué    |     |     | F   | T   | T   |
| le compte de l'utilisateur n'est pas désactivé |     |     |     | F   | T   |
|                                                |     |     |     |     |     |
| utilisateur avec ce pseudo existant            | F   | F   | F   | F   | T   |
| l'utilisateur peut poster des messages         | F   | F   | F   | F   | T   |
| l'utilisateur peut devenir modérateur          | F   | F   | F   | F   | T   |
|                                                |     |     |     |     |     |
| nombre de tests dans le jeu de tests           | 2   | 1   | 1   | 1   | 1   |

#### Poster un message  (HAUTE)

|                                                       | 1   | 2   | 3   | 4   |
|:----------------------------------------------------- |:--- |:--- |:--- |:--- |
| l'utilisateur est membre  du réseau social            | F   | T   | T   | T   |
| message bien formé (non null ∧ non vide)              |     | F   | T   | T   |
| message accepté par les modérateurs si pas modérateur |     |     | F   | T   |
| message posté                                         | F   | F   | F   | T   |
| message peut être caché                               | F   | F   | F   | T   |
|                                                       |     |     |     |     |
| nombre de tests dans le jeu de tests                  | 1   | 2   | 1   | 1   |

#### Moderer message (HAUTE)

|                                                                     | 1   | 2   | 3   | 4   | 5   |
|:------------------------------------------------------------------- |:--- |:--- |:--- | --- | --- |
| message bien formé (non null ∧ non vide)                            | F   | T   | T   | T   | T   |
| message existe deja                                                 |     | F   | T   | T   | T   |
| etat message = non visible                                          |     |     | F   | T   | T   |
| Statut message = En attente de modération                           |     |     |     | F   | T   |
|                                                                     |     |     |     |     |     |
| message accepté ou non et donc statut != "en attente de modération" | F   | F   | F   | F   | T   |
| message visible ou non                                              | F   | F   | F   | F   | T   |
|                                                                     |     |     |     |     |     |
| nombre de tests dans le jeu de tests                                | 2   | 1   | 1   | 1   | 1   |

# 3. Conception

## 3.1. Listes des classes candidates et de leurs attributs

Voici les listes des classes candidates et de leurs attributs:

- `MiniSocs` (mise en œuvre du patron de conception Façade) avec
  l'attribut `nom` pour le nom du système,
- `Utilisateur` avec les attributs `pseudo` pour identifier de manière
  unique un utilisateur, `nom` et `prénom`, adresse `courriel`, et
  `etatCompte` pour l'état de son compte,
- `ÉtatCompte` avec les énumérateurs `COMPTE_ACTIF` et `COMPTE_DÉSACTIVÉ`,

## 3.2. Premières opérations des classes

Les seules opérations que nous connaissons déjà sont celles
correspondant aux cas d'utilisation. Comme nous utilisons le patron de
conception Façade, toutes les opérations des cas d'utilisation sont
dans la Façade.

Donc, dans la classe `MiniSocs`, voici les premières opérations (en
ignorant celles de priorité « basse ») :

- `ajouterUtilisateur`,
- `désactiverCompte`,
- `bloquerCompte`,
- `listerUtilisateurs`.

## 3.3. Diagramme de classes

Le diagramme de classes obtenu lors d'une analyse à partir de l'énoncé
du problème est donné dans la figure qui suit. Dans ces diagrammes,
les opérations ne sont pas mentionnées parce qu'il y en aurait trop.

**Important: même dans les diagrammes de la conception détaillée, on
ne montre pas les attributs traduisant des associations.**

Pour rappel, la documentation du langage pour écrire les diagrammes
UML avec PlantUML est disponible à l'adress suivante :

- (https://plantuml.com/fr/)

Version sans les notifications
([source](./Diagrammes/minisocs_uml_diag_classes_sans_notif.pu)).

![diagrammeclasses](./Diagrammes/minisocs_uml_diag_classes_sans_notif.svg)
([source](./Diagrammes/minisocs_uml_diag_classes_sans_notif.pu))

## 3.4. Diagrammes de séquence

Dans la suite, plusieurs versions d'un même diagramme de séquence sont proposés :

- une version dite « recommandée » **avec** les barres d'activation,
- une version dite « simplifiée » **sans** les barres d'activation.

Pour rappel, la documentation du langage pour écrire les diagrammes
UML avec PlantUML est disponible à l'adress suivante :

- (https://plantuml.com/fr/)

#### Ajouter un utilisateur (HAUTE)

Version recommandée
([source](./Diagrammes/minisocs_uml_diag_seq_ajouter_utilisateur.pu)).

![diagrammeséquenceajouterutilisateur](./Diagrammes/minisocs_uml_diag_seq_ajouter_utilisateur.svg)
([source](./Diagrammes/minisocs_uml_diag_seq_ajouter_utilisateur.pu))

Version simplifiée
([source](./Diagrammes/minisocs_uml_diag_seq_ajouter_utilisateur_version_simplifiee.pu)).

![diagrammeséquenceajouterutilisateursimplifié](./Diagrammes/minisocs_uml_diag_seq_ajouter_utilisateur_version_simplifiee.svg)
([source](./Diagrammes/minisocs_uml_diag_seq_ajouter_utilisateur_version_simplifiee.pu))

#### Créer réseau social (HAUTE)

Version recommandée
([source](./Diagrammes/minisocs_uml_diag_seq_creer_minisocs.pu)).

![diagrammeséquencecreerminisocs](./Diagrammes/minisocs_uml_diag_seq_creer_minisocs.svg)
([source](./Diagrammes/minisocs_uml_diag_seq_creer_minisocs.pu))

#### Ajouter un membre à un réseau social (HAUTE)

Version recommandée
([source](./Diagrammes/DSUC2.pu)).

![diagrammeséquenceajoutermembre](./Diagrammes/DSUC2.svg)
([source](./Diagrammes/DSUC2.pu))

#### Poster un message (HAUTE)

Version recommandée
([source](./Diagrammes/minisocs_uml_diag_seq_poster_message.pu)).

![diagrammeséquencecreerminisocs](./Diagrammes/minisocs_uml_diag_seq_poster_message.png)
([source](./Diagrammes/minisocs_uml_diag_seq_poster_message.pu))

# 7. Diagrammes de machine à états et invariants

Dans les diagrammes de machine à états, nous faisons le choix de faire
apparaître les états de création et de destruction. Ces états sont
transitoires, il est vrai, mais ils méritent cependant une attention
particulière.  L'état de création, en particulier, donne lieu, lors de
la réalisation dans un langage de programmation orienté objet, à
l'écriture d'une opération « constructeur » qui garantit que
tous les attributs sont initialisés correctement dès la création d'une
instance. Nous savons également qu'en Java la destruction se réalise
en « oubliant » l'objet : un mécanisme de ramasse
miettes détruit automatiquement les objets lorsqu'ils ne sont plus
référencés. Il n'en est pas de même dans tous les langages, et par
exemple en C++ qui ne possède pas de mécanisme de ramasse miettes, la
destruction des objets peut s'avérer un casse tête ardu.

Les actions provoquées par des appels en provenance d'autres objets
apparaissent sur les transitions. Nous avons gardé comme action
interne uniquement les actions correspondant à des appels que l'objet
fait seul ou fait de manière répétitive.  Les constructeurs et
destructeurs sont des exceptions (ils apparaissent en interne bien
qu'étant déclenchés par un autre objet).

## 7.1. Classe Utilisateur

### 7.1.1. Diagramme de machine à états

Diagramme ([source](./Diagrammes/minisocs_uml_diag_machine_a_etats_utilisateur.pu)).

![diagrammemachineaétatsutilisateur](./Diagrammes/minisocs_uml_diag_machine_a_etats_utilisateur.svg)
([source](./Diagrammes/minisocs_uml_diag_machine_a_etats_utilisateur.pu))

### 7.1.2. Fiche de la classe

Voici tous les attributs de la classe :

```
— final String pseudonyme
— String nom
— String prenom
— String courriel
— EtatCompte etatCompte
— Statut statut
```

### 7.1.3. Invariant

```
  pseudonyme != null ∧ !pseudonyme.isBlank()
∧ nom != null ∧ !nom.isBlank()
∧ prenom != null ∧ !prenom.isBlank()
∧ EmailValidator.getInstance().isValid(courriel)
∧ etatCompte != null
```

## 7.2. Classe Message

### 7.2.1. Diagramme de machine à états

Diagramme ([source](./Diagrammes/DMEC1.pu)).

![diagrammemachineaétatmessage](./Diagrammes/DMEC1.svg)
([source](./Diagrammes/DMEC1.pu))

### 7.2.2. Fiche de la classe

Voici tous les attributs de la classe :

```
— String contenu
— String auteur
— Statut_mes statut_mes
— Etat_mes etat_mes
```

### 7.2.3. Invariant

```
  contenu != null ∧ !contenu.isBlank()
∧ auteur != null ∧ !auteur.isBlank()
∧ statut_mes != null
∧ etat_mes != null
```

## 7.3. Classe Reseau_social

### 7.3.1. Diagramme de machine à états

Diagramme ([source](./Diagrammes/minisocs_uml_diag_machine_a_etats_reseau_social.pu)).

![diagrammemachineaétatsutilisateur](./Diagrammes/minisocs_uml_diag_machine_a_etats_reseau_social.png)
([source](./Diagrammes/minisocs_uml_diag_machine_a_etats_reseau_social.pu))

### 7.3.2. Fiche de la classe

Voici tous les attributs de la classe :

```
— String nom
— String créateur
— Etat_res etat_res
```

### 7.3.3. Invariant

```
  nom != null ∧ !nom.isBlank()
∧ créateur != null ∧ !créateur.isBlank()
∧ etat_res != null
```

## 7.4. Classe Membre

### 7.4.1. Diagramme de machine à états

Diagramme ([source](./Diagrammes/minisocs_uml_diag_machine_a_etats_membre.pu)).

![diagrammemachineaétatsutilisateur](./Diagrammes/minisocs_uml_diag_machine_a_etats_membre.png)
([source](./Diagrammes/minisocs_uml_diag_machine_a_etats_membre.pu)) 

### 7.4.2. Fiche de la classe

Voici tous les attributs de la classe :

```
— String pseudo_rs
— Role Rôle
```

### 7.4.3. Invariant

```
  pseudo_rs != null ∧ !pseudo_rs.isBlank()
∧ Rôle != null
```

# 8 Préparation des tests unitaires

## 8.1. Opérations de la classe Utilisateur

### Opération constructeur

|                                              | 1   | 2   | 3   | 4   | 5   |
|:-------------------------------------------- |:--- |:--- |:--- |:--- |:--- |
| pseudonyme bien formé (non null ∧ non vide)  | F   | T   | T   | T   | T   |
| nom bien formé (non null ∧ non vide)         |     | F   | T   | T   | T   |
| prénom bien formé  (non null ∧ non vide)     |     |     | F   | T   | T   |
| courriel bien formé selon le standard RFC822 |     |     |     | F   | T   |
|                                              |     |     |     |     |     |
| pseudonyme' = pseudonyme                     | F   | F   | F   | F   | T   |
| nom' = nom                                   | F   | F   | F   | F   | T   |
| prénom' = prénom                             | F   | F   | F   | F   | T   |
| courriel' = courriel                         | F   | F   | F   | F   | T   |
| étatCompte' = actif                          | F   | F   | F   | F   | T   |
|                                              |     |     |     |     |     |
| levée d'un exception                         | oui | oui | oui | oui | non |
|                                              |     |     |     |     |     |
| nombre de tests dans le jeu de tests         | 2   | 2   | 2   | 3   | 1   |

Trois tests dans le jeu de tests 5 pour non null, puis non vide, et
enfin une chaîne de caractères qui n'est pas une adresse courriel.

### Opération désactiverCompte

|                                      | 1   | 2   |
|:------------------------------------ |:--- |:--- |
| étatCompte = actif                   | F   | T   |
|                                      |     |     |
| étatCompte' = désactivé              |     | T   |
|                                      |     |     |
| levée d'une exception                | oui | non |
|                                      |     |     |
| nombre de tests dans le jeu de tests | 1   | 2   |

Deux tests dans le jeu de tests 2 pour l'idempotence.

## 8.2. Opérations de la classe Message

### Opération constructeur

|                                                | 1   | 2   | 3   | 4   | 5   |
|:---------------------------------------------- |:--- |:--- |:--- |:--- |:--- |
| contenu (non null ∧ non vide)                  | F   | T   | T   | T   | T   |
| etatCompte auteur (non bloqué ∧ non désactivé) |     | F   | T   | T   | T   |
| Statut_mes en attente de modération            |     |     | F   | T   | T   |
| Etat_mes non visible                           |     |     |     | F   | T   |
|                                                |     |     |     |     |     |
| contenu' = contenu                             | F   | F   | F   | F   | T   |
| statut_mes' = en attente de modération         | F   | F   | F   | F   | T   |
| etat_mes' = visible                            | F   | F   | F   | F   | T   |
|                                                |     |     |     |     |     |
| levée d'un exception                           | oui | oui | oui | oui | non |
|                                                |     |     |     |     |     |
| nombre de tests dans le jeu de tests           | 2   | 2   | 2   | 1   | 1   |

### Opération modérer

|                                        | 1   | 2   | 3   | 4   |
|:-------------------------------------- |:--- |:--- |:--- |:--- |
| message en attente de modération       | F   | T   | T   | T   |
| message accepté par les modérateurs    |     | F   | T   | T   |
| message visible par les utilisateurs   |     |     | F   | T   |
|                                        |     |     |     |     |
| statut_mes' = en attente de modération | F   | F   | F   | T   |
| statut_mes' = accepté                  | F   | F   | F   | T   |
| etat_mes' = visible                    | F   | F   | F   | T   |
|                                        |     |     |     |     |
| levée d'un exception                   | oui | oui | oui | non |
|                                        |     |     |     |     |
| nombre de tests dans le jeu de tests   | 1   | 1   | 1   | 1   |

---

FIN DU DOCUMENT
