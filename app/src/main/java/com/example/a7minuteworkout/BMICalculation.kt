package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_b_m_i_calculation.*
import kotlinx.android.synthetic.main.activity_main.*

class BMICalculation : AppCompatActivity() {
    val METRIC_UNITS_VIEW="METRIC_UNITS_VIEW"
    val US_UNITS_VIEW="US_UNITS_VIEW"
    var currentVisibleView:String=METRIC_UNITS_VIEW
    var idi=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i_calculation)
        setSupportActionBar(toolbar_bmi_activity)
       val actionBar=supportActionBar
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="Calculate BMI"
        }
toolbar_bmi_activity.setNavigationOnClickListener {
    onBackPressed()
}
        makeVisibleMetricsUnitsView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->

            if(checkedId==R.id.rbMetricUnits)
            {
                makeVisibleMetricsUnitsView()
                    calculateresult.setOnClickListener {
                        llDisplayBMIResult.visibility=View.VISIBLE
                        val h:Float=height.text.toString().toFloat()
                        val w:Float=weight.text.toString().toFloat()
                        val r:Float=cal(h,w)
                        result.text=r.toString()
                        if(r>=25)
                        {
                            range.text="HIGH"
                            remark.text="Your BMI is higher than the normal range."
                        }
                        else if(r<18.9)
                        {
                            range.text="LOW"
                            remark.text="Your BMI is lower than the normal range "
                        }
                        else
                        {
                            range.text="NORMAL"
                            remark.text="Congratulations, Your BMI is in the normal range "
                        }
                        idi++
                    }


            }
            else
            {
                makeVisibleUsUnitsView()
                calculateresult.setOnClickListener {
                    llDisplayBMIResult.visibility=View.VISIBLE

                    val f:Float=etUsUnitHeightFeet.text.toString().toFloat()
                    val i:Float=etUsUnitHeightInch.text.toString().toFloat()
                    val w:Float=etUsUnitWeight.text.toString().toFloat()
                    val r:Float=calus(f,i,w)
                    result.text=r.toString()
                    if(r>=25)
                    {
                        range.text="HIGH"
                        remark.text="Your BMI is higher than the normal range."
                    }
                    else if(r<18.9)
                    {
                        range.text="LOW"
                        remark.text="Your BMI is lower than the normal range "
                    }
                    else
                    {
                        range.text="NORMAL"
                        remark.text="Congratulations, Your BMI is in the normal range "
                    }
                    idi++
                }
            }
        }
        if(idi==1)
        {
            calculateresult.setOnClickListener {
                llDisplayBMIResult.visibility=View.VISIBLE
                val h:Float=height.text.toString().toFloat()
                val w:Float=weight.text.toString().toFloat()
                val r:Float=cal(h,w)
                result.text=r.toString()
                if(r>=25)
                {
                    range.text="HIGH"
                    remark.text="Your BMI is higher than the normal range."
                }
                else if(r<18.9)
                {
                    range.text="LOW"
                    remark.text="Your BMI is lower than the normal range "
                }
                else
                {
                    range.text="NORMAL"
                    remark.text="Congratulations, Your BMI is in the normal range "
                }
                idi++
            }
        }







//        TO CREATE AN ACTIONBAR
    }
    private fun makeVisibleMetricsUnitsView()
    {
        currentVisibleView=METRIC_UNITS_VIEW
        tilMetricUnitHeight.visibility= View.VISIBLE
        tilMetricUnitWeight.visibility=View.VISIBLE
        weight.text!!.clear()
        height.text!!.clear()



        tilUsUnitWeight.visibility=View.GONE
        llUsUnitsHeight.visibility=View.GONE
        llDisplayBMIResult.visibility=View.INVISIBLE

    }
    private fun makeVisibleUsUnitsView()
    {
        currentVisibleView=US_UNITS_VIEW
        tilMetricUnitHeight.visibility= View.GONE
        tilMetricUnitWeight.visibility=View.GONE
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()
        etUsUnitWeight.text!!.clear()

        tilUsUnitWeight.visibility=View.VISIBLE
        llUsUnitsHeight.visibility=View.VISIBLE
        llDisplayBMIResult.visibility=View.INVISIBLE


    }
    private fun validateMetricUnits():Boolean{
        var isvalid=true
        if(weight.text.toString().isEmpty())
        {
            isvalid=false
        }
        else if(height.text.toString().isEmpty())
        {
            isvalid=false
        }
        return isvalid
    }
    private fun cal(height:Float,weight:Float):Float
    {
        val res:Float=((weight/height)/height)*10000
        return res
    }
    private fun calus(feet:Float,inches:Float, weight:Float):Float
    {
        val d:Float=feet*12+inches
        val res:Float=((weight/d)/d)*703
        return res
    }

}
