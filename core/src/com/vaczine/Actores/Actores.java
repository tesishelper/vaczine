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


import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;

public class Actores {


    Mundo m;
    Sprite imagen;
    private TextureAtlas ta_actores;//carga imagenes de atlas de texturas
    Sprite auraATB, muerte;
    private NumberFormat format = new DecimalFormat("0.00");
    float speed=5;
    float ancho;
    float alto;
    int grupo; //0 = infante, 1 = niño, 2=jovenF, 3=jovenM, 4= adultoF, 5 adultoFP, 6=adultoM,7=ancianoF, 8 ancianoM
    int estado; //0 = sano, 1 = enfermo, 2=curado, 3=vacunado, 4= muerto


    Vector2 posicion;
    Vector2 direccion;
    Rectangle borde;
    boolean miraEste=true;

    float escalaAlto = (float) 0.45;
    float escalaAncho = (float) 0.45;

    long edad, delta, delta2, delta3; // se usa para determinar el paso del
    int segundos;

    float dt = Gdx.graphics.getDeltaTime();

    String index="sano";


    public Actores(int grupo, int estado, Vector2 posicion,Mundo m) {

        this.m = m;
        this.grupo=grupo;
        this.estado=estado;
        this.posicion = posicion;


  //determinar la imagen del actor
        ta_actores = m.getTa_actores(); //tomamos las texturas de la clase mundo

       // index = actorIndex(grupo,estado);


        imagen = new Sprite(ta_actores.findRegion(actorIndex(grupo,estado))); // normal


        ancho = imagen.getWidth()*0.40f;
        alto = imagen.getHeight()*0.30f;


        if (m.isInGame()==true){ //si es minijuego el tamño de los personajes es

        ancho = ancho*1.5f;
        alto = alto*1.5f;
        }

        imagen.setSize(ancho, alto);
        float radio = (float) Math.sqrt(m.getInfectividad());
      //  System.out.println(radio  );
        borde = new Rectangle(); // rectangulo que su usa para la reteccion de coliciones
        borde.setSize(ancho*(radio/40), alto*(radio/40));
        borde.setPosition(this.posicion.x+ ancho/2 - borde.getWidth()/2, this.posicion.y+ alto/2 -borde.getHeight()/2);//posision del rectangulo


//determinar el tiempo
        segundos = 0;
        if (estado==1) {setTimeEnfermo();} //anota el tiempo de la enfermedad
        setEdad();
        setDelta();
        setDelta3();

//direccion del movimiento
        direccion = new Vector2((float) Math.random() * 20,
                (float) Math.random() * 20);
        if (direccion.x < 10) {
            direccion.x = direccion.x * (-1);
        }
        if (direccion.x > 10) {
            direccion.x = direccion.x - 10;
        }
        if (direccion.y < 10) {
            direccion.y = direccion.y * (-1);
        }
        if (direccion.y > 10) {
            direccion.y = direccion.y - 10;
        }

    }



/*
    public int actorIndex(int grupo, int estado, int index){

        if (grupo == 0 && estado== 0) {index = 34;}	//bb sano
        if (grupo == 0 && estado== 1) {index = 36;} //bb enfermo
        if (grupo == 0 && estado== 2) {index =  35;} //bb curado
        if (grupo == 0 && estado== 3) {index =  33;} //bb vacunado
        if (grupo == 1 && estado== 0) {index =  31;} //niñe sane
        if (grupo == 1 && estado== 1) {index = 29;} //niñe enfermo
        if (grupo == 1 && estado== 2) {index = 30;} //niñe curade
        if (grupo == 1 && estado== 3) {index = 28;} //niñe vacunade
        if (grupo == 2 && estado== 0) {index = 20;} //adol sana
        if (grupo == 2 && estado== 1) {index = 19;} //adol enferma
        if (grupo == 2 && estado== 2) {index = 22;} //adol curada
        if (grupo == 2 && estado== 3) {index =  21;} //adol vacunada
        if (grupo == 3 && estado== 0) {index = 23;} //adol sano
        if (grupo == 3 && estado== 1) {index = 27;} //adol enfermo
        if (grupo == 3 && estado== 2) {index = 26;} //adol curado
        if (grupo == 3 && estado== 3) {index = 25;} //adol vacunado
        if (grupo == 4 && estado== 0) {index =16;} //mujer sana
        if (grupo == 4 && estado== 1) {index = 17;} //mujer enferma
        if (grupo == 4 && estado== 2) {index = 12;} //mujer curada
        if (grupo == 4 && estado== 3) {index = 13;} //mujer vacunada
        if (grupo == 5 && estado== 0) {index = 24;} //embarazada sana
        if (grupo == 5 && estado== 1) {index = 18;} //embarazada enferma
        if (grupo == 5 && estado== 2) {index =15;} //embarazada curada
        if (grupo == 5 && estado== 3) {index = 14;} //embarazada vacunada
        if (grupo == 6 && estado== 0) {index = 11;} //hombre sano
        if (grupo == 6 && estado== 1) {index = 10;} //hombre enfermo
        if (grupo == 6 && estado== 2) {index = 3;} //hombre curado
        if (grupo == 6 && estado== 3) {index = 2;} //hombre vacunado
        if (grupo == 7 && estado== 0) {index = 9;} //anciana sana
        if (grupo == 7 && estado== 1) {index = 8;} //anciana enferma
        if (grupo == 7 && estado== 2) {index = 5;} //anciana curada
        if (grupo == 7 && estado== 3) {index = 4;} //anciana vacunada
        if (grupo == 8 && estado== 0) {index = 7;} //anciano sano
        if (grupo == 8 && estado== 1) {index = 6;} //anciano enfermo
        if (grupo == 8 && estado== 2) {index =1;} //anciano curado
        if (grupo == 8 && estado== 3) {index = 0;} //anciano vacunado

        return index;
    }
*/
    public String actorIndex(int grupo, int estado){

        String str="";

        if (estado== 0) {str = "sano";} //niñe sane
        if ( estado== 1) {str = "enfermo";} //niñe enfermo
        if ( estado== 2) {str = "curado";} //niñe curade
        if ( estado== 3) {str = "vacunado";} //niñe vacunade
        if ( estado== 4) {str = "muerto";} //niñe vacunade

        return str;
    }


//metodo para dibujar el actor

    public void verActor(SpriteBatch sb) {

        sb.begin();
        imagen.draw(sb);
        sb.end();


    }

    public void verBorde(ShapeRenderer sr, BitmapFont font, SpriteBatch sb, float dt) {

        sr.begin(ShapeType.Filled);

        sr.setColor(Color.CYAN);
        sr.rect(borde.x, borde.y, borde.width, borde.height);
        sr.end();

        sb.begin();
        font.draw(sb, ""+ dt*1000 ,borde.x, borde.y);
        sb.end();

    }

//evolución del actor

    public void update(float dt) {

        if(m.getPlay()==1){

        aleatorio();
        evolEnfer(m.getLetalidad());

        posicion.add(dt* (direccion.x) * m.getMovilidad(),
                dt*(direccion.y)*m.getMovilidad());

       // posicion.add(Gdx.graphics.getDeltaTime() * (direccion.x) * m.getMovilidad(),
       //         Gdx.graphics.getDeltaTime() * (direccion.y) *m.getMovilidad());

        //System.out.println("Delta "+ Gdx.graphics.getDeltaTime());

     if(m.getCuarentena()==0){   //no hay cuarentena

        if (posicion.x < -100) {posicion.x = -100; direccion.x= direccion.x*(-1);}
        if (posicion.x > 1124) {posicion.x =1124;direccion.x= direccion.x*(-1);}
        if (posicion.y < -100) {posicion.y = -100; direccion.y = direccion.y*(-1);}
        if (posicion.y > 700) {posicion.y = 700;direccion.y = direccion.y*(-1);}
        }

        if(m.getCuarentena()==1){ //hay cuarentena parcial

           // if(estado==1){

                if (posicion.x < -100) {posicion.x = -100; direccion.x= direccion.x*(-1);}

                if (posicion.x > 240 && posicion.x<250&&posicion.y >312) {posicion.x =240;direccion.x= direccion.x*(-1);}
                if (posicion.x > 240 && posicion.x<250&&posicion.y <287) {posicion.x =240;direccion.x= direccion.x*(-1);}

                if (posicion.x < 260 && posicion.x>250&&posicion.y >312) {posicion.x =260;direccion.x= direccion.x*(-1);}
                if (posicion.x < 260 && posicion.x>250&&posicion.y <287) {posicion.x =260;direccion.x= direccion.x*(-1);}

                if (posicion.y < -100) {posicion.y = -100; direccion.y = direccion.y*(-1);}
                if (posicion.y > 700) {posicion.y = 700;direccion.y = direccion.y*(-1);}


                 if (posicion.x > 1124) {posicion.x =1124;direccion.x= direccion.x*(-1);}
            }

     if(m.getCuarentena()==2){ //hay cuarentena total

         if(estado==1){

             if (posicion.x < -100) {posicion.x = -100; direccion.x= direccion.x*(-1);}
             if (posicion.x > 240) {posicion.x =240;direccion.x= direccion.x*(-1);}
             if (posicion.y < -100) {posicion.y = -100; direccion.y = direccion.y*(-1);}
             if (posicion.y > 700) {posicion.y = 700;direccion.y = direccion.y*(-1);}

         } else{

             if (posicion.x < 260) {posicion.x = 260; direccion.x= direccion.x*(-1);}
             if (posicion.x > 1124) {posicion.x =1124;direccion.x= direccion.x*(-1);}
             if (posicion.y < -100) {posicion.y = -100; direccion.y = direccion.y*(-1);}
             if (posicion.y > 700) {posicion.y = 700;direccion.y = direccion.y*(-1);}


         }


     }




        imagen.setPosition(this.posicion.x, this.posicion.y);//posicion del actor
        borde.setPosition(this.posicion.x+ ancho/2 - borde.getWidth()/2, this.posicion.y+ alto/2 -borde.getHeight()/2);//posision del rectangulo


        if(direccion.x>0) {imagen.setFlip(false,false);}
        if(direccion.x<0) {imagen.setFlip(true,false);}

    }}

//matar el actor

