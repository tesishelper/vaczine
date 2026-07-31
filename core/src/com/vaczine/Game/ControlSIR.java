package com.vaczine.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.vaczine.Actores.Vaczinito;
import com.vaczine.Pantallas.ModeloSIR;

import javax.swing.border.Border;


public class ControlSIR extends InputAdapter {


    Camera cam;
   ModeloSIR ms;
    boolean verlinea;
    Vector3 vector3;

    public ControlSIR(ModeloSIR ms, Camera cam){

      vector3 = new Vector3();
        this.cam=cam;

        this.ms = ms;
    }

    @Override
    public boolean keyUp(int keycode) {




        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {


        return super.keyTyped(character);
    }

    @Override
    public boolean keyDown(int keycode) {




        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        vector3.set(screenX,screenY,0);
        Vector3 ori=cam.unproject(vector3);

        if(ms.getM().getPlay()!=0 && ms.getM().getPlay()!=2 && ms.getM().getPlay()!=3 && ori.x>ms.getOrigenX() && ori.x< ms.getOrigenX()+700 && ori.y> ms.getOrigenY()){

            ms.getPosLinea().x= ori.x;
            ms.getPosLinea().y= ori.y;
            ms.setVerLinea(true);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {


        ms.setVerLinea(false);


        return true;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        vector3.set(screenX,screenY,0);
        Vector3 ori=cam.unproject(vector3);
        if(ms.getM().getPlay()!=0 && ms.getM().getPlay()!=2 && ms.getM().getPlay()!=3 && ori.x>ms.getOrigenX() && ori.x< ms.getOrigenX()+700 && ori.y> ms.getOrigenY()){

            ms.getPosLinea().x= ori.x;
            ms.getPosLinea().y= ori.y;

        }

        return true;
    }



}
