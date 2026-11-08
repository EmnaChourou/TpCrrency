package com.example.constraintlayout

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.constraintlayout.databinding.ActivityMainBinding
import java.util.Locale

/**
 * Activity principale pour l'application Currency Converter.
 * Permet de convertir un montant entre différentes devises à partir de taux fixes.
 */
class MainActivity : AppCompatActivity() {

    // View Binding pour accéder aux composants d'interface
    private lateinit var binding: ActivityMainBinding

    // Map contenant les taux de change fixes par rapport à 1 EUR
    private val taux = mapOf(
        "EUR" to 1.0,
        "USD" to 1.17,
        "TND" to 3.40,
        "GBP" to 0.87,
        "JPY" to 172.50,
        "CAD" to 1.62,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Souligner le montant et le résultat comme demandé par le TP
        binding.tvMontant.paintFlags = binding.tvMontant.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.tvResultat.paintFlags = binding.tvResultat.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        // Initialisation des des spinners
        configurerSpinners()

        // Configuration des écouteurs pour les touches du clavier numérique
        configurerClavier()

        // Bouton de conversion
        binding.btnConvertir.setOnClickListener {
            effectuerConversion()
        }

        // Bouton d'inversion des devises
        binding.btnInverser.setOnClickListener {
            inverserDevises()
        }
    }

    /**
     * Remplit et configure les spinners de sélection de devises.
     * La source affiche par défaut USD et la cible EUR.
     */
    private fun configurerSpinners() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.devises,
            android.R.layout.simple_spinner_item,
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spDeviseSource.adapter = adapter
        binding.spDeviseCible.adapter = adapter

        // Sélection par défaut : Source = USD, Cible = EUR
        val devisesArray = resources.getStringArray(R.array.devises)
        val indexUSD = devisesArray.indexOf("USD").takeIf { it >= 0 } ?: 0
        val indexEUR = devisesArray.indexOf("EUR").takeIf { it >= 0 } ?: 1

        binding.spDeviseSource.setSelection(indexUSD)
        binding.spDeviseCible.setSelection(indexEUR)

        // Ecouteurs pour recalculer la conversion en cas de changement de sélection
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (binding.tvResultat.text.isNotEmpty()) {
                    effectuerConversion(afficherToastErreur = false)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spDeviseSource.onItemSelectedListener = listener
        binding.spDeviseCible.onItemSelectedListener = listener
    }

    /**
     * Associe les événements de clic aux touches du clavier numérique.
     */
    private fun configurerClavier() {
        binding.btn0.setOnClickListener { ajouterCaractere("0") }
        binding.btn1.setOnClickListener { ajouterCaractere("1") }
        binding.btn2.setOnClickListener { ajouterCaractere("2") }
        binding.btn3.setOnClickListener { ajouterCaractere("3") }
        binding.btn4.setOnClickListener { ajouterCaractere("4") }
        binding.btn5.setOnClickListener { ajouterCaractere("5") }
        binding.btn6.setOnClickListener { ajouterCaractere("6") }
        binding.btn7.setOnClickListener { ajouterCaractere("7") }
        binding.btn8.setOnClickListener { ajouterCaractere("8") }
        binding.btn9.setOnClickListener { ajouterCaractere("9") }
        binding.btnDot.setOnClickListener { ajouterCaractere(".") }

        // Touche C : réinitialise le montant à "0" et vide le résultat
        binding.btnC.setOnClickListener {
            binding.tvMontant.text = "0"
            binding.tvResultat.text = ""
        }
    }

    /**
     * Ajoute un caractère au montant saisi en respectant les règles de gestion :
     * - Limite à 10 caractères.
     * - Un seul point décimal autorisé.
     * - Le premier chiffre tapé remplace le "0" initial.
     */
    private fun ajouterCaractere(c: String) {
        val texteActuel = binding.tvMontant.text.toString()

        // Règle : Limité à 10 caractères
        if (texteActuel.length >= 10) return

        // Règle : Un seul point décimal autorisé
        if ((c == ".") && texteActuel.contains(".")) return

        val nouveauTexte = if (texteActuel == "0") {
            if (c == ".") "0." else c
        } else {
            texteActuel + c
        }

        binding.tvMontant.text = nouveauTexte

        // Si une conversion a déjà été affichée, mise à jour automatique du résultat
        if (binding.tvResultat.text.isNotEmpty()) {
            effectuerConversion(afficherToastErreur = false)
        }
    }

    /**
     * Effectue la conversion de devise en passant par l'Euro (EUR) comme devise pivot.
     *
     * @param montant Montant à convertir
     * @param source Code de la devise source
     * @param cible Code de la devise cible
     * @return Le montant converti
     */
    private fun convertir(montant: Double, source: String, cible: String): Double {
        val tauxSource = taux[source] ?: 1.0
        val tauxCible = taux[cible] ?: 1.0

        val montantEnEUR = montant / tauxSource
        return montantEnEUR * tauxCible
    }

    /**
     * Récupère les données saisies, applique les contrôles et affiche le résultat.
     */
    private fun effectuerConversion(afficherToastErreur: Boolean = true) {
        val montantStr = binding.tvMontant.text.toString()
        val montant = montantStr.toDoubleOrNull() ?: 0.0

        // Si le montant vaut 0
        if (montant == 0.0) {
            if (afficherToastErreur) {
                Toast.makeText(this, getString(R.string.toast_saisir_montant), Toast.LENGTH_SHORT).show()
            }
            binding.tvResultat.text = ""
            return
        }

        val source = binding.spDeviseSource.selectedItem?.toString() ?: "USD"
        val cible = binding.spDeviseCible.selectedItem?.toString() ?: "EUR"

        val resultat = convertir(montant, source, cible)

        // Formater le résultat arrondi à 2 décimales
        binding.tvResultat.text = String.format(Locale.US, "%.2f", resultat)
    }

    /**
     * Échange les sélections des deux spinners et relance la conversion.
     */
    private fun inverserDevises() {
        val posSource = binding.spDeviseSource.selectedItemPosition
        val posCible = binding.spDeviseCible.selectedItemPosition

        binding.spDeviseSource.setSelection(posCible)
        binding.spDeviseCible.setSelection(posSource)

        val montantStr = binding.tvMontant.text.toString()
        val montant = montantStr.toDoubleOrNull() ?: 0.0
        if (montant > 0.0) {
            effectuerConversion(afficherToastErreur = false)
        }
    }
}