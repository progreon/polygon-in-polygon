/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygoninpolygon;

import polygoninpolygon.model.MyPolygon;
import java.awt.Point;
import javax.swing.JFrame;
import polygoninpolygon.gui.MainFrame;

/**
 *
 * @author marco
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mf.pack();
//        mf.setSize(600, 400);
        mf.setLocationRelativeTo(null);
        mf.setTitle("PolygonInPolygon");
        mf.setVisible(true);
    }

}
