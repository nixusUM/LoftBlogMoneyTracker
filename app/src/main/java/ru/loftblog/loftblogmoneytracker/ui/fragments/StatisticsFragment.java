package ru.loftblog.loftblogmoneytracker.ui.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.database.models.Categories;
import ru.loftblog.loftblogmoneytracker.database.models.Expenses;

@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment{

    @ViewById(R.id.mainLayot)
    FrameLayout mainLayot;

    private PieChart mChart;

    private ArrayList<Entry> yData;
    private ArrayList<String> xData ;

    @Override
    public void onResume() {
        super.onResume();
            insertData();
            drawPieChart();
        }


    private void drawPieChart(){
        getActivity().setTitle(getString(R.string.statistics));
        mChart = new PieChart(getContext());
        mainLayot.addView(mChart);
        mainLayot.setBackgroundColor(getResources().getColor(R.color.backGroundDark));
        mChart.setUsePercentValues(true);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDescription(getString(R.string.statistics));
        mChart.animateXY(1500, 1500);
        mChart.setDescriptionTextSize(15f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setTransparentCircleAlpha(90);
        mChart.setHoleRadius(55f);
        mChart.setTransparentCircleRadius(65f);
        mChart.setRotation(0);
        mChart.setRotationEnabled(true);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                if (e == null)
                    return;
                    Toast.makeText(getContext(), xData.get(e.getXIndex()) + ": " +
                            yData.get(e.getXIndex()).getVal() + " " + getString(R.string.rMoney) , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData();
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setTextSize(12f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void insertData() {

        List<Categories> categories = new Select().from(Categories.class).execute();

        if (categories != null) {
            xData = new ArrayList<>();
            yData = new ArrayList<>();

            for (Categories category : categories) {
                float sum = 0f;
                for (Expenses expense : category.expenses()) {
                    sum += expense.getPrice();
                }
                if (sum != 0f) {
                    xData.add(category.title);
                    yData.add(new Entry(sum, xData.size() - 1));
                }
            }
        }
    }

    private void addData() {

        PieDataSet dataset = new PieDataSet(yData, "");
        dataset.setSliceSpace(5);
        dataset.setSelectionShift(8);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataset.setColors(colors);
        PieData data = new PieData(xData, dataset);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }
}

