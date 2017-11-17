/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matevzfa.osrsclient.rsclient.coregui;

import com.matevzfa.osrsclient.rsclient.market.*;

/**
 *
 * @author ben
 */
public interface ItemListingRolloverListener {

    void onRolledOver(ItemResultPanel itemPanel);

    void onRolledOff(ItemResultPanel itemPanel);
    
    void onClicked(ItemResultPanel itemPanel);

}
