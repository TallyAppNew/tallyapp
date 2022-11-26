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
import com.ngedev.core.HelperAds
import com.ngedev.tallyapp.adapter.SubMenuAdapter
import com.ngedev.tallyapp.model.SubMenuModel
import com.ngedev.tallyapp.webview.WebViewActivity
import java.util.*


class ActivitySubMenu : AppCompatActivity() {
    private var mAdView: AdView? = null

    var listMenu = listOf<SubMenuModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submenu)

//        HelperAds.showAds(applicationContext, this@ActivitySubMenu, R.string.menu_ads)

//        MobileAds.initialize(
//            this
//        ) { }
//
//        mAdView = findViewById(R.id.adView)
//        val adRequest = AdRequest.Builder().build()
//        mAdView?.loadAd(adRequest)

        val action = intent.action ?: ""
        initData(action)
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        initRecyclerView(action)


    }

    private fun initRecyclerView(action: String) {
        findViewById<RecyclerView>(R.id.rvMenu).apply {
            layoutManager = LinearLayoutManager(this@ActivitySubMenu)
            adapter = SubMenuAdapter(listMenu) {
                openWebActivity(it.htmlTitle, action)
            }
        }
    }

    private fun initData(action: String) {
        listMenu = when(action) {
            "basic_formula" -> {
                generateListBasicMenu().also {
                    title = "Basic Formula"
                }
            }
            "calculations" -> {
                generateCalculationList().also {
                    title = "Calculations"

                }
            }
            "drilling" -> {
                generateDrillingList().also {
                    title = "Drilling Fluids"
                }
            }
            "engineering" -> {
                generateEngineeringList().also {
                    title = "Engineering Calculations"
                }
            }
            "pressure" -> {
                generatePressureList().also {
                    title = "Pressure Control"
                }
            }
//            "drilling_mud" -> {
//                generateDrillingMudList().also {
//                    title = "Drilling Mud"
//                }
//            }
            "h2s" -> {
                generateH2S().also {
                    title = "H2S"
                }
            }
            else -> {
                generateDrillingRigComponents().also {
                    title = "Drilling Rig Components"
                }
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


    private fun generateListBasicMenu() = listOf(
        SubMenuModel("AnnularVelocity.html", "Anular Velocity"),
        SubMenuModel("BuoyancyFactor.html", "Buoyancy Factor"),
        SubMenuModel("CapacityFormula.html", "Capacity Formula"),
        SubMenuModel("ControlDrilling.html", "Control Drilling"),
        SubMenuModel("CostperFoot.html", "Cost per Foot"),
        SubMenuModel("DPDCCalculations.html", "DPDC Calculations"),
        SubMenuModel(
            "EquivalentCirculatingDensity(ECD).html",
            "Equivalent Circulating Density (ECD)"
        ),
        SubMenuModel("FormationTemperature.html", "Formation Temperature"),
        SubMenuModel("HydraulicHorsepower.html", "Hydraulic Horsepower"),
        SubMenuModel("HydrostaticPressure.html", "Hydrostatic Pressure"),
        SubMenuModel("HydrostaticPressureDecrease.html", "Hydrostatic Pressure Decrease"),
        SubMenuModel("LossofOverbalance.html", "Loss of Overbalance"),
        SubMenuModel("PressureGradient.html", "Pressure Gradient"),
        SubMenuModel("PumpOutput.html", "Pump Output"),
        SubMenuModel("PumpPressure.html", "Pump Pressure"),
        SubMenuModel("SpecificGravity.html", "Specific Gravity"),
        SubMenuModel("TemperatureConversion.html", "Temperature Conversion")
    )

    private fun generateCalculationList() = listOf(
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
        SubMenuModel("Volumesandstrokes.html", "Volumes and Strokes"),
        generateFileReferencesWTitle("WashoutDepth.html")
    )

    private fun generateDrillingList() = listOf(
        generateFileReferencesWTitle("Dilution.html"),
        SubMenuModel("DilutionofMudSystem.html", "Dilution of Mud System"),
        generateFileReferencesWTitle("Displacement.html"),
        SubMenuModel("EvaluationofCentrifuge.html", "Evaluation of Centrifuge"),
        SubMenuModel("EvaluationofHydrocyclone.html", "Evaluation of Hydrocyclone"),
        generateFileReferencesWTitle("IncreaseMudWeight.html"),
        generateFileReferencesWTitle("MixingFluids.html"),
        generateFileReferencesWTitle("OilBasedMudCalculations.html"),
        generateFileReferencesWTitle("SolidsAnalysis.html"),
        generateFileReferencesWTitle("SolidsFractions.html")
    )

    private fun generateEngineeringList() = listOf(
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

    private fun generatePressureList() = listOf(
        generateFileReferencesWTitle("KickAnalysis.html"),
        generateFileReferencesWTitle("KillSheetCalculations.html"),
        generateFileReferencesWTitle("PressureAnalysis.html"),
        generateFileReferencesWTitle("StrippingSnubbingCalculations.html"),
        generateFileReferencesWTitle("SubseaConsideration.html"),
        generateFileReferencesWTitle("WorkoverOperations.html")
    )

    private fun generateDrillingRigComponents() = listOf(
        SubMenuModel("power_and_prime_movers.html", "Power And Prime Movers"),
        SubMenuModel("hoisting_component.html", "Hoisting Component"),
        SubMenuModel("rotating_component.html", "Rotating Component"),
        SubMenuModel("circulating_component.html", "Circulating Component"),
        SubMenuModel("well_control_component.html", "Well Control Component"),
        SubMenuModel("tubular_and_tubular_handling_equipment.html", "Tubular and Tubular Handling Equipment"),
        SubMenuModel("bit.html", "Bit"),
        SubMenuModel("fishing_tools.html", "Fishing Tools"),
    )

    private fun generateH2S() = listOf(
        SubMenuModel("what_is_h2s.html", "What is H2S"),
        SubMenuModel("physical_properties_of_h2s.html", "Physical Properties Of H2S"),
        SubMenuModel("occupational_exposure_limits_oel_toxicity_levels_and_scale.html", "Occupational Exposure Limits OEL Toxicity Levels And Scale"),
        SubMenuModel("h2s_detection_system.html", "H2S Detection System"),
        SubMenuModel("protection_system.html", "Protection System"),
        SubMenuModel("emergency_response_procedure_and_strategy.html", "Emergency Response Procedure And Strategy"),
        SubMenuModel("emergency_rescue_technique.html", "Emergency Rescue Technique")
    )


    private fun generateFileReferencesWTitle(s: String): SubMenuModel {
        val titleWithoutHtml = s.replace(".html", "")
        val title = splitCamelCase(titleWithoutHtml)
        return SubMenuModel(s, title ?: "")
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