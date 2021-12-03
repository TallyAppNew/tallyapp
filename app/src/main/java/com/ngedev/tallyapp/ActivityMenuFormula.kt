package com.ngedev.tallyapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.ngedev.core.HelperAds
import com.ngedev.tallyapp.adapter.FormulaMenuAdapter
import com.ngedev.tallyapp.model.MenuFormulaModel
import com.ngedev.tallyapp.webview.WebViewActivity
import java.util.*


class ActivityMenuFormula : AppCompatActivity() {
    private var mAdView: AdView? = null

    var listMenu = listOf<MenuFormulaModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_formula)

        HelperAds.showAds(applicationContext, this@ActivityMenuFormula, R.string.menu_ads)

        MobileAds.initialize(
            this
        ) { }

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView?.loadAd(adRequest)

        val action = intent.action ?: ""
        initData(action)
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        initRecyclerView(action)


    }

    private fun initRecyclerView(action: String) {
        findViewById<RecyclerView>(R.id.rvMenu).apply {
            layoutManager = LinearLayoutManager(this@ActivityMenuFormula)
            adapter = FormulaMenuAdapter(listMenu) {
                openWebActivity(it.htmlTitle, action)
            }
        }
    }

    private fun initData(action: String) {
        listMenu = if (action.equals("basic_formula")) {
            generateListBasicMenu().also {
                title = "Basic Formula"
            }
        } else if (action.equals("calculations")) {
            generateCalucationList().also {
                title = "Calculations"

            }
        } else if (action.equals("drilling")) {
            generateDrilingList().also {
                title = "Drilling Fluids"
            }
        } else if (action.equals("engineering")) {
            generateEngineeringList().also {
                title = "Engineering Calculations"
            }
        } else {
            generatePressureList().also {
                title = "Pressure Control"
            }
        }
    }

    private fun openWebActivity(url: String, action: String) {
        val i = Intent(applicationContext, WebViewActivity::class.java)
        i.putExtra("url", url)
        i.putExtra("dir", action)
        startActivity(i)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun generateListBasicMenu() = listOf<MenuFormulaModel>(
        MenuFormulaModel("AnnularVelocity.html", "Anular Velocity"),
        MenuFormulaModel("BuoyancyFactor.html", "Buoyancy Factor"),
        MenuFormulaModel("CapacityFormula.html", "Capacity Formula"),
        MenuFormulaModel("ControlDrilling.html", "Control Drilling"),
        MenuFormulaModel("CostperFoot.html", "Cost per Foot"),
        MenuFormulaModel("DPDCCalculations.html", "DPDC Calculations"),
        MenuFormulaModel(
            "EquivalentCirculatingDensity(ECD).html",
            "Equivalent Circulating Density (ECD)"
        ),
        MenuFormulaModel("FormationTemperature.html", "Formation Temperature"),
        MenuFormulaModel("HydraulicHorsepower.html", "Hydraulic Horsepower"),
        MenuFormulaModel("HydrostaticPressure.html", "Hydrostatic Pressure"),
        MenuFormulaModel("HydrostaticPressureDecrease.html", "Hydrostatic Pressure Decrease"),
        MenuFormulaModel("LossofOverbalance.html", "Loss of Overbalance"),
        MenuFormulaModel("PressureGradient.html", "Pressure Gradient"),
        MenuFormulaModel("PumpOutput.html", "Pump Output"),
        MenuFormulaModel("PumpPressure.html", "Pump Pressure"),
        MenuFormulaModel("SpecificGravity.html", "Specific Gravity"),
        MenuFormulaModel("TemperatureConversion.html", "Temperature Conversion")
    )

    private fun generateCalucationList() = listOf<MenuFormulaModel>(
        generateFileReferencesWTitle("BreakCirculationPressure.html"),
        generateFileReferencesWTitle("BulkDensity.html"),
        generateFileReferencesWTitle("CementingCalculations.html"),
        generateFileReferencesWTitle("DifferentialHydrostaticPressure.html"),
        generateFileReferencesWTitle("DrillStringDesign.html"),
        generateFileReferencesWTitle("HydraulicingCasing.html"),
        generateFileReferencesWTitle("LostReturn.html"),
        generateFileReferencesWTitle("SlugCalculations.html"),
        generateFileReferencesWTitle("SpottingPillsCalculations.html"),
        generateFileReferencesWTitle("StuckPipeCalculation.html"),
        generateFileReferencesWTitle("TonmileCalculations.html"),
        MenuFormulaModel("Volumesandstrokes.html", "Volumes and Strokes"),
        generateFileReferencesWTitle("WashoutDepth.html")
    )

    private fun generateDrilingList() = listOf<MenuFormulaModel>(
        generateFileReferencesWTitle("Dilution.html"),
        MenuFormulaModel("DilutionofMudSystem.html", "Dilution of Mud System"),
        generateFileReferencesWTitle("Displacement.html"),
        MenuFormulaModel("EvaluationofCentrifuge.html", "Evaluation of Centrifuge"),
        MenuFormulaModel("EvaluationofHydrocyclone.html", "Evaluation of Hydrocyclone"),
        generateFileReferencesWTitle("IncreaseMudWeight.html"),
        generateFileReferencesWTitle("MixingFluids.html"),
        generateFileReferencesWTitle("OilBasedMudCalculations.html"),
        generateFileReferencesWTitle("SolidsAnalysis.html"),
        generateFileReferencesWTitle("SolidsFractions.html")
    )

    private fun generateEngineeringList() = listOf<MenuFormulaModel>(
        generateFileReferencesWTitle("BitNozzle.html"),
        generateFileReferencesWTitle("CriticalAnnularVelocity.html"),
        generateFileReferencesWTitle("CuttingSlipVelocity.html"),
        generateFileReferencesWTitle("d-exponent.html"),
        generateFileReferencesWTitle("DirectionalDrillingCalculations.html"),
        generateFileReferencesWTitle("ECD.html"),
        generateFileReferencesWTitle("FractureGradientDetermination.html"),
        generateFileReferencesWTitle("HydraulicsAnalysis.html"),
        generateFileReferencesWTitle("MisscelaneousEquationsCalculation.html"),
        generateFileReferencesWTitle("SurgeSwabPressures.html"),
        generateFileReferencesWTitle("TankCapacityCalculations.html")
    )

    private fun generatePressureList() = listOf<MenuFormulaModel>(
        generateFileReferencesWTitle("KickAnalysis.html"),
        generateFileReferencesWTitle("KillSheetCalculations.html"),
        generateFileReferencesWTitle("PressureAnalysis.html"),
        generateFileReferencesWTitle("StrippingSnubbingCalculations.html"),
        generateFileReferencesWTitle("SubseaConsideration.html"),
        generateFileReferencesWTitle("WorkoverOperations.html")
    )


    private fun generateFileReferencesWTitle(s: String): MenuFormulaModel {
        val titleWithoutHtml = s.replace(".html", "")
        val title = splitCamelCase(titleWithoutHtml)
        return MenuFormulaModel(s, title ?: "")
    }


    private fun splitCamelCase(s: String): String? {
        return s.replace(
            String.format(
                "%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])",
                "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"
            ).toRegex(),
            " "
        )
    }

}