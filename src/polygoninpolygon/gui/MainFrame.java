/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygoninpolygon.gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import polygoninpolygon.model.MyPolygon;

/**
 *
 * @author marco
 */
public class MainFrame extends JFrame {
    
    MyPolygon thePolygon;
    PolygonPanel polyPanel;
    MainMenu mainMenu;
    boolean showInnerPolies;

    public MainFrame() throws HeadlessException {
        init();
    }
    
    private void init() {
        thePolygon = new MyPolygon();
        polyPanel = new PolygonPanel(this);
        JPanel panel = new JPanel(new BorderLayout());
        this.setContentPane(panel);
        panel.add(polyPanel, BorderLayout.CENTER);
        mainMenu = new MainMenu(this);
        this.setMenuBar(mainMenu);
        showInnerPolies = false;
    }
    
    public void clearAll() {
        showInnerPolies = false;
        thePolygon.reset();
        polyPanel.repaint();
    }
    
    public void reverseSpin() {
        thePolygon.reverse();
        polyPanel.repaint();
    }
    
    public void toggleInnerPolies() {
        showInnerPolies = !showInnerPolies;
        polyPanel.repaint();
    }
    
}
