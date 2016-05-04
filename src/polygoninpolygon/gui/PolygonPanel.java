/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygoninpolygon.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import polygoninpolygon.model.MyPolygon;

/**
 *
 * @author marco
 */
public class PolygonPanel extends JPanel {

    private final MainFrame mainFrame;
    private final ClickInputHandler clickInput;
    private final int shiftAmount = 10;
    private final boolean useColors = true;
    private final Color[] colors = new Color[]{Color.red, Color.black};

    public PolygonPanel(MainFrame mainFrame) {
        super(new BorderLayout());
        this.mainFrame = mainFrame;
        clickInput = new ClickInputHandler();
        this.addMouseListener(clickInput);
        this.setPreferredSize(new Dimension(600, 400));
        this.setMinimumSize(new Dimension(600, 400));
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 5));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        MyPolygon poly = mainFrame.thePolygon;
        g2.setStroke(new BasicStroke(2));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawPolygon(g2, poly);
    }

    private void drawPolygon(Graphics2D g2, MyPolygon poly) {
        Point[] points = poly.getPoints();
        int l = points.length;
        if (l == 1) {
            Point p = points[0];
            g2.drawLine(p.x, p.y, p.x, p.y);
        }
        if (l > 1) {
            if (useColors && ((BasicStroke)g2.getStroke()).getLineWidth() != 2) {
                for (int i = 0; i < l - 1; i++) {
                    g2.setColor(colors[i % 2]);
                    g2.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
                }
            } else {
                for (int i = 0; i < l - 1; i++) {
                    g2.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
                }
            }
            if (!mainFrame.showInnerPolies) {
                g2.setStroke(new BasicStroke(1));
            }
            Point p0 = points[0];
            Point pl = points[l - 1];
            if (useColors && ((BasicStroke)g2.getStroke()).getLineWidth() != 2) {
                g2.setColor(colors[(l - 1) % 2]);
            }
            g2.drawLine(pl.x, pl.y, p0.x, p0.y);
        }
        // Draw inner polygons
//        if (mainFrame.showInnerPolies && points.length > 2 && poly.isConvex()) {
        if (mainFrame.showInnerPolies && l > 2) {
            g2.setStroke(new BasicStroke(1));
            MyPolygon innerPolygon = poly.getInnerPolygon(shiftAmount);
            if (innerPolygon != null) {
                drawPolygon(g2, innerPolygon);
            }
        }
    }

    private class ClickInputHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!mainFrame.showInnerPolies) {
                mainFrame.thePolygon.addPoint(e.getPoint());
                PolygonPanel.this.repaint();
            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }

    }

}
