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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vaczine.Game.Mundo;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Vaczinito {

    Mundo m;
    private TextureAtlas ta_actores;//carga imagenes de atlas de texturas
    private Texture imagen, imagen2;
    private Sprite heroe, heroeCaido;
    private NumberFormat format = new DecimalFormat("0.00");
    private ShapeRenderer caja;
    float ancho;
    float alto;
    Rectangle borde;
    private int dosis= 0; //cuenta la cantidad de vacuanas disparadas
    private boolean caido = false;

    private int direccionX = 0;
    private int direccionY = 0;

    Vector2 posicion;


    public Vaczinito(Vector2 posicion,Mundo m) {

        this.m = m;
        this.posicion = posicion;

        caja = new ShapeRenderer();

        //instanciar las imagenes y definir el tamaño del actor

        imagen = m.getT_heroe(); // normal
        imagen2 = m.getT_heroeCaido();
        heroe = new Sprite(imagen);
        heroeCaido = new Sprite(imagen2);

        ancho = 100;
        alto = 90;
        heroe.setSize(ancho, alto);
        heroeCaido.setSize(90, 90);


        this.posicion.x=820;
        this.posicion.y= 300;

        heroe.setPosition(this.posicion.x, this.posicion.y);//posicion del actor
        heroeCaido.setPosition(this.posicion.x, this.posicion.y);//posicion del actor

        borde = new Rectangle();
        borde.height = alto*0.6f;
        borde.width = ancho*0.5f;
        borde.x = posicion.x;
        borde.y = posicion.y;

        //carga las vacunas

        m.agregarvacunas();


    }
//metodo para dibujar el actor

    public void verHeroe(SpriteBatch sb) {

       if(caido==false){
        sb.begin();
        heroe.draw(sb);
        sb.end();}
       else {
           sb.begin();
           heroeCaido.draw(sb);
           sb.end();}

       // caja.begin(ShapeType.Line);
      //  caja.setColor(Color.CYAN);
      // caja.rect(borde.x,borde.y,borde.width, borde.height);
      // caja.end();


    }

    public void vacunar(){

       for (int i=0; i<m.getaVacunas().size;i++){

       m.getaVacunas().get(dosis).setFire(true);

        Vacuna v = m.getaVacunas().get(i);

        if(v.isFire()==false){



       v.setFire(true);
       i=m.getaVacunas().size;
       m.playLaser();
       }}}


    public void update(float dt) {

     if (m.getPlay() == 1) {

         posicion.add(dt* (direccionX) * m.getMovilidad(),
                 dt*(direccionY)*m.getMovilidad());


         if(posicion.x<-50 ){posicion.x=1025;}
         if(posicion.x>1025 ){posicion.x=-50;}
         if(posicion.y<-50 ){posicion.y=600;}
         if(posicion.y>600 ){posicion.y=-50;}



         if(direccionX>0) {heroe.setFlip(true,false);

             heroe.setPosition(this.posicion.x+20, this.posicion.y);//posicion del actor
             heroeCaido.setPosition(this.posicion.x+40, this.posicion.y+20);
             borde.setPosition(this.posicion.x+ 40, this.posicion.y+20);//posision del rectangulo  //System.out.print("heroe pos x:"+ posicion.x +" y: "+ posicion.y);
         }
         if(direccionX<0) {heroe.setFlip(false,false);

             heroe.setPosition(this.posicion.x, this.posicion.y);//posicion del actor
             heroeCaido.setPosition(this.posicion.x+40, this.posicion.y+20);
             borde.setPosition(this.posicion.x+ 40, this.posicion.y+20);//posision del rectangulo  //System.out.print("heroe pos x:"+ posicion.x +" y: "+ posicion.y);
         }

         if(direccionX==0) {
             heroe.setPosition(this.posicion.x, this.posicion.y);//posicion del actor
             heroeCaido.setPosition(this.posicion.x+40, this.posicion.y+20);
             borde.setPosition(this.posicion.x+ 40, this.posicion.y+20);//posision del rectangulo  //System.out.print("heroe pos x:"+ posicion.x +" y: "+ posicion.y);
         }


        }
    }




    public Vector2 getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }


    public Sprite getHeroe() {
        return heroe;
    }

    public void setHeroe(Sprite heroe) {
        this.heroe = heroe;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getAlto() {
        return alto;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    public Rectangle getBorde() {
        return borde;
    }

    public void setBorde(Rectangle borde) {
        this.borde = borde;
    }

    public boolean isCaido() {
        return caido;
    }

    public void setCaido(boolean caido) {
        this.caido = caido;
    }

    public int getDireccionX() {
        return direccionX;
    }

    public void setDireccionX(int direccionX) {
        this.direccionX = direccionX;
    }

    public int getDireccionY() {
        return direccionY;
    }

    public void setDireccionY(int direccionY) {
        this.direccionY = direccionY;
    }
}
