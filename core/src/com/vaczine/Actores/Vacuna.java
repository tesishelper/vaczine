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

package com.vaczine.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaczine.Game.Mundo;

public class Vacuna{

    Mundo m;
    private Texture t_vacuna;
    private Sprite imagen;
    private ShapeRenderer caja;
    int ancho = 7;
    int alto = 7;
    int speed;
    Vector2 posicion;
    Vector2 direccion;
    Rectangle borde;
    boolean fire = false;
    private int sentido = 1;

    public Vacuna(Mundo m) {

        this.m = m;

        caja = new ShapeRenderer();

        this.posicion = new Vector2();

        direccion = new Vector2();
        direccion.y = 0;
        direccion.x = -1;
        speed = 30;


        ancho =  40;
        alto = 12;

        t_vacuna = m.getT_vacuna();
        imagen = new Sprite(t_vacuna);
        imagen.setPosition(this.posicion.x, this.posicion.y);
        imagen.setSize(ancho, alto);

        borde = new Rectangle();
        borde.height = alto*2;
        borde.width = ancho;
        borde.x = posicion.x;
        borde.y = posicion.y;

    }

    public void verObjeto(SpriteBatch sb) {
        if (fire == true) {
            sb.begin();
            imagen.draw(sb);
            sb.end();
        }
     //  caja.begin(ShapeType.Line);
     //  caja.setColor(Color.CYAN);
     //  caja.rect(borde.x,borde.y,borde.width, borde.height);
     //  caja.end();

    }



    public void update() {

        if (fire==true){ //si se lanzo la vacuna
        posicion.add(Gdx.graphics.getDeltaTime() * (direccion.x*512*sentido) ,
                Gdx.graphics.getDeltaTime() * (direccion.y) );

        imagen.setPosition(posicion.x, posicion.y);
        borde.x = posicion.x;
        borde.y = posicion.y-borde.getHeight()/3;

        //si la vacuna sale de pantalla vuelve a origen
        if (posicion.x < (0 - ancho)) {fire=false;}
        if (posicion.x > (1030))      {fire=false;}
        }

        //la vacuana esta a la espera de ser lanzada
        if (fire ==false){

         if(m.getHeroe().getHeroe().isFlipX()==false){sentido= 1; imagen.flip(false,false);}
         if(m.getHeroe().getHeroe().isFlipX()==true){sentido =-1; imagen.flip(true,false); }

         posicion.x = m.getHeroe().getPosicion().x+50;

          posicion.y = m.getHeroe().getPosicion().y+35;

          imagen.setPosition(posicion.x, posicion.y);
          borde.x = posicion.x;
          borde.y = posicion.y;

        }




        }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public Rectangle getBorde() {
        return borde;
    }

    public void setBorde(Rectangle borde) {
        this.borde = borde;
    }

    public int getSentido() {
        return sentido;
    }

    public void setSentido(int sentido) {
        this.sentido = sentido;
    }

    public Sprite getImagen() {
        return imagen;
    }

    public void setImagen(Sprite imagen) {
        this.imagen = imagen;
    }
}



