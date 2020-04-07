package com.example.lab22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Arrays;
import java.util.Random;

public class Main3Activity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;
    private LineGraphSeries<DataPoint> series3;
    private LineGraphSeries<DataPoint> series4;
    private LineGraphSeries<DataPoint> series5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        GraphView graph = (GraphView) findViewById(R.id.graph2);
        GraphView graph2 = (GraphView) findViewById(R.id.graph3);
        GraphView graph3 = (GraphView) findViewById(R.id.graph4);
        GraphView graph4 = (GraphView) findViewById(R.id.graph5);
        GraphView graph5 = (GraphView) findViewById(R.id.graph6);
        series = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();
        series3 = new LineGraphSeries<>();
        series4 = new LineGraphSeries<>();
        series5 = new LineGraphSeries<>();
        int n = 14, W = 2000 / n, N = 256;
        float A, f;
        float[] Fre = new float[N];
        float[] Fim = new float[N];
        float[] Fre2 = new float[N];
        float[] Fim2 = new float[N];
        float[] x = new float[N];
        float[] time1 = new float[N];
        float[] time2 = new float[N];
        float[] time3 = new float[N];
        float[] t1 = new float[N];
        float[] t2 = new float[N];
            for (int i = 0; i < N; i++) {
                x[i] = 0;
                time1[i] = 0;
                time2[i] = 0;
                time3[i] = 0;
            }
            for (int t = 0, w = W; t < n; t++, w += W) {
                for (int i = 0; i < N; i++) {
                    f = ((new Random()).nextFloat());
                    A = ((new Random()).nextFloat());
                    x[i] += A * Math.sin(w * i + f);
                }
            }
        for (int i = 0; i < N; i++) {
            time1[i] = 0;
            time2[i] = 0;
            time3[i] = 0;
            t1[i] = 0;
            t2[i] = 0;
        }

            for (int t = 0, w = W; t < n; t++, w += W) {
                for (int i = 0; i < N; i++) {
                    f = ((new Random()).nextFloat());
                    A = ((new Random()).nextFloat());
                    x[i] += A * Math.sin(w * i + f);
                }
            }

        double[] sinuses = new double[N*N];
        double[] cosisis = new double[N*N];
        for (int i = 0; i < N*N; i++) {
            cosisis[i] = Math.cos(2 * Math.PI * i / N);
            sinuses[i] = Math.sin(2 * Math.PI * i / N);
        }
        long start2 = System.nanoTime();
            for (int p = 0; p < N - 1; p++) {
                Fre[p] = Fim[p] = 0;
                for (int k = 0; k < N - 1; k++) {
                    Fre[p] += x[k] * cosisis[p*k];
                    Fim[p] -= x[k] * sinuses[p*k];
                    long end2 = System.nanoTime();
                    if (k == 0) {
                    time2[k] = end2 - start2;
                    } else {
                        time2[k] = time2[k - 1] + end2 - start2;
                    }
                    }
                    series2.appendData(new DataPoint(p, Math.sqrt(Fre[p] * Fre[p] + Fim[p] * Fim[p])), true, N);
            }
        long end2 = System.nanoTime();
        long start1 = System.nanoTime();
            for (int p = 0; p < N - 1; p++) {
                Fre[p] = Fim[p] = 0;
                for (int k = 0; k < N - 1; k++) {
                    Fre[p] += x[k] * Math.cos(2 * Math.PI * p * k / N);
                    Fim[p] -= x[k] * Math.sin(2 * Math.PI * p * k / N);
                    long end1 = System.nanoTime();
                    if (k == 0) {
                        time1[k] = end1 - start1;
                    } else {
                        time1[k] = time1[k-1] + end1 - start1;
                    }
                }
                    series.appendData(new DataPoint(p, Math.sqrt(Fre[p] * Fre[p] + Fim[p] * Fim[p])), true, N);
            }
        long start3 = System.nanoTime();
        for (int p = 0; p < N - 1; p++) {
            Fre[p] = Fim[p] = 0;
            for (int k = 0; k < N / 2 - 1; k++) {
                Fre2[p] += x[2 * k + 1] * Math.cos(p * (2 * k + 1));
                Fim2[p] -= x[2 * k + 1] * Math.sin(p * (2 * k + 1));
                long end3 = System.nanoTime();
                if (k == 0) {
                    time3[k] = end3 - start3;
                } else {
                    time3[k] = time3[k-1] + end3 - start3;
                }
            }
            series3.appendData(new DataPoint(p, Math.sqrt(Fre2[p] * Fre2[p] + Fim2[p] * Fim2[p])), true, N);
        }

        for (int p = 0; p < N - 1; p++) {
            t1[p] = time1[p] / time2[p];
            t1[p] = time1[p] / time3[p];
            series4.appendData(new DataPoint(p, t1[p]), true, N);
            series5.appendData(new DataPoint(p, t1[p]), true, N);
        }
        System.out.println(Arrays.toString(time1));
        System.out.println(Arrays.toString(time2));
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-25);
        graph.getViewport().setMaxY(80);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(250);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        graph.addSeries(series);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(-25);
        graph2.getViewport().setMaxY(80);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(250);
        graph2.getViewport().setScrollable(true);
        graph2.getViewport().setScalable(true);
        graph2.addSeries(series2);
        graph3.getViewport().setYAxisBoundsManual(true);
        graph3.getViewport().setMinY(-25);
        graph3.getViewport().setMaxY(80);
        graph3.getViewport().setXAxisBoundsManual(true);
        graph3.getViewport().setMinX(0);
        graph3.getViewport().setMaxX(250);
        graph3.getViewport().setScrollable(true);
        graph3.getViewport().setScalable(true);
        graph3.addSeries(series3);
        graph4.getViewport().setYAxisBoundsManual(true);
        graph4.getViewport().setMinY(-25);
        graph4.getViewport().setMaxY(80);
        graph4.getViewport().setXAxisBoundsManual(true);
        graph4.getViewport().setMinX(0);
        graph4.getViewport().setMaxX(250);
        graph4.getViewport().setScrollable(true);
        graph4.getViewport().setScalable(true);
        graph4.addSeries(series4);
        graph5.getViewport().setYAxisBoundsManual(true);
        graph5.getViewport().setMinY(-25);
        graph5.getViewport().setMaxY(80);
        graph5.getViewport().setXAxisBoundsManual(true);
        graph5.getViewport().setMinX(0);
        graph5.getViewport().setMaxX(250);
        graph5.getViewport().setScrollable(true);
        graph5.getViewport().setScalable(true);
        graph5.addSeries(series5);
    }
}
