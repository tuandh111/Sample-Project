/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.BieuDo;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author DELL E5470
 */
public class CurveChart extends javax.swing.JPanel {

    DecimalFormat df = new DecimalFormat("#,##0.##");
    private List<ModelLegend> legends = new ArrayList<>();
    private List<ModelChart> model = new ArrayList<>();
    private final Animator animator;
    private float animate;

    /**
     * Creates new form CurveChart
     */
    public CurveChart() {
        initComponents();
                setOpaque(false);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animate = fraction;
                repaint();
            }
        };
        animator = new Animator(800, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        blankPlotChart.getNiceScale().setMaxTicks(5);
        blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
            @Override
            public int getMaxLegend() {
                return legends.size();
            }

            @Override
            public String getLabelText(int index) {
                return model.get(index).getLabel();
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index, List<Path2D.Double> gra) {
                for (int i = 0; i < legends.size(); i++) {
                    double ys;
                    double xs;
                    double x = size.getX() + size.getWidth() / 2f;
                    if (index == 0) {
                        ys = chart.getSeriesValuesOf(0, size.getHeight()) * animate;
                        ys = size.getY() + size.getHeight() - ys;
                        xs = x - size.getWidth() / 2;
                    } else {
                        ys = chart.getSeriesValuesOf(model.get(index - 1).getValues()[i], size.getHeight()) * animate;
                        ys = size.getY() + size.getHeight() - ys;
                        xs = x - size.getWidth();
                    }
                    double s = xs + size.getWidth() / 4;
                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
                    double yy = size.getY() + size.getHeight() - seriesValues;
                    gra.get(i).append(new CubicCurve2D.Double(xs, ys, s, ys, s, yy, x, yy), true);
                    if (index == chart.getLabelCount() - 1) {
                        xs = x;
                        ys = yy;
                        s = xs + size.getWidth() / 4;
                        seriesValues = chart.getSeriesValuesOf(0, size.getHeight()) * animate;
                        yy = size.getY() + size.getHeight() - seriesValues;
                        gra.get(i).append(new CubicCurve2D.Double(xs, ys, s, ys, s, yy, x + size.getWidth() / 2, yy), true);
                    }
                }
            }

            @Override
            public void renderGraphics(Graphics2D g2, List<Path2D.Double> gra) {
                g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
                for (int i = 0; i < gra.size(); i++) {
                    Color c = legends.get(i).getColorLight();
                    g2.setPaint(new GradientPaint(0, 0, legends.get(i).getColor(), 0, getHeight(), new Color(c.getRed(), c.getGreen(), c.getBlue(), 0)));
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fill(gra.get(i));
                    g2.setPaint(new GradientPaint(0, 0, legends.get(i).getColor(), getWidth(), 0, legends.get(i).getColorLight()));
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    g2.draw(gra.get(i));
                }
            }

            @Override
            public boolean mouseMoving(BlankPlotChart chart, MouseEvent evt, Graphics2D g2, SeriesSize size, int index) {
                return false;
            }
        });
    }
  public void addLegend(String name, Color color, Color color1) {
        ModelLegend data = new ModelLegend(name, color, color1);
        legends.add(data);
        panelLegend.add(new LegendItem(data));
        panelLegend.repaint();
        panelLegend.revalidate();
    }

    public void addData(ModelChart data) {
        model.add(data);
        blankPlotChart.setLabelCount(model.size());
        double max = data.getMaxValues();
        if (max > blankPlotChart.getMaxValues()) {
            blankPlotChart.setMaxValues(max);
        }
    }

    public void clear() {
        animate = 0;
        blankPlotChart.setLabelCount(0);
        model.clear();
        repaint();
    }

    public void start() {
        if (!animator.isRunning()) {
            animator.start();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blankPlotChart = new com.tuandhpc05076.BieuDo.BlankPlotChart();
        panelLegend = new javax.swing.JPanel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(blankPlotChart, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(blankPlotChart, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.BieuDo.BlankPlotChart blankPlotChart;
    private javax.swing.JPanel panelLegend;
    // End of variables declaration//GEN-END:variables
}
