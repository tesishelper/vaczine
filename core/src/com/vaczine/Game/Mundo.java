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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.vaczine.Actores.Actores;
import com.vaczine.Actores.DatoTabla;

import com.vaczine.Actores.Vaczinito;
import com.vaczine.Actores.Vacuna;
import com.vaczine.Pantallas.Resultado01;

public class Mundo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private VaczineGame game;

    private TextureAtlas ta_actores;//carga imagenes de atlas de texturas
    private Texture t_heroe, t_vacuna,t_heroeCaido; //textura de nuestro heroe
    private Skin sk_skin, sk_actores;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private float ancho;
    private float alto;
    private Array<Actores> act0,act;
    private Array<Vacuna> aVacunas;


    private Array<DatoTabla> aEnfermos,aSanos,aCurados,aVacunados,aMuertos,aMovilidad,aCuarentena; // lista de organismos

    private Array<DatoTabla> aEnfermos0,aSanos0,aCurados0,aVacunados0,aMuertos0,aMovilidad0,aCuarentena0; // lista de organismos
    private Vaczinito heroe;

    private int poblacion = 500, poblacion0; //tamaño total de la poblacion al comenzar
    private int cobertura = 0, cobertura0; //% de personas vacunadas
    private int enfermos = 10, enfermos0; //# de personas enfermas al comenzar
    private int letalidad = 3, letalidad0;//% de personas enfermas que moriran
    private int infectividad= 20,infectividad0; //% poresnta je de personas sanas que se enfermaran
    private int numero; //se usa para contar
    private int cuarentena = 0; // 0 = false 1= L1 y 2= l2
    private int duracion = 15000; // duracion de la enfermedad

    private int play =1; //1= play 0= pausa
    private int bono = 100; //premio por no morir
    int movilidad=10;
    private boolean gameOver = false; //se hace true cuando termina el juego
    private boolean inGame = false;   //se hace true cuando estamos jugando el minijuego
    private int nivel = 1; //nivel del juego
    private Rectangle ro,rc,ru, rd,rl,rr,rur,rul,rdr,rdl;

    private int setEnfermos=0;

    private int actorTotal =0;
    private int actorSano = 0; //numero de actores
    private int actorEnfermo = 0; //numero de actores
    private int actorCurado = 0; //numero de actores
    private int actorVacunado = 0; //numero de actores
    private int actorMuerto =0;


    int dias0,dias20,meses0,años0, segundos,segundos2,segundos3, dias,dias2, meses, años; // anota el paso del
    // tiempo
    long deltaDias,deltaDias2, deltaMes, deltaAño, deltaSegundos, deltaSegundos2,deltaSegundos3; // mide diferencia de tiempo entre

   /* private int infanteMuerto;
    private int niñeMuerto;
    private int jovenFMuerta;
    private int jovenMMuerto;
    private int adultoFMuerta;
    private int adultoFPMuerta;
    private int adultoMMuerto;
    private int ancianoFMuerta;
    private int ancianoMMuerto;
    private int infanteTotal;
    private int niñeTotal;
    private int jovenFTotal;
    private int jovenMTotal;
    private int adultoFTotal;
    private int adultoFPToatal;
    private int adultoMTotal;
    private int ancianoFTotal;
    private int ancianoMTotal;

    */

    private Sonido s;									// una accion y la siguiente

    private Color colorFondo;

    private int pobSIR = 5000000;
    private int infSIR = 1;
    private int vacSIR = 0;
    private float R0SIR = 3.5f;
    private int TinfSIR = 12;
    private float mortalidadSIR = 0.5f;

    //Constructor
    public Mundo(VaczineGame game) {

        this.game = game;

        ancho= 1024;
        alto= 600;

        s = new Sonido();//carga el sonido

        colorFondo = new Color(0f/255f, 170f/255f, 170f/255f, 1);



//instancia los actores
        ta_actores = new TextureAtlas("actores.pack");//carga el atlas de texturas donde estan los botones
        sk_actores = new Skin();
        sk_actores.addRegions(ta_actores);


//instancia las listas
        act0 = new Array<Actores>();
        act = new Array<Actores>();
        aVacunas = new Array<Vacuna>();


//listas para el simulador basico

        aEnfermos = new Array<DatoTabla>();
        aSanos = new Array<DatoTabla>();
        aCurados = new Array<DatoTabla>();
        aVacunados = new Array<DatoTabla>();
        aMuertos = new Array<DatoTabla>();
        aMovilidad= new Array<DatoTabla>();
        aCuarentena= new Array<DatoTabla>();

 //lista que se usaran para ver datos anteriores en el simulador basico

        aEnfermos0 = new Array<DatoTabla>();
        aSanos0 = new Array<DatoTabla>();
        aCurados0 = new Array<DatoTabla>();
        aVacunados0 = new Array<DatoTabla>();
        aMuertos0 = new Array<DatoTabla>();
        aMovilidad0= new Array<DatoTabla>();
        aCuarentena0= new Array<DatoTabla>();


//instancia nuetro heroe
        t_heroe = new Texture("heroe2.png");
        t_vacuna = new Texture("vacuna.png");
        t_heroeCaido = new Texture("heroeCaido.png");


        Vector2 pos = new Vector2();
        heroe = new Vaczinito(pos,this);



        rdl = new Rectangle(60,20,66,66);
        rd = new Rectangle(126,20,66,66);
        rdr = new Rectangle(193,20,66,66);

        rl = new Rectangle(60,86,66,66);
        ro = new Rectangle(159,119,2,2);
        rc = new Rectangle(126,86,66,66);
        rr = new Rectangle(193,86,66,66);

        rul = new Rectangle(60,152,66,66);
        ru = new Rectangle(126,152,66,66);
        rur = new Rectangle(193,152,66,66);


    }