    public void morir() {

        estado = 4;

    }
    //esta funcion desencadena la enfermedad
    public void enfermar(float infectividad) {

      //  float i = (float) (Math.random()*100); //tiramos los dados

      //  if (i<=infectividad) { if(estado == 0) { estado = 1; setTimeEnfermo();m.playPlop();}} //si no esta vacunado el actor se enferma y se anota el tiempo
        if(estado == 0) { estado = 1; setTimeEnfermo();

       m.playPlop();
        } //si no esta vacunado el actor se enferma y se anota el tiempo

//deve escojer su proxima imagen

        imagen = new Sprite(ta_actores.findRegion(actorIndex(grupo,estado))); // normal
        imagen.setSize(ancho, alto);

//System.out.println("enfermó");

    }

    public void vacunarse(){

        estado = 3;

       ;
        imagen = new Sprite(ta_actores.findRegion(actorIndex(grupo,estado))); // normal
        imagen.setSize(ancho, alto);
        m.playokbell();

    }

//evolucion de la enfermedad

    public void evolEnfer(float letalidad) {

        if(estado==1) {// si el actor esta enfermo

            if (deltaTimeEnfermo() > msecondTime(m.getDuracion())) {//pasaron 15 segundos=30 días

                float i = (float) (Math.random()*100);

                if (i<=letalidad) {estado=4;};//el actor muere
                if (i>letalidad) {estado=2;} //el actor se cura

                imagen = new Sprite(ta_actores.findRegion(actorIndex(grupo,estado))); // normal
                imagen.setSize(ancho, alto);

            }}

    }

