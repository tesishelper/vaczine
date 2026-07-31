package com.vaczine.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.vaczine.Actores.Vaczinito;

import javax.swing.border.Border;


public class Controles extends InputAdapter {

   Vaczinito vz;
   Rectangle rc;
    Camera cam;
    Vector3 vector3;

    public Controles(Vaczinito vz, Rectangle rc,  Camera cam){

        this.vz=vz;
        this.cam=cam;
        this.rc=rc;

        vector3 = new Vector3();

    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode==Input.Keys.LEFT){

            vz.setDireccionX(0);

        }
        if(keycode==Input.Keys.RIGHT){

            vz.setDireccionX(0);}

        if(keycode==Input.Keys.UP){

            vz.setDireccionY(0); }

        if(keycode==Input.Keys.DOWN){

            vz.setDireccionY(0); }


        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {


        return super.keyTyped(character);
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode== Input.Keys.A){

            vz.vacunar();
        }

        if(keycode==Input.Keys.LEFT){

            vz.setDireccionX(-20);

        }
        if(keycode==Input.Keys.RIGHT){

            vz.setDireccionX(20);}

        if(keycode==Input.Keys.UP){

            vz.setDireccionY(20); }

        if(keycode==Input.Keys.DOWN){

            vz.setDireccionY(-20); }


        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {



        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {


        rc.setPosition(159,120);


        return true;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        vector3.set(screenX,screenY,0);
        Vector3 ori=cam.unproject(vector3);
     if(ori.x>60 && ori.x <260 && ori.y<220 && ori.y>20) {

         rc.setPosition(ori.x - rc.width/2, ori.y- rc.height / 2);

     }
        return true;
    }



}