//calcular la carga de la poblacion

    public void poblacionInicial() {


        actorVacunado = (int) (poblacion*((float)cobertura/100));
     //   infanteEnfermo = (int) ((infante-infanteVacunado)*((float)enfermos/100)); //numero de actores
        actorSano = poblacion-actorVacunado; //numero de actores




    }
//metodos para expresar un valor relativo al tamaño de la pantalla usar números de 0 a 100

    public float Xsize(float xpos){
        float ancho = Gdx.graphics.getWidth();
        return (xpos * ancho) / 100;  }

    public float Ysize(float ypos){
        float alto = Gdx.graphics.getHeight();
        return (ypos * alto) / 100;  }

  //metodo para escalar objetos en proporcion al tamaño de la pantalla usar como %. !00 es igual a 1
     public float Xr(float xr){
        float ancho = Gdx.graphics.getWidth();
         return (ancho/1024)*(xr/100); }

    public float Yr(float yr){
        float alto = Gdx.graphics.getHeight();
        return (alto/600)*(yr/100); }

    //metodo para definir el tamaño texto y posicion de un boton

 public void addBoton(TextButton boton, float txSize,float tySize, float xsize, float ysize, float xpos, float ypos){

     boton.getLabel().setFontScale(txSize,tySize);
     boton.setWidth(xsize);
     boton.setHeight(ysize);
     boton.setX(xpos-boton.getWidth()/2);
     boton.setY(ypos);

 }
// metodo para definir tamaño y posicion de un textField

 public void addTextField (TextField tf,TextFieldStyle tfs, float xsize, float ysize, float xpos, float ypos){

    // tfs.font.getData().setScale(Xsize((float)0.10));

     tf.setAlignment(Align.top);
     tf.setAlignment(Align.center);
     tf.setCursorPosition(Align.right);
     tf.setSize(xsize, ysize);
     tf.setCursorPosition(0);
     tf.setPosition(xpos -(tf.getWidth()/2), ypos); }

     public void addTexto(SpriteBatch sb, BitmapFont fuente, String str, float xpos, float ypos){
    //fuente.getData().setScale(Xsize((float)0.14));
    fuente.draw(sb, str, xpos,ypos+ fuente.getLineHeight());
     }

    public int RandomInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public void agregarActores() {

        int xmax= 1124;
        int xmin=-100;
        int ymax= 700;
        int ymin= -100;

//agrega actor sano
        for (int i = 0; i < actorSano; i++) {
            Vector2 pos = new Vector2((float) RandomInRange(xmin, xmax),(float) RandomInRange(ymin, ymax));
            act.add(new Actores(0,0,pos,this));}
//agrega actor enfermo

       // for (int i = 0; i < actorEnfermo; i++) {
       //     Vector2 pos = new Vector2((float) RandomInRange(xmin, xmax),(float) RandomInRange(ymin, ymax));
       //     act.add(new Actores(0,1,pos,this));}
//agrega actor vacunado
        for (int i = 0; i < actorVacunado; i++) {
            Vector2 pos = new Vector2((float) RandomInRange(xmin, xmax),(float) RandomInRange(ymin, ymax));
            act.add(new Actores(0,3,pos,this));}


        act.shuffle();
    }

    public void agregarenfermo(int numero) {

        for (int e=0; e<numero;e++) {
            int i = (int) (Math.random()*7);
            Vector2 pos = new Vector2((float) Math.random() * ancho,(float) Math.random() * alto);

            act.add(new Actores(i,1,pos,this));

            act.shuffle();}
    }

    public void agregarvacunas(){

        for (int i=0; i<20; i++){

            aVacunas.add(new Vacuna(this));

        }
//System.out.println(aVacunas.size);
    }

//metodo update

    public void update(){
        if (play==1) {

            if(años ==1) {contarTodo();game.setScreen(new Resultado01(game));  }//detien la partida

            colectarDatos(1000);//agrega enfermos para la gráfica cada 3 días

            detectarColiciones();

//mueve los actores
            numero = act.size;


        }}

//metodo update para la pantalla Minijuego


    public void update2(){
        if (play==1) {

            if(años ==1) {contarTodo();game.setScreen(new Resultado01(game));  }//detien la partida



            detectarColiciones();

//mueve los actores
            numero = act.size;


         //heroe.update();
 //mueve las vacunas
        numero = aVacunas.size;
            for (int i = numero - 1; i >= 0; i--) {
                aVacunas.get(i).update();}

        }}

//contar actores

    public int contarPoblacion(Array<Actores> act,int n) {//0 infantes, 1 niñes, 2 jovenesF, 3 jovenesM, 4 adultoF, 5 AdultoFO, 6 adultoM, 7 ancianoF 8 ancianoM, 9 todos
        numero =0;

        for (int i =0; i<act.size;i++) {

            if(n == 9) { if(act.get(i).getEstado()!=4) {numero = numero+1;}}//cuenta a todos
            if(act.get(i).getGrupo()==n) {if(act.get(i).getEstado()!=4) {numero = numero+1;}}
        }
        return numero;}


    public int contarEnfermos(Array<Actores> act,int n) {
        numero =0;
        for (int i =0; i<act.size;i++) {

            if(n == 9) { if(act.get(i).getEstado()==1) {numero = numero+1;}}//cuenta a todos enfermos
            if(act.get(i).getGrupo()==n) {if(act.get(i).getEstado()==1) {numero = numero+1;}}
        }
        return numero;}

    public int contarSanos(Array<Actores> act,int n) {
        numero =0;
        for (int i =0; i<act.size;i++) {

            if(n == 9) { if(act.get(i).getEstado()==0) {numero = numero+1;}}//cuenta a todos enfermos
            if(act.get(i).getGrupo()==n) {if(act.get(i).getEstado()==0) {numero = numero+1;}}
        }
        return numero;}

    public int contarCurados(Array<Actores> act,int n) {
        numero =0;
        for (int i =0; i<act.size;i++) {

            if(n == 9) { if(act.get(i).getEstado()==2) {numero = numero+1;}}//cuenta a todos enfermos
            if(act.get(i).getGrupo()==n) {if(act.get(i).getEstado()==2) {numero = numero+1;}}
        }
        return numero;}

    public int contarVacunados(Array<Actores> act,int n) {
        numero =0;
        for (int i =0; i<act.size;i++) {

            if(n == 9) { if(act.get(i).getEstado()==3) {numero = numero+1;}}//cuenta a todos enfermos
            if(act.get(i).getGrupo()==n) {if(act.get(i).getEstado()==3) {numero = numero+1;}}
        }
        return numero;}

    public int contarMuertos(Array<Actores> act,int n) {
        numero =0;
        for (int i =0; i<act.size;i++) {

            if(n == 9) { if(act.get(i).getEstado()==4) {numero = numero+1;}}//cuenta a todos enfermos
            if(act.get(i).getGrupo()==n) {if(act.get(i).getEstado()==4) {numero = numero+1;}}
        }
        return numero;}

    public int contarEnfermosHistoricos(Array<Actores> act,int n) {
        numero =0;
        for (int i =0; i<act.size;i++) {
            if(n == 9) {if(act.get(i).getEstado()==1) {numero = numero+1;}//cuenta enfermos
                if(act.get(i).getEstado()==2) {numero = numero+1;}//cuenta curados
                if(act.get(i).getEstado()==4) {numero = numero+1;}}//cuenta muertos
            if(act.get(i).getGrupo()==n) {if(act.get(i).getEstado()==1) {numero = numero+1;}//cuenta enfermos
                if(act.get(i).getEstado()==2) {numero = numero+1;}//cuenta curados
                if(act.get(i).getEstado()==4) {numero = numero+1;}}//cuenta muertos
        }
        return numero;}

   public void resetListas(Array lista0, Array lista1){

       lista0.clear();

       for(int i=0;i<lista1.size;i++){

           lista0.add(lista1.get(i));
       }

       lista1.clear(); //limpiar la lista de actores

   }


    public void resetMundo(){

        resetListas(act0, act);

        //pasamos los datos de la última artida aotro set de variables

        dias0 = dias;
        dias20 = dias2;
        meses0 = meses;
        años0 = años;

        poblacion0 = poblacion;
        cobertura0 = cobertura;
        enfermos0 = enfermos;
        letalidad0 = letalidad;
        infectividad0= infectividad;

        resetListas(aEnfermos0,aEnfermos);
        resetListas(aSanos0,aSanos);
        resetListas(aCurados0,aCurados);
        resetListas(aVacunados0,aVacunados);
        resetListas(aMuertos0,aMuertos);
        resetListas(aMovilidad0, aMovilidad);
        resetListas(aCuarentena0,aCuarentena);


        setDias(0);
        setDias2(0);
        setMeses(0);
        setAños(0);
        setDelta();

        cuarentena =0;
        movilidad = 10;



    }



    public void contarTodo() {
        actorTotal=0;
        actorSano = 0; //numero de actores
        actorEnfermo = 0; //numero de actores
        actorCurado = 0; //numero de actores
        actorVacunado = 0; //numero de actores


        for (int i=0;i<act.size;i++) {

            if(act.get(i).getGrupo()==0 ){actorTotal++;};
            if(act.get(i).getGrupo()==0 && act.get(i).getEstado()==0){actorSano++;};
            if(act.get(i).getGrupo()==0 && act.get(i).getEstado()==1) {actorEnfermo++;};
            if(act.get(i).getGrupo()==0 && act.get(i).getEstado()==2) {actorCurado++;};
            if(act.get(i).getGrupo()==0 && act.get(i).getEstado()==3) {actorVacunado++;};
            if(act.get(i).getGrupo()==0 && act.get(i).getEstado()==4) {actorMuerto++;};

                    }


    }

   //se usa para guardar la pantalla que se esta viendo
    /*public void capturarPantalla(String str){

        Date date = new Date();
        Calendar calendarG = new GregorianCalendar();
        calendarG.setTime(date);

        String year = String.valueOf(calendarG.get(Calendar.YEAR));
        int month = calendarG.get(Calendar.MONTH);
        String strMonth = String.valueOf(calendarG.get(Calendar.MONTH));
        if(month<10){strMonth = "0"+String.valueOf(calendarG.get(Calendar.MONTH));}
        int day =  calendarG.get(Calendar.DAY_OF_MONTH);
        String strDay =  String.valueOf(calendarG.get(Calendar.DAY_OF_MONTH));
        if(day<10){strDay =  "0"+String.valueOf(calendarG.get(Calendar.DAY_OF_MONTH));}
        int hours = calendarG.get(Calendar.HOUR_OF_DAY);
        String strHours = String.valueOf(calendarG.get(Calendar.HOUR_OF_DAY));
        if (hours<10) { strHours = "0"+String.valueOf(calendarG.get(Calendar.HOUR_OF_DAY));}
        int minutes = calendarG.get(Calendar.MINUTE);
        String strMinutes = String.valueOf(calendarG.get(Calendar.MINUTE));
        if(minutes<10){strMinutes = "0"+String.valueOf(calendarG.get(Calendar.MINUTE));}
        int second = calendarG.get(Calendar.SECOND);
        String strSecond = String.valueOf(calendarG.get(Calendar.SECOND));
        if(second<10){strSecond = "0"+String.valueOf(calendarG.get(Calendar.SECOND));}


        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

// This loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        for (int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }

        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        PixmapIO.writePNG(Gdx.files.external("vaczine/"+str+year+strMonth+strDay+strHours+strMinutes+strSecond+".png"), pixmap);
        pixmap.dispose();


    }

     */


    public void colectarDatos(int x){

        if (deltaTime2() > msecondTime(x)) {

            DatoTabla dato= new DatoTabla(dias2,contarEnfermos(act,9));
            DatoTabla dato2= new DatoTabla(dias2,contarSanos(act,9));
            DatoTabla dato3= new DatoTabla(dias2,contarCurados(act,9));
            DatoTabla dato4= new DatoTabla(dias2,contarVacunados(act,9));
            DatoTabla dato5= new DatoTabla(dias2,contarMuertos(act,9));
            DatoTabla dato6= new DatoTabla(dias2,movilidad);

            aEnfermos.add(dato);
            aSanos.add(dato2);
            aCurados.add(dato3);
            aVacunados.add(dato4);
            aMuertos.add(dato5);
            aMovilidad.add(dato6);

            int total = contarEnfermos(act,9)+contarCurados(act,9)+contarMuertos(act,9);

           // System.out.println(total);

            //System.out.println("x: "+ dato.getX()+ "; Y: "+ dato.getY());
            setDelta2();
        }

    }



//sonidos

    public void playClick() {s.getButtonClick().play(0.25f); } //vamos a dejarlo mudo en esta version
    public void playTime() { s.getTime().play(0.5f); }
    public void playPlop(){ s.getPlop().play( 0.25f); }
    public void playLaser() { s.getLaser().play(); }
    public void playokbell() { s.getOk_bell().play(); }
  //  public void playMusic(){s.getAmbient().loop();}
   // public void stopMysic(){s.getAmbient().stop();}

    //detectar coliciones
    public void detectarColiciones() {
    // enfermos tocan a sanos
        for (int i = 0; i < act.size; i++) {

            Actores ac = act.get(i);
            Rectangle r = ac.getBorde();

            if (ac.getEstado() ==1) {//si el actor esta enfermo

                for (int a = 0; a < act.size; a++) {
                    Actores ac2 = act.get(a);
                    Rectangle r2 = ac2.getBorde();

                    if (i!=a && ac2.getEstado()!= 1 && ac2.getEstado()!= 4 && r.overlaps(r2)) {//ac y ac2 son diferentes, ac2 no esta enfermo ni muerto y se tocan

                        if(ac2.getEstado()==0) {ac2.enfermar(infectividad/20); };//ac2 no esta vacunado
                        if(ac2.getEstado()==2) {};
                        if(ac2.getEstado()==3) {};
                    }
                }}}
    //vacunas tocan a sanos sin vacunar

        for (int i=0; i< aVacunas.size; i++){

            Vacuna v = aVacunas.get(i);
            Rectangle rv = v.getBorde();

           if(v.isFire()==true){ //si la vacuna fue disparada

               for (int a = 0; a < act.size; a++) {

                Actores ac = act.get(a);
                Rectangle r = ac.getBorde();

                if(rv.overlaps(r) && ac.getEstado()==0){//si la vacuna toca al actor y este no esta vacunado

                    ac.vacunarse();
                    v.setFire(false);

                }}

            }


        }
    //enfermo toca a vaczinito
    if (inGame== true){
        for (int i = 0; i < act.size; i++) {

            Actores ac = act.get(i);
            Rectangle r = ac.getBorde();

            if (ac.getEstado() ==1) {//si el actor esta enfermo

                Rectangle r2 = heroe.getBorde();

                if(r.overlaps(r2)){ //si el enfermo toca a vaczinito

                    setDelta();
                    setDelta5();
                    for (int e=0;i<getAct().size;i++) {

                        getAct().get(e).setTimeEnfermo();//
                    }

                    heroe.setCaido(true);
                    gameOver = true;
                    bono = 0;

                }

               }}}

        if(game.isAndroid()==true){

          if(ro.overlaps(ru)){ heroe.setDireccionY(20); heroe.setDireccionX(0); }
          if(ro.overlaps(rul)){ heroe.setDireccionY(20);heroe.setDireccionX(-20);  }
          if(ro.overlaps(rur)){ heroe.setDireccionY(20);heroe.setDireccionX(20);  }
          if(ro.overlaps(rr)){ heroe.setDireccionY(0);heroe.setDireccionX(20);  }
          if(ro.overlaps(rl)){ heroe.setDireccionY(0);heroe.setDireccionX(-20);  }
          if(ro.overlaps(rd)){ heroe.setDireccionY(-20);heroe.setDireccionX(0);  }
          if(ro.overlaps(rdl)){ heroe.setDireccionY(-20);heroe.setDireccionX(-20);  }
          if(ro.overlaps(rdr)){ heroe.setDireccionY(-20);heroe.setDireccionX(20);  }
          if(ro.overlaps(rc)){ heroe.setDireccionY(0);heroe.setDireccionX(0);  }

        }
}



//mide el tiempo transcurrido desde el ultimo set

    public long deltaTime() {
        return TimeUtils.nanoTime() - deltaDias;
    }

    public long deltaTime2() {
        return TimeUtils.nanoTime() - deltaDias2;
    }

    public void setDelta() {
        deltaDias = TimeUtils.nanoTime();
    }

    public void setDelta2() {
        deltaDias2 = TimeUtils.nanoTime();
    }

    public long deltaTime3()  {
        return TimeUtils.nanoTime() - deltaSegundos;
    }

    public void setDelta3() {
        deltaSegundos = TimeUtils.nanoTime();
    }

    public long deltaTime4()  {
        return TimeUtils.nanoTime() - deltaSegundos2;
    }

    public void setDelta4() {
        deltaSegundos2 = TimeUtils.nanoTime();
    }

    public long deltaTime5()  {
        return TimeUtils.nanoTime() - deltaSegundos3;
    }

    public void setDelta5() {
        deltaSegundos3 = TimeUtils.nanoTime();
    }


    //convierte de ms a nanosegundos para mas comodidad
    public long msecondTime(long ms) {

        return ms * 1000000;
    }

    //convierte de s a nanosegundos para mas comodidad
    public long secondTime(long s) {

        return s * 1000000000;
    }



    public void contadorReverso(){



         if (deltaTime3() > msecondTime(1000) && segundos>0) {

            segundos = segundos -1;

         setDelta3();
    }}


    public void contadorReverso2(){

        if (deltaTime4() > msecondTime(1000) && segundos2>0) {

            segundos2 = segundos2 -1;

            setDelta4();
        }}

   public void contadorReverso3(){

        if (deltaTime() > msecondTime(1000)) {

            segundos3 = segundos3 -1;

            setDelta();
        }}


    public void agregarEnfermoTiempo(int time){

        if (deltaTime5() > msecondTime(time) ) {

           agregarenfermo(1);

            setDelta5();
        }}

public void dispose(){


    s.dispose();
    t_heroeCaido.dispose();
    t_heroe.dispose();
    t_vacuna.dispose();
    ta_actores.dispose();


}


    public void contadorTiempoSIR() {


            dias = dias + 1;
            dias2 = dias2+1;


            if (dias == 30) {
                dias = 0;
                meses = meses + 1;

            }

            if (meses == 12) {
                meses = 0;
                años = años + 1;
            }

    }



    public void contadorTiempoSIR(int speed) {

        int delta = 500/speed;

        if (deltaTime() > msecondTime(delta)) {

            dias = dias + 1;
            dias2 = dias2+1;


            if (dias == 30) {
                dias = 0;
                meses = meses + 1;

            }

            if (meses == 12) {
                meses = 0;
                años = años + 1;
            }

            setDelta();
        }
    }

    public void contadorTiempo() {

        if (deltaTime() > msecondTime(1000)) {

            dias = dias + 2;
            dias2 = dias2+2;


            if (dias == 30) {
                dias = 0;
                meses = meses + 1;

            }

            if (meses == 12) {
                meses = 0;
                años = años + 1;
            }


            setDelta();
        }
    }







//getters and Setters



    public TextureAtlas getTa_actores() {
        return ta_actores;
    }

    public void setTa_actores(TextureAtlas ta_actores) {
        this.ta_actores = ta_actores;
    }

    public Skin getSk_actores() {
        return sk_actores;
    }

    public void setSk_actores(Skin sk_actores) {
        this.sk_actores = sk_actores;
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

    public Array<Actores> getAct() {
        return act;
    }

    public void setAct(Array<Actores> act) {
        this.act = act;
    }

    public int getLetalidad() {
        return letalidad;
    }

    public void setLetalidad(int letalidad) {
        this.letalidad = letalidad;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public int getAños() {
        return años;
    }

    public void setAños(int años) {
        this.años = años;
    }

    public int getPlay() {
        return play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public int getCobertura() {
        return cobertura;
    }

    public void setCobertura(int cobertura) {
        this.cobertura = cobertura;
    }

    public int getInfectividad() {
        return infectividad;
    }

    public void setInfectividad(int infectividad) {
        this.infectividad = infectividad;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public int getEnfermos() {
        return enfermos;
    }

    public void setEnfermos(int enfermos) {
        this.enfermos = enfermos;
    }

    public VaczineGame getGame() {
        return game;
    }

    public void setGame(VaczineGame game) {
        this.game = game;
    }


    public long getDeltaDias() {
        return deltaDias;
    }

    public void setDeltaDias(long deltaDias) {
        this.deltaDias = deltaDias;
    }

    public long getDeltaMes() {
        return deltaMes;
    }

    public void setDeltaMes(long deltaMes) {
        this.deltaMes = deltaMes;
    }

    public long getDeltaAño() {
        return deltaAño;
    }

    public void setDeltaAño(long deltaAño) {
        this.deltaAño = deltaAño;
    }



      public Sonido getS() {
        return s;
    }

    public void setS(Sonido s) {
        this.s = s;
    }

    public Array<DatoTabla> getaEnfermos() {
        return aEnfermos;
    }

    public void setaEnfermos(Array<DatoTabla> aEnfermos) {
        this.aEnfermos = aEnfermos;
    }

    public int getDias2() {
        return dias2;
    }

    public void setDias2(int dias2) {
        this.dias2 = dias2;
    }

    public Array<DatoTabla> getaSanos() {
        return aSanos;
    }

    public void setaSanos(Array<DatoTabla> aSanos) {
        this.aSanos = aSanos;
    }

    public Array<DatoTabla> getaCurados() {
        return aCurados;
    }

    public void setaCurados(Array<DatoTabla> aCurados) {
        this.aCurados = aCurados;
    }

    public Array<DatoTabla> getaVacunados() {
        return aVacunados;
    }

    public void setaVacunados(Array<DatoTabla> aVacunados) {
        this.aVacunados = aVacunados;
    }

    public Array<DatoTabla> getaMuertos() {
        return aMuertos;
    }

    public void setaMuertos(Array<DatoTabla> aMuertos) {
        this.aMuertos = aMuertos;
    }


    public Texture getT_heroe() {
        return t_heroe;
    }

    public void setT_heroe(Texture t_heroe) {
        this.t_heroe = t_heroe;
    }

    public Texture getT_vacuna() {
        return t_vacuna;
    }

    public void setT_vacuna(Texture t_vacuna) {
        this.t_vacuna = t_vacuna;
    }

    public Vaczinito getHeroe() {
        return heroe;
    }

    public void setHeroe(Vaczinito heroe) {
        this.heroe = heroe;
    }

    public Array<Vacuna> getaVacunas() {
        return aVacunas;
    }

    public void setaVacunas(Array<Vacuna> aVacunas) {
        this.aVacunas = aVacunas;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getSegundos2() {
        return segundos2;
    }

    public void setSegundos2(int segundos2) {
        this.segundos2 = segundos2;
    }

    public int getSegundos3() {
        return segundos3;
    }

    public void setSegundos3(int segundos3) {
        this.segundos3 = segundos3;
    }

    public int getCuarentena() {
        return cuarentena;
    }

    public void setCuarentena(int cuarentena) {
        this.cuarentena = cuarentena;
    }

    public int getMovilidad() {
        return movilidad;
    }

    public void setMovilidad(int movilidad) {
        this.movilidad = movilidad;
    }

    public Array<DatoTabla> getaMovilidad() {
        return aMovilidad;
    }

    public void setaMovilidad(Array<DatoTabla> aMovilidad) {
        this.aMovilidad = aMovilidad;
    }

    public Array<DatoTabla> getaCuarentena() {
        return aCuarentena;
    }

    public void setaCuarentena(Array<DatoTabla> aCuarentena) {
        this.aCuarentena = aCuarentena;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getPobSIR() {
        return pobSIR;
    }

    public void setPobSIR(int pobSIR) {
        this.pobSIR = pobSIR;
    }

    public int getInfSIR() {
        return infSIR;
    }

    public void setInfSIR(int infSIR) {
        this.infSIR = infSIR;
    }

    public int getVacSIR() {
        return vacSIR;
    }

    public void setVacSIR(int vacSIR) {
        this.vacSIR = vacSIR;
    }

    public float getR0SIR() {
        return R0SIR;
    }

    public void setR0SIR(float r0SIR) {
        R0SIR = r0SIR;
    }

    public int getTinfSIR() {
        return TinfSIR;
    }

    public void setTinfSIR(int tinfSIR) {
        TinfSIR = tinfSIR;
    }

    public float getMortalidadSIR() {
        return mortalidadSIR;
    }

    public void setMortalidadSIR(float mortalidadSIR) {
        this.mortalidadSIR = mortalidadSIR;
    }

    public Texture getT_heroeCaido() {
        return t_heroeCaido;
    }

    public void setT_heroeCaido(Texture t_heroeCaido) {
        this.t_heroeCaido = t_heroeCaido;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getBono() {
        return bono;
    }

    public void setBono(int bono) {
        this.bono = bono;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Rectangle getRo() {
        return ro;
    }

    public void setRo(Rectangle ro) {
        this.ro = ro;
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public Array<DatoTabla> getaEnfermos0() {
        return aEnfermos0;
    }

    public void setaEnfermos0(Array<DatoTabla> aEnfermos0) {
        this.aEnfermos0 = aEnfermos0;
    }

    public Array<DatoTabla> getaSanos0() {
        return aSanos0;
    }

    public void setaSanos0(Array<DatoTabla> aSanos0) {
        this.aSanos0 = aSanos0;
    }

    public Array<DatoTabla> getaCurados0() {
        return aCurados0;
    }

    public void setaCurados0(Array<DatoTabla> aCurados0) {
        this.aCurados0 = aCurados0;
    }

    public Array<DatoTabla> getaVacunados0() {
        return aVacunados0;
    }

    public void setaVacunados0(Array<DatoTabla> aVacunados0) {
        this.aVacunados0 = aVacunados0;
    }

    public Array<DatoTabla> getaMuertos0() {
        return aMuertos0;
    }

    public void setaMuertos0(Array<DatoTabla> aMuertos0) {
        this.aMuertos0 = aMuertos0;
    }

    public Array<DatoTabla> getaMovilidad0() {
        return aMovilidad0;
    }

    public void setaMovilidad0(Array<DatoTabla> aMovilidad0) {
        this.aMovilidad0 = aMovilidad0;
    }

    public Array<DatoTabla> getaCuarentena0() {
        return aCuarentena0;
    }

    public void setaCuarentena0(Array<DatoTabla> aCuarentena0) {
        this.aCuarentena0 = aCuarentena0;
    }

    public Array<Actores> getAct0() {
        return act0;
    }

    public int getPoblacion0() {
        return poblacion0;
    }

    public int getCobertura0() {
        return cobertura0;
    }

    public int getEnfermos0() {
        return enfermos0;
    }

    public int getLetalidad0() {
        return letalidad0;
    }

    public int getInfectividad0() {
        return infectividad0;
    }

    public int getDias0() {
        return dias0;
    }

    public int getMeses0() {
        return meses0;
    }

    public int getAños0() {
        return años0;
    }

    public int getDias20() {
        return dias20;
    }
}
