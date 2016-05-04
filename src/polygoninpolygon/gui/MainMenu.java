/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygoninpolygon.gui;

import java.awt.CheckboxMenuItem;
import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author marco
 */
public class MainMenu extends MenuBar {
    
//    private MainFrame mainFrame;
    private Menu mnuEdit;

    public MainMenu(MainFrame mainFrame) throws HeadlessException {
        mnuEdit = new Menu("Edit");
        MenuItem mniClear = new MenuItem("Clear all");
        MenuItem mniReverse = new MenuItem("Reverse");
        CheckboxMenuItem mniToggleInners = new CheckboxMenuItem("Toggle Inner Polygons", false);
        mniClear.addActionListener((ActionEvent e) -> {
            mainFrame.clearAll();
            mniToggleInners.setState(false);
        });
        mnuEdit.add(mniClear);
        mniReverse.addActionListener((ActionEvent e) -> {
            mainFrame.reverseSpin();
        });
        mnuEdit.add(mniReverse);
        mniToggleInners.addItemListener((ItemEvent e) -> {
            mainFrame.toggleInnerPolies();
        });
        mnuEdit.add(mniToggleInners);
        this.add(mnuEdit);
    }
    
}
