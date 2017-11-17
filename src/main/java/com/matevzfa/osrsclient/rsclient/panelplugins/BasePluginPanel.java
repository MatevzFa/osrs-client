/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matevzfa.osrsclient.rsclient.panelplugins;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author ben
 */
public class BasePluginPanel extends JPanel {
	
	
	
	public BasePluginPanel(){
		
		super(new MigLayout());
		this.add(new CombatCalcPanel(), "height 20%, width 100%");
	}
}
