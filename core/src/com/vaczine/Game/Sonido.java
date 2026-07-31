/*Copyright 2019 Adolfo R. Zurita*/

/*This file is part of Vaczine.

 Vaczine is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Vaczine is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with EvoluZion.  If not, see <http://www.gnu.org/licenses/>.*/


package com.vaczine.Game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class Sonido {


    protected Sound buttonClick, plop, laser,ok_bell,ambient, time;



    public Sonido(){

        buttonClick= Gdx.audio.newSound(Gdx.files.internal("bClick.mp3"));
       // buttonClick= Gdx.audio.newSound(Gdx.files.internal("bip.mp3"));
       // buttonClick= Gdx.audio.newSound(Gdx.files.internal("mouseclick.mp3"));
      //  buttonClick= Gdx.audio.newSound(Gdx.files.internal("drumsticks.wav"));
        time = Gdx.audio.newSound(Gdx.files.internal("drumsticks.wav"));
        plop = Gdx.audio.newSound(Gdx.files.internal("plop.mp3"));
        laser= Gdx.audio.newSound(Gdx.files.internal("Laser Shot.wav"));
        ok_bell= Gdx.audio.newSound(Gdx.files.internal("ok_bell.wav"));
       // ambient = Gdx.audio.newSound(Gdx.files.internal("ambient.mp3"));

    }




    public void dispose() {


        buttonClick.dispose();
        plop.dispose();
        laser.dispose();
        ok_bell.dispose();
       // ambient.dispose();
        time.dispose();
    }




    public Sound getButtonClick() {
        return buttonClick;
    }




    public void setButtonClick(Sound buttonClick) {
        this.buttonClick = buttonClick;
    }




    public Sound getPlop() {
        return plop;
    }




    public void setPlop(Sound plop) {
        this.plop = plop;
    }

    public Sound getLaser() {
        return laser;
    }

    public void setLaser(Sound laser) {
        this.laser = laser;
    }

    public Sound getOk_bell() {
        return ok_bell;
    }

    public void setOk_bell(Sound ok_bell) {
        this.ok_bell = ok_bell;
    }

    public Sound getAmbient() {
        return ambient;
    }

    public void setAmbient(Sound ambient) {
        this.ambient = ambient;
    }

    public Sound getTime() {
        return time;
    }

    public void setTime(Sound time) {
        this.time = time;
    }
}

