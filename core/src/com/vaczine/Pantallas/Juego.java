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

package com.vaczine.Pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import com.vaczine.Actores.DatoTabla;
import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Archivar;
import com.vaczine.Game.VaczineGame;
import com.vaczine.Game.Mundo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Juego implements Screen{
    private VaczineGame game;

    private ShapeRenderer caja, barrera, barra;
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private BitmapFont fuente_botones, fuente, fuenteAmarilla;
    private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
    private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_terminar,b_agregar,b_pausar,b_cuarentena, b_movilidad; //crea botones con texto similares a los de swing
    private TextButton b_m1, b_m2, b_m3,b_m4, b_m5, b_c0,b_c1,b_c2; //crea botones con texto similares a los de swing
    private Mundo m;
    private int verDato =-1; //0 ocultar 1 ver
    private int verMov = -1;  //-1 ocultar 1 ver
    private int verCarentena = -1;  //-1 ocultar 1 ver
    private TextButton b_verdatos;
    private float rangoX, rangoY;
    private int origenX;
    private float origenY;

    private Array<String> aStringParaEscribirDatos;


    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;


    public Juego(VaczineGame game) {
        this.game = game;

        Adshandler handler = game.getHandler();
        handler.showAds(false);

        m = game.getM();


        //setear el tiempo
        m.setDias(0);
        m.setMeses(0);
        m.setAños(0);
        m.setDelta();

        aStringParaEscribirDatos = new Array<>();
        //aStringParaEscribirDatos.add("S/vacunar;Enfermos;Curados;Muertos");


    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc
       ;


        caja = new ShapeRenderer();
        barrera = new ShapeRenderer();
        barra = new ShapeRenderer();

        rangoX = (float) 200/m.getDias2();
        rangoY = (float) 100/m.getAct().size;

        origenX = 850;
        origenY = 440;


        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente.setColor(255f/255f, 255f/255f, 135f/255f, 1);
        fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuenteAmarilla = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuenteAmarilla.setColor(Color.YELLOW);

        batch = game.getBatch();//new SpriteBatch(); //se usa para dibujar en la pantalla
        //crear la camara
        camera = new OrthographicCamera(ancho, alto);
        //crear el viewport
        viewport = new StretchViewport(ancho, alto); //no lo estoy usando ahora
        // viewport.setCamera(camara);

        //crear el stage


        ta_atlas = new TextureAtlas("pack31.pack");//carga el atlas de texturas donde estan los botones
        sk_skin = new Skin();
        sk_skin.addRegions(ta_atlas);

        //instansia los elemrntos de un boton
        //la posocion up y down usando imagenes y el texto que tiene cada uno

        TextButtonStyle estilo = new TextButtonStyle();
        estilo.up = sk_skin.getDrawable("boton_up");
        estilo.down = sk_skin.getDrawable("boton_down");
        estilo.font = fuente_botones;

        b_verdatos = new TextButton(game.getTx().getVerOcultar(), estilo);
        b_cuarentena = new TextButton(game.getTx().getCuarentena(), estilo);
        b_movilidad = new TextButton(game.getTx().getMovilidad(), estilo);
        b_agregar = new TextButton(game.getTx().getAgregarEnfermo(), estilo);
        b_pausar = new TextButton(">/=", estilo);
        b_terminar = new TextButton(game.getTx().getTerminar(), estilo);
        //botones para regular la movilidad
        b_m1 = new TextButton("100 %", estilo);
        b_m2 = new TextButton("80 %", estilo);
        b_m3 = new TextButton("60 %", estilo);
        b_m4 = new TextButton("40 %", estilo);
        b_m5 = new TextButton("20 %", estilo);

        b_m1.setVisible(false);
        b_m2.setVisible(false);
        b_m3.setVisible(false);
        b_m4.setVisible(false);
        b_m5.setVisible(false);

        //botones para regular la cuarentena

        b_c0 = new TextButton("L 0", estilo);
        b_c1 = new TextButton("L 1", estilo);
        b_c2 = new TextButton("L 2", estilo);


        b_c0.setVisible(false);
        b_c1.setVisible(false);
        b_c2.setVisible(false);



        //stage maneja elementos que reciben entradas como botones o eventos
            //en este caso se us apara los botones

            stage = new Stage();
            stage.clear();
            Gdx.input.setInputProcessor(stage);



            //instansia los elemrntos de un boton


            //instancia los botones
            m.addBoton(b_verdatos,0.6f,1f,170,60,85,540);
            m.addBoton(b_cuarentena,0.6f,1f,170,60,255,540);
            m.addBoton(b_movilidad,0.6f,1f,170,60,425,540);
            m.addBoton(b_agregar,0.45f,1f,170,60,595,540);
            m.addBoton(b_pausar,0.8f,1f,170,60,765,540);
            m.addBoton(b_terminar,0.6f,1f,170,60,935,540);

            m.addBoton(b_m1,0.8f,1f,100,60,390,480);
            m.addBoton(b_m2,0.8f,1f,100,60,500,480);
            m.addBoton(b_m3,0.8f,1f,100,60,610,480);
            m.addBoton(b_m4,0.8f,1f,100,60,720,480);
            m.addBoton(b_m5,0.8f,1f,100,60,830,480);

            m.addBoton(b_c0,1f,1f,100,60,220,480);
            m.addBoton(b_c1,1f,1f,100,60,270,420);
            m.addBoton(b_c2,1f,1f,100,60,320,360);



            stage.addActor(b_verdatos);
            stage.addActor(b_cuarentena);
            stage.addActor(b_movilidad);
            stage.addActor(b_terminar);
            stage.addActor(b_pausar);
            stage.addActor(b_agregar);

            stage.addActor(b_m1);
            stage.addActor(b_m2);
            stage.addActor(b_m3);
            stage.addActor(b_m4);
            stage.addActor(b_m5);

            stage.addActor(b_c0);
            stage.addActor(b_c1);
            stage.addActor(b_c2);



            //se agregan los listener para los botones

            b_verdatos.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    m.playClick();

                    verDato = verDato*(-1) ;
                }});

            b_cuarentena.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

               /* verMov=-1;
                b_m1.setVisible(false);
                b_m2.setVisible(false);
                b_m3.setVisible(false);
                b_m4.setVisible(false);
                b_m5.setVisible(false);*/

                verCarentena = verCarentena*(-1);
                if(verCarentena==1){

                    b_c0.setVisible(true);
                    b_c1.setVisible(true);
                    b_c2.setVisible(true);
                }else{
                    b_c0.setVisible(false);
                    b_c1.setVisible(false);
                    b_c2.setVisible(false); }



            }});

            b_c0.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

               /* verCarentena = verCarentena*(-1);
                if(verCarentena==1){

                    b_c0.setVisible(true);
                    b_c1.setVisible(true);
                    b_c2.setVisible(true);
                }else{
                    b_c0.setVisible(false);
                    b_c1.setVisible(false);
                    b_c2.setVisible(false); }*/

                m.setCuarentena(0);
                DatoTabla dato= new DatoTabla(m.getDias2(),0);
                m.getaCuarentena().add(dato);
            }});

        b_c1.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();


               /* verCarentena = verCarentena*(-1);
                if(verCarentena==1){

                    b_c0.setVisible(true);
                    b_c1.setVisible(true);
                    b_c2.setVisible(true);
                }else{
                    b_c0.setVisible(false);
                    b_c1.setVisible(false);
                    b_c2.setVisible(false); }*/


                for (int i=0; i< m.getAct().size; i++) {

                    if(m.getAct().get(i).getEstado() ==1 ){

                        int xmax= 240;
                        int xmin=-100;
                        int ymax= 700;
                        int ymin= -100;
                        Vector2 pos = new Vector2((float) m.RandomInRange(xmin, xmax),(float) m.RandomInRange(ymin, ymax));

                        m.getAct().get(i).setPosicion(pos);
                    } else{

                        int xmax= 1124;
                        int xmin=260;
                        int ymax= 700;
                        int ymin= -100;
                        Vector2 pos = new Vector2((float) m.RandomInRange(xmin, xmax),(float) m.RandomInRange(ymin, ymax));

                        m.getAct().get(i).setPosicion(pos);

                    }

                }

                m.setCuarentena(1);
                DatoTabla dato= new DatoTabla(m.getDias2(),1);
                m.getaCuarentena().add(dato);
            }});


        b_c2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();


                /*verCarentena = verCarentena*(-1);
                if(verCarentena==1){

                    b_c0.setVisible(true);
                    b_c1.setVisible(true);
                    b_c2.setVisible(true);
                }else{
                    b_c0.setVisible(false);
                    b_c1.setVisible(false);
                    b_c2.setVisible(false); }*/


                for (int i=0; i< m.getAct().size; i++) {

                    if(m.getAct().get(i).getEstado() ==1 ){

                        int xmax= 240;
                        int xmin=-100;
                        int ymax= 700;
                        int ymin= -100;
                        Vector2 pos = new Vector2((float) m.RandomInRange(xmin, xmax),(float) m.RandomInRange(ymin, ymax));

                        m.getAct().get(i).setPosicion(pos);
                    } else{

                        int xmax= 1124;
                        int xmin=260;
                        int ymax= 700;
                        int ymin= -100;
                        Vector2 pos = new Vector2((float) m.RandomInRange(xmin, xmax),(float) m.RandomInRange(ymin, ymax));

                        m.getAct().get(i).setPosicion(pos);

                    }

                }

                m.setCuarentena(2);
                DatoTabla dato= new DatoTabla(m.getDias2(),2);
                m.getaCuarentena().add(dato);
            }});




             b_movilidad.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

            /* verCarentena= -1;
                b_c0.setVisible(false);
                b_c1.setVisible(false);
                b_c2.setVisible(false);
*/

              verMov = verMov*(-1);

             if(verMov == 1){
                 b_m1.setVisible(true);
               b_m2.setVisible(true);
                b_m3.setVisible(true);
                b_m4.setVisible(true);
             b_m5.setVisible(true);}
             else{

                 b_m1.setVisible(false);
                 b_m2.setVisible(false);
                 b_m3.setVisible(false);
                 b_m4.setVisible(false);
                 b_m5.setVisible(false);}



            }});

        b_m1.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

               // verMov = verMov*(-1);

                   m.setMovilidad(10);


                /*if(verMov == 1){
                    b_m1.setVisible(true);
                    b_m2.setVisible(true);
                    b_m3.setVisible(true);
                    b_m4.setVisible(true);
                    b_m5.setVisible(true);}
                else{

                    b_m1.setVisible(false);
                    b_m2.setVisible(false);
                    b_m3.setVisible(false);
                    b_m4.setVisible(false);
                    b_m5.setVisible(false);}*/
            }});
        b_m2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

               // verMov = verMov*(-1);

                    m.setMovilidad(8);

                /*

                if(verMov == 1){
                    b_m1.setVisible(true);
                    b_m2.setVisible(true);
                    b_m3.setVisible(true);
                    b_m4.setVisible(true);
                    b_m5.setVisible(true);}
                else{

                    b_m1.setVisible(false);
                    b_m2.setVisible(false);
                    b_m3.setVisible(false);
                    b_m4.setVisible(false);
                    b_m5.setVisible(false);}*/
            }});
        b_m3.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

              //  verMov = verMov*(-1);

                    m.setMovilidad(6);

            /*

                if(verMov == 1){
                    b_m1.setVisible(true);
                    b_m2.setVisible(true);
                    b_m3.setVisible(true);
                    b_m4.setVisible(true);
                    b_m5.setVisible(true);}
                else{

                    b_m1.setVisible(false);
                    b_m2.setVisible(false);
                    b_m3.setVisible(false);
                    b_m4.setVisible(false);
                    b_m5.setVisible(false);}*/
            }});

        b_m4.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                //verMov = verMov*(-1);

                    m.setMovilidad(4);

            /*

                if(verMov == 1){
                    b_m1.setVisible(true);
                    b_m2.setVisible(true);
                    b_m3.setVisible(true);
                    b_m4.setVisible(true);
                    b_m5.setVisible(true);}
                else{

                    b_m1.setVisible(false);
                    b_m2.setVisible(false);
                    b_m3.setVisible(false);
                    b_m4.setVisible(false);
                    b_m5.setVisible(false);}*/
            }});

        b_m5.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                //verMov = verMov*(-1);

                    m.setMovilidad(2);

                /*
                if(verMov == 1){
                    b_m1.setVisible(true);
                    b_m2.setVisible(true);
                    b_m3.setVisible(true);
                    b_m4.setVisible(true);
                    b_m5.setVisible(true);}
                else{

                    b_m1.setVisible(false);
                    b_m2.setVisible(false);
                    b_m3.setVisible(false);
                    b_m4.setVisible(false);
                    b_m5.setVisible(false);}*/
            }});

            b_terminar.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    m.playClick();
                  //  if(game.getMusicaOn()==1){ m.playMusic();}//musica de ambiente
                    m.contarTodo();

                    if(!game.isAndroid()){guardarDatosDeLaPartida();} //Ésta función es solo para PC

                    game.setScreen(new Resultado01(game));
                }});

            b_pausar.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    m.playClick();
                    m.setDelta();//
                    for (int i=0;i<m.getAct().size;i++) {

                        m.getAct().get(i).setTimeEnfermo();//
                    }
                    m.setPlay(m.getPlay()*(-1)) ;
                }});

            b_agregar.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    m.playClick();
                    m.agregarenfermo(1);

                }});




    }


    //manejo de archicos para la version de PC


    // colecta datos para graficar
    public void colectarDatos() {

        String datos = game.getTx().getPoblacionOriginal() +": "+ m.getPoblacion()+ "\n";
        aStringParaEscribirDatos.add(datos);
              datos = game.getTx().getCoberturaDeVacunacion() +": "+ m.getCobertura()+ " % \n";
        aStringParaEscribirDatos.add(datos);
            datos = game.getTx().getLetalidadDeLaEnfermedad()+": "+ m.getLetalidad()+ " % \n";
        aStringParaEscribirDatos.add(datos);
            datos = game.getTx().getInfectividadDeLaEnfermedad()+": "+ m.getInfectividad()+ "\n\n";
        aStringParaEscribirDatos.add(datos);

        datos = game.getTx().getDia()+";"+game.getTx().getMovilidad()+";"+game.getTx().getSanosSinVacunar()+";"+game.getTx().getVacunados()+";"+game.getTx().getEnfermos()+";"+game.getTx().getRecuperados()+";"+game.getTx().getMuertos()+"\n";
        aStringParaEscribirDatos.add(datos);

        for(int i =0 ; i < m.getaSanos().size; i++){

            aStringParaEscribirDatos.add(i+";");
            aStringParaEscribirDatos.add(m.getaMovilidad().get(i).getY()*10+";");
            aStringParaEscribirDatos.add(m.getaSanos().get(i).getY()+";");
            aStringParaEscribirDatos.add(m.getaVacunados().get(i).getY()+";");
            aStringParaEscribirDatos.add(m.getaEnfermos().get(i).getY()+";");
            aStringParaEscribirDatos.add(m.getaCurados().get(i).getY()+";");
            aStringParaEscribirDatos.add(m.getaMuertos().get(i).getY()+"\n");

        }



    }

    public void guardarDatosDeLaPartida(){

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

        colectarDatos();

        Archivar f_Datos = new Archivar();

        f_Datos.creararchivo( "Resultado_" +year+strMonth+strDay+strHours+strMinutes+strSecond+".csv");

        for(int i = 0; i< aStringParaEscribirDatos.size; i++){
            f_Datos.escribirArchivo(aStringParaEscribirDatos.get(i));
        }

        f_Datos.cerrarArchivo();

    }


    @Override
    public void render(float delta) {


        //Se encarga de dibujar la pantalla
        Gdx.gl.glClearColor(m.getColorFondo().r,m.getColorFondo().g,m.getColorFondo().b,m.getColorFondo().a);

       // Gdx.gl.glClearColor(0f/255f, 25f/255f, 100f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);


        camera.update(); //

        stage.getViewport().setCamera(camera);//el estage usará el vieport de la camara
        stage.act(delta);

        batch.setProjectionMatrix(camera.combined); //batch usa el punto de vista de la camara
        caja.setProjectionMatrix(camera.combined); // shape usa el punto de vista de la camara
        barrera.setProjectionMatrix(camera.combined); // shape usa el punto de vista de la camara
        barra.setProjectionMatrix(camera.combined); // shape usa el punto de vista de la camara

              //actores
        m.update();

        if(m.getPlay()==1) {m.contadorTiempo();}//cuenta el tiempo de juego
        if(m.getAños()==1){  //if(game.getMusicaOn()==1){ m.playMusic();}//musica de ambiente
                             m.contarTodo();
                             game.setScreen(new Resultado01(game)); }


        for (int i=0; i< m.getAct().size; i++) {
            if(m.getAct().get(i).getEstado()!= 4) {//si el actor no esta muerto
                m.getAct().get(i).update(delta);
                m.getAct().get(i).verActor(batch);
         if(game.debug==true){ m.getAct().get(i).verBorde(caja,fuente,batch, delta);} //se ve el rectangulo de cada personaje
            }
        }

        caja.begin(ShapeType.Filled);
        caja.setColor(Color.BLACK);
       // caja.rect(0, 540,ancho,60);

        caja.end();


        //barrera de la cuarentena

        fuenteAmarilla.getData().setScale(0.7f, 0.8f);
        fuente.getData().setScale(0.45f,0.5f);

        if(m.getCuarentena()==0){
            batch.begin();
            fuenteAmarilla.draw(batch, game.getTx().getCuarentena() + ": L0" ,520, 500);
            batch.end();
        }

        if(m.getCuarentena()== 1){

            barrera.begin(ShapeType.Filled);
            barrera.setColor(Color.WHITE);
            barrera.rect(255, 0,15,275);
            barrera.rect(255, 325,15,275);

            barrera.end();

            batch.begin();
            fuenteAmarilla.draw(batch, game.getTx().getCuarentena() + ": L1" ,520, 500);
           // fuente.draw(batch, "" + m.contarEnfermos(9)+ " personas",	10,60);
            fuente.draw(batch, game.getTx().getZonaDeCuarentena()+" L1",	10,30);
            batch.end();

        }

        if(m.getCuarentena()== 2){

            barrera.begin(ShapeType.Filled);
            barrera.setColor(Color.WHITE);
            barrera.rect(255, 0,15,alto);
            barrera.end();

            batch.begin();
            fuenteAmarilla.draw(batch, game.getTx().getCuarentena() + ": L2" ,520, 500);
            fuente.draw(batch, "" + m.contarEnfermos(m.getAct(),9)+ " "+game.getTx().getPersonas(),	10,60);
            fuente.draw(batch, game.getTx().getZonaDeCuarentena()+" L2",	10,30);
            batch.end();

        }

       // System.out.println(m.Xsize(100));



        if (verDato==1)	{
            caja.begin(ShapeType.Filled);
            caja.setColor(Color.BLACK);
            caja.rect(0, 380,250,220);
            caja.end();



            batch.begin();
            fuente.draw(batch, game.getTx().getPoblacion()+": " + m.contarPoblacion(m.getAct(),9),	10, 540);
            fuente.getData().setScale(0.4f,0.55f);
            fuente.draw(batch, game.getTx().getSanosvacunados()+": " + m.contarVacunados(m.getAct(),9),10, 515);
            fuente.draw(batch, game.getTx().getSanosSinVacunar()+": " + m.contarSanos(m.getAct(),9),	10, 490);
            fuente.getData().setScale(0.45f,0.55f);
            fuente.draw(batch, game.getTx().getEnfermos()+": " + m.contarEnfermos(m.getAct(),9),	10,465);
            fuente.draw(batch, game.getTx().getCurados()+": " + m.contarCurados(m.getAct(),9),	10, 440);
            fuente.draw(batch, game.getTx().getMuertos()+": " + m.contarMuertos(m.getAct(),9),	10, 415);

            if (game.debug == true) {fuente.draw(batch, "delta time: " +Gdx.graphics.getDeltaTime()*1000+ " ms",	10, 300);}

            batch.end();}


        //grafica las enfermos

        caja.begin(ShapeType.Filled);
        caja.setColor(Color.BLACK);
        caja.rect(850, 440,274,160);
        caja.end();

        barra.begin(ShapeType.Filled);

            barra.setColor(Color.RED);

            for (int i=0;i<m.getaEnfermos().size;i++) {
                int dias = (int) (origenX+ i);
                float enfermos = (float)rangoY*(m.getaEnfermos().get(i).getY());
                barra.rect(dias, origenY,2,enfermos);
            }
      /*  barra.setColor(Color.WHITE);

        for (int i=0;i<m.getaCuarentena().size;i++) {
            int dias = (int) (origenX+ m.getaCuarentena().get(i).getX());
            float enfermos = 200;
            barra.rect(dias, origenY,1,enfermos);
            System.out.println(m.getaCuarentena().get(i).getX());
        }*/

        barra.end();


        //tiempo
        batch.begin();
        fuenteAmarilla.draw(batch, "|"+game.getTx().getAnos()+": " + m.getAños() + " |"+game.getTx().getMeses()+": " + m.getMeses() + " |"+game.getTx().getDias()+": " + m.getDias(),
                250, 540);

        fuenteAmarilla.draw(batch, "|"+game.getTx().getMovilidad()+": " + m.getMovilidad()*10 + " %",250, 500);

        batch.end();
        //botones
        batch.begin();
        stage.draw();//dibuja los botones definidos en resize
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height);// actualiza el stage para que no camve las proporciones de los elementos
        camera.setToOrtho(false,ancho, alto); // ajusta la camara para el ancho y alto predefinido



    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    dispose();
    }

    @Override
    public void dispose() {
        sk_skin.dispose();
        ta_atlas.dispose();
        stage.dispose();
        fuente_botones.dispose();
        fuenteAmarilla.dispose();
        fuente.dispose();
      //  batch.dispose();

    }



}