    //agrega canvios en el movimiento de los actores
    public void aleatorio() {

        if (delta3Time() > msecondTime((long) (Math.random() * (20000 - 5000)) + 5000)) {

            direccion.x = (float) (Math.random() * (10 + 10) - 10)*m.Xr(100);
            direccion.y = (float) (Math.random() * (10 + 10) - 10)*m.Yr(100);

            if (direccion.x > 10*m.Xr(100)) {
                direccion.x = 10*m.Xr(100);
            }
            if (direccion.x < -10*m.Xr(100)) {
                direccion.x = -10*m.Xr(100);
            }
            if (direccion.y > 10*m.Yr(100)) {
                direccion.y = 10*m.Yr(100);
            }
            if (direccion.y < -10*m.Yr(100)) {
                direccion.y = -10*m.Yr(100);
            }


            setDelta3();

        }

    }

//manejo del tiempo
// mide los segundos transcurrido desde su creación

    public void contadorTiempo() {

        if (delta2Time() > msecondTime(1000)) {

            segundos = segundos + 1;
            // System.out.println(segundos + " tiempo mitosis "+ tiempoMitosis);

            setDelta();
        }
    }

// mide el tiempo transcurrido desde el unltimo set

    public long deltaTimeEnfermo() {
        return TimeUtils.nanoTime() - delta;
    }

    public long edadTime() {
        return TimeUtils.nanoTime() - edad;
    }

    public long delta2Time() {
        return TimeUtils.nanoTime() - delta2;
    }

    public long delta3Time() {
        return TimeUtils.nanoTime() - delta3;
    }

// anota el tiempo de juego transcurrido en el momento que se invoca un
// evento

    public void setTimeEnfermo() {
        delta = TimeUtils.nanoTime();
    }

    public void setEdad() {
        edad = TimeUtils.nanoTime();
    }

    public void setDelta() {
        delta2 = TimeUtils.nanoTime();
    }

    public void setDelta3() {
        delta3 = TimeUtils.nanoTime();
    }

    // convierte de ms a nanosegundos para mas comodidad
    public long msecondTime(long ms) {

        return ms * 1000000;
    }


    public Sprite getImagen() {
        return imagen;
    }


    public void setImagen(Sprite imagen) {
        this.imagen = imagen;
    }


    public Sprite getAuraATB() {
        return auraATB;
    }


    public void setAuraATB(Sprite auraATB) {
        this.auraATB = auraATB;
    }


    public NumberFormat getFormat() {
        return format;
    }


    public void setFormat(NumberFormat format) {
        this.format = format;
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


    public int getGrupo() {
        return grupo;
    }


    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }


    public int getEstado() {
        return estado;
    }


    public void setEstado(int estado) {
        this.estado = estado;
    }


    public Vector2 getPosicion() {
        return posicion;
    }


    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }


    public Vector2 getDireccion() {
        return direccion;
    }


    public void setDireccion(Vector2 direccion) {
        this.direccion = direccion;
    }


    public Rectangle getBorde() {
        return borde;
    }


    public void setBorde(Rectangle borde) {
        this.borde = borde;
    }


    public boolean isMiraEste() {
        return miraEste;
    }


    public void setMiraEste(boolean miraEste) {
        this.miraEste = miraEste;
    }





    public float getEscalaAlto() {
        return escalaAlto;
    }


    public void setEscalaAlto(float escalaAlto) {
        this.escalaAlto = escalaAlto;
    }


    public float getEscalaAncho() {
        return escalaAncho;
    }


    public void setEscalaAncho(float escalaAncho) {
        this.escalaAncho = escalaAncho;
    }


    public long getEdad() {
        return edad;
    }


    public void setEdad(long edad) {
        this.edad = edad;
    }


    public long getDelta() {
        return delta;
    }


    public void setDelta(long delta) {
        this.delta = delta;
    }


    public long getDelta2() {
        return delta2;
    }


    public void setDelta2(long delta2) {
        this.delta2 = delta2;
    }


    public long getDelta3() {
        return delta3;
    }


    public void setDelta3(long delta3) {
        this.delta3 = delta3;
    }


    public int getSegundos() {
        return segundos;
    }


    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }


}
