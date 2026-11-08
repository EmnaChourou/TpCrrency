# 💱 Currency Converter – Application Android Kotlin

[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![Android SDK](https://img.shields.io/badge/Min%20SDK-24-brightgreen.svg)](https://developer.android.com/)
[![UI](https://img.shields.io/badge/UI-ConstraintLayout%20%7C%20TableLayout-orange.svg)](https://developer.android.com/training/constraint-layout)

Une application Android native développée en **Kotlin** qui permet de convertir un montant d'une devise vers une autre à partir de taux de change fixes par rapport à l'Euro (**EUR**).

---

## 📱 Aperçu de l'Interface

L'écran unique (`activity_main.xml`) est séparé en deux zones principales par une `Guideline` à **45 %** de la hauteur de l'écran :

* **Zone d'affichage (Haut - 45 %)** : Fond bleu turquoise (`#2BB8E8`) contenant le titre, la sélection des devises source/cible (`Spinner`), le montant et le résultat soulignés (`TextView`), le bouton d'inversion (`ImageButton`) et le bouton de conversion (`Button`).
* **Zone du clavier (Bas - 55 %)** : Clavier numérique personnalisé en `TableLayout` (4 lignes × 3 colonnes) avec touches sans bordure (`32sp`), chiffres en turquoise (`#1BA6C9`) et touche d'effacement **C** en rouge (`#F0727A`).

---

## ✨ Fonctionnalités

- 🔀 **Conversion multi-devises** : Prise en charge de `USD`, `EUR`, `TND`, `GBP`, `JPY`, et `CAD`.
- ⌨️ **Clavier numérique sur mesure** : Pas de clavier système ; la saisie s'effectue exclusivement via le clavier virtuel de l'application.
- 🔄 **Inversion instantanée** : Bouton d'inversion pour permuter la devise source et la devise cible.
- ⚡ **Calcul automatique (Bonus)** : Mise à jour en temps réel lors des frappes ou des changements de sélections de devises.
- 🛡️ **Règles de gestion & Contrôles** :
  - Limitation du montant à **10 caractères** maximum.
  - Un **seul point décimal** autorisé (`.`).
  - Le premier chiffre tapé remplace le `0` initial.
  - La touche **C** réinitialise le montant à `0` et efface le résultat.
  - Alerte `Toast` ("Veuillez saisir un montant") si l'utilisateur valide avec un montant de `0`.
- 📱 **Orientation Portrait** : Verrouillée en portrait (`android:screenOrientation="portrait"`).

---

## 🧮 Taux de Change Fixes (Pivot : EUR)

Les conversions s'effectuent en passant par l'Euro (`EUR`) comme devise pivot :

$$\text{Montant}_{\text{EUR}} = \frac{\text{Montant}}{\text{Taux}(\text{Source})}$$
$$\text{Résultat} = \text{Montant}_{\text{EUR}} \times \text{Taux}(\text{Cible})$$

| Code Devise | Devise | Taux pour 1 EUR |
| :---: | :--- | :---: |
| **EUR** | Euro | `1.00` |
| **USD** | Dollar américain | `1.17` |
| **TND** | Dinar tunisien | `3.40` |
| **GBP** | Livre sterling | `0.87` |
| **JPY** | Yen japonais | `172.50` |
| **CAD** | Dollar canadien | `1.62` |

---

## 🧪 Cas de Tests Validés

Les cas de tests suivants ont été vérifiés et automatisés via des tests unitaires (`ExampleUnitTest.kt`) :

| Montant | Devise Source | Devise Cible | Résultat Attendu | Statut |
| :---: | :---: | :---: | :---: | :---: |
| `120` | `USD` | `EUR` | `102.56` | ✅ Validé |
| `100` | `EUR` | `TND` | `340.00` | ✅ Validé |
| `50` | `GBP` | `USD` | `67.24` | ✅ Validé |
| `10` | `EUR` | `EUR` | `10.00` | ✅ Validé |

---

## 📸 Captures d'Écran

Les captures d'écran se trouvent dans le dossier `screenshots/` à la racine du dépôt :

- `screenshots/00_demarrage.png` – Écran d'accueil au démarrage.
- `screenshots/01_test_120_USD_EUR.png` – Test 120 USD vers EUR.
- `screenshots/02_test_100_EUR_TND.png` – Test 100 EUR vers TND.
- `screenshots/03_test_50_GBP_USD.png` – Test 50 GBP vers USD.
- `screenshots/04_test_10_EUR_EUR.png` – Test 10 EUR vers EUR.

---

## 🛠️ Stack Technique

- **Langage** : Kotlin
- **Architecture UI** : XML Layouts (`ConstraintLayout`, `TableLayout`, `Guideline`)
- **Binding** : View Binding (`ActivityMainBinding`)
- **Min SDK** : API 24 (Android 7.0 Nougat)
- **Target SDK** : API 37
- **Outil de Build** : Gradle (Kotlin DSL `build.gradle.kts`)

---

