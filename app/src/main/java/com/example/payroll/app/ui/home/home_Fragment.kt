package com.example.payroll.app.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.payroll.R
import com.example.payroll.app.core.PAYROLL
import com.example.payroll.app.core.formatDate
import com.example.payroll.databinding.FragmentHomeBinding
import com.example.payroll.domain.model.EmployeeModel

import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    lateinit var employeeModel: EmployeeModel
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val payroll: List<EmployeeModel> =
            (arguments?.get(PAYROLL)) as List<EmployeeModel>
        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()
        }
        employeeModel = payroll.first()

        setUserDate(payroll.first())
        showPieChart(
            viewModel.getEmployeeTotalAllowances(employeeModel)!!,
            viewModel.getEmployeeTotalDeductions(employeeModel)!!
        )
        setupPayrollTable(viewModel.getTableData(employeeModel))

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showPieChart(allowances: Double, deduction: Double) {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = ""
        //initializing data
        val typeAmountMap: MutableMap<String, Float> = HashMap()
        typeAmountMap[getString(
            R.string.allowances,
            allowances.toString(),
            employeeModel.currency
        )] =
            allowances.toFloat()


        typeAmountMap[getString(
            R.string.deductions,
            deduction.toString(),
            employeeModel.currency
        )] =
            deduction.toFloat()


        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#556080"))
        colors.add(Color.parseColor("#F0C419"))


        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }
        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = 12f
        pieDataSet.valueTextColor = resources.getColor(android.R.color.white)
        pieDataSet.valueFormatter = PercentFormatter()
        //providing color list for coloring different entries
        pieDataSet.colors = colors
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true)
        binding.pie.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            holeRadius = 0f;
            transparentCircleRadius = 0f;
            setDrawEntryLabels(false)
            legend.apply {
                direction = Legend.LegendDirection.RIGHT_TO_LEFT
                orientation = Legend.LegendOrientation.VERTICAL
                textSize = 16f
                formSize = 16f
                typeface =
                    Typeface.create(resources.getFont(R.font.almarai_regular), Typeface.NORMAL)
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                setDrawInside(false);
                xEntrySpace = 7f;
                yEntrySpace = 10f;
                yOffset = -15f;

            }

            data = pieData
            invalidate()
        }
    }

    private fun setupPayrollTable(data: HashMap<String, String>) {
        data.onEachIndexed { index, entry ->
            val row = TableRow(requireContext()).apply {
                val text1 = TextView(requireContext()).apply {
                    textSize = 20f
                    setPadding(8)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    text = (index + 1).toString()
                }
                val text2 = TextView(requireContext()).apply {
                    textSize = 20f
                    setPadding(8)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    text = entry.key
                }
                val text3 = TextView(requireContext()).apply {
                    textSize = 20f
                    setPadding(8)
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    text = entry.value + employeeModel.currency

                }
                addView(text3)
                addView(text2)
                addView(text1)
                when {
                    (index + 1) % 2 == 0 -> setBackgroundColor(resources.getColor(R.color.light_blue))

                    (index + 1) % 3 == 0 -> setBackgroundColor(resources.getColor(R.color.pink))

                    else -> setBackgroundColor(resources.getColor(R.color.white))


                }
            }
            binding.tableView.addView(row)
        }
    }

    private fun setUserDate(employeeModel: EmployeeModel) {
        binding.employeeName.text = employeeModel.employeeName
        binding.employeeTitle.text = employeeModel.JobTitle
        binding.date.text = formatDate(employeeModel.Date!!)
        binding.netSalary.text = getSpannableText(
            getString(
                R.string.net_salary,
                viewModel.getNetSalary(employeeModel).toString(),
                employeeModel.currency
            ), ':', Color.BLUE
        )
        binding.total.text = getSpannableText(
            getString(
                R.string.total,
                viewModel.getNetSalary(employeeModel).toString(),
                employeeModel.currency
            ), separator = employeeModel.currency?.first(), color = resources.getColor(R.color.red)
        )

    }

    private fun getSpannableText(
        text: String,
        separator: Char? = null,
        color: Int
    ): Spannable {


        val wordtoSpan: Spannable =
            SpannableString(text)
        wordtoSpan.setSpan(
            ForegroundColorSpan(color),
            0, text.indexOfFirst {
                it == separator
            } + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return wordtoSpan
    }

}