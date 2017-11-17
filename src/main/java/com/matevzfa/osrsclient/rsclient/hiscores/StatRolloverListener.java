/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matevzfa.osrsclient.rsclient.hiscores;

/**
 *
 * @author ben
 */
public interface StatRolloverListener {

    void onRolledOver(LevelScorePanel skillPanel);

    void onRolledOff(LevelScorePanel skillPanel);



}
