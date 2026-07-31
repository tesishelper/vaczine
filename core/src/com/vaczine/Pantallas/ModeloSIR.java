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
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Actores.Actores;
import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Archivar;
import com.vaczine.Game.ControlSIR;
import com.vaczine.Game.Controles;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ModeloSIR implements Screen{
    private VaczineGame game;

    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private BitmapFont fuente_boton, fuente;
    private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
    private Texture screenshot, txS,txI, txIa, txC;
    private Skin sk_skin, sk_skin2;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_menu; //crea botones con texto similares a los de swing

    private Mundo m;
    private TextButton b_capturar,b_callar,b_si, b_no,b_ok,b_terminar,b_FF,b_pausar,b_escalaX,b_escalaY,b_RoUP,b_RoDow,b_setR0;
    private float rangoX, rangoY;
    private ShapeRenderer caja, linea, linea2;
    private CheckBox cb_verSanos;
    private TextureAtlas ta_atlas2;
    private TextureAtlas ta_actores;//carga imagenes de atlas de texturas
    private CheckBox cb_verEnfermos;
    private CheckBox cb_verVacunados;
    private CheckBox cb_verCurados;
    private CheckBox cb_verMuertos;
    private CheckBox cb_log10;
    private Sprite sp_muerto,spM;
    private TextureAtlas ta_muerto;
    private Sprite ac_sano;
    private Sprite ac_infectado;
    private Sprite ac_vacunado;
    private Sprite ac_recuperado;
    private int origenX;
    private float origenY;

    private Vector2 posLinea;
    private boolean verLinea;
    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;

    private int escalaX=1;
    private int escalaY=1;

    private int callar = 1;
    String partida = "";

    private DecimalFormat format, format2,format3, format4,format5;

    private int intento =0;

    private ControlSIR controlSIR;
    private InputMultiplexer inputMultiplexer;

    //variables y Arreglos para hacer los calculos
    private int speed = 1;
    private float N = 0;
    private float S,S1,S2 = 0;
    private float I,I1,I2 = 0;
    private float Ia,Ia1,Ia2 = 0;
    private float R, R1,R2 = 0;
    private float R0 =0;
    private float R01 =0;
    private float R02 =0;
    private int Tinf = 0;
    private float mort = 0;

    private int ImaxT = 0;  // tiempo donde sea alcanza el pico de enfermos
    private float Imax =0;   //cantidad maxima de enfermos en su pico

    private Array<Float> ar0,aS1,aS2,aS3,aS4,aI1,aI2,aI3,aI4,aIa1,aIa2,aIa3,aIa4,aR1,aR2,aR3,aR4;
    private Array<Integer> at,ac;

    private boolean salir = false;
    //colore

    Color colorInfectado, colorRecuperado;

    private Array<String> aStringParaEscribirDatos;

    private boolean vercamara = true;


    public ModeloSIR(VaczineGame game) {
        this.game = game;


        Adshandler handler = game.getHandler();
        handler.showAds(false);

        m = game.getM2();

        partida = game.getTictacRuta();

        aStringParaEscribirDatos = new Array<>();

        posLinea = new Vector2();

        N= m.getPobSIR();
        S= N - (N*m.getVacSIR()/100)-m.getInfSIR();
        S1= S;
        S2= S;
        I = m.getInfSIR();
        I1 = I;
        I2= I;
        Ia = I;
        Ia1 = I;
        Ia2 = I;
        Imax = I;
        R0 = m.getR0SIR();
        R01= R0;
        R02= R0;
        Tinf = m.getTinfSIR();
        mort = m.getMortalidadSIR();

        at  = new Array<Integer>();
        ac = new Array<Integer>();
        ar0 = new Array<Float>();
        aS1 = new Array<Float>();
        aS2 = new Array<Float>();
        aS3 = new Array<Float>();
       // aS4 = new Array<Float>();
        aI1 = new Array<Float>();
        aI2 = new Array<Float>();
        aI3 = new Array<Float>();
       // aI4 = new Array<Float>();
        aIa1 = new Array<Float>();
        aIa2 = new Array<Float>();
        aIa3 = new Array<Float>();
       // aIa4 = new Array<Float>();
        aR1 = new Array<Float>();
        aR2 = new Array<Float>();
        aR3 = new Array<Float>();
       // aR4 = new Array<Float>();

        //setear el tiempo
        m.setDias2(0);
        m.setMeses(0);
        m.setAños(0);
        m.setDelta(); //para el contador de dias
        m.setDelta5();//para el programa que ejecuta los calcultos

        at.add(m.getDias2());
        aS1.add(S);
        aS2.add(S);
        aS3.add(S);
       // aS4.add(S);
        aI1.add(I);
        aI2.add(I);
        aI3.add(I);
       // aI4.add(I);
        aIa1.add(Ia);
        aIa2.add(Ia1);
        aIa3.add(Ia2);
       // aIa4.add(Ia3);
        aR1.add(0f);
        aR2.add(0f);
        aR3.add(0f);
      //  aR4.add(0f);
        ac.add(m.getDias2());
        ar0.add(R0);


        leerSonido();




    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc

        format = new DecimalFormat("###,###.##");
        format2 = new DecimalFormat("0.00E0");
        format3 = new DecimalFormat("00.00");
        format4 = new DecimalFormat("#0.00");
        format5 = new DecimalFormat("###,###");


        caja = new ShapeRenderer();
        linea = new ShapeRenderer();
        linea2 = new ShapeRenderer();
        screenshot = new Texture("screenshot.png");//imagen
      //  imgR = new Texture("fondoRecortado.png");//imagen

        //colore

        colorInfectado = new Color(Color.BROWN );
        colorRecuperado = new Color(Color.BLUE);



        ta_muerto = new TextureAtlas("actores.pack");//imagen
        sp_muerto = new Sprite(ta_muerto.findRegion("muerto"));//sprite para usar la imagen con mas libertad
        spM = new Sprite(ta_muerto.findRegion("muerto"));
        txS = new Texture("sanos.png");
        txI = new Texture("infectados.png");
        txIa = new Texture("infectadosA.png");
        txC = new Texture("curados.png");




        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente_boton = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);//
        batch = game.getBatch();//new SpriteBatch(); //se usa para dibujar en la pantalla

        //crear la camara
        camera = new OrthographicCamera(ancho, alto);
        //crear el viewport
        viewport = new StretchViewport(ancho, alto); //no lo estoy usando ahora
        // viewport.setCamera(camara);


        ta_atlas = new TextureAtlas("pack31.pack");//carga el atlas de texturas donde estan los botones
        sk_skin = new Skin();
        sk_skin.addRegions(ta_atlas);


        //instansia los elemrntos de un boton
        //la posocion up y down usando imagenes y el texto que tiene cada uno

        TextButtonStyle estilo = new TextButtonStyle();
        estilo.up = sk_skin.getDrawable("boton_up");
        estilo.down = sk_skin.getDrawable("boton_down");
        estilo.font = fuente_boton;

        fuente.getData().setScale(1,1);


        CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
        checkBoxStyle.checkboxOff = sk_skin.getDrawable("check_box_off");
        checkBoxStyle.checkboxOn = sk_skin.getDrawable("check_box_on");
        checkBoxStyle.font = fuente;




        cb_log10 = new CheckBox("", checkBoxStyle);
        cb_verSanos = new CheckBox("", checkBoxStyle);

        cb_verCurados = new CheckBox("", checkBoxStyle);
        cb_verEnfermos = new CheckBox("", checkBoxStyle);
        cb_verMuertos = new CheckBox("", checkBoxStyle);


        b_ok = new TextButton("OK", estilo);
        b_ok.setVisible(false);

        b_terminar = new TextButton(game.getTx().getSalir(), estilo);
        b_pausar = new TextButton(game.getTx().getPausa(), estilo);
        b_FF = new TextButton(">> "+ speed+"X", estilo);
        b_capturar = new TextButton("", estilo);
        b_callar = new TextButton("Callar", estilo);
        if(callar==1){b_callar.setText("TicTac "+game.getTx().getNo());}
        if(callar==-1){b_callar.setText("TicTac "+game.getTx().getSi());}
        b_escalaY = new TextButton(game.getTx().getEscala()+" Y", estilo);
        b_escalaX = new TextButton(game.getTx().getEscala()+" X", estilo);
        b_setR0 = new TextButton(game.getTx().getCambiar()+" R0", estilo);

        b_RoUP = new TextButton("R0 >>", estilo);
        b_RoUP.setVisible(false);
        b_RoDow = new TextButton("<< R0", estilo);
        b_RoDow.setVisible(false);

        b_si = new TextButton(game.getTx().getSi(), estilo);
        b_si.setVisible(false);
        b_no = new TextButton("No", estilo);
        b_no.setVisible(false);

// instanciar actores para ver
        ta_actores = m.getTa_actores(); //tomamos las texturas de la clase mundo


        int h = 60; int w = 25;
        int acx= 780;


        ac_sano= new Sprite(ta_actores.findRegion("sano"));
        ac_sano.setSize(w,h);
        ac_sano.setPosition(acx,520);
        ac_infectado= new Sprite(ta_actores.findRegion("enfermo"));
        ac_infectado.setSize(w,h);
        ac_infectado.setPosition(acx,420);
        ac_recuperado=new Sprite(ta_actores.findRegion("curado"));
        ac_recuperado.setSize(w,h);
        ac_recuperado.setPosition(acx,320);
        sp_muerto.setSize(30,30);
        sp_muerto.setPosition(acx,230);



        //stage maneja elementos que reciben entradas como botones o eventos
        //en este caso se us apara los botones

        //controles para mover la pieza comodin
        stage = new Stage();
        stage.clear();

        controlSIR = new ControlSIR(this, camera); //instancia los controles
        inputMultiplexer = new InputMultiplexer();//esto es para manejar multiples controles

        //Agregar los contrloles del stage y de tocar la pantalla

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(controlSIR);

        Gdx.input.setInputProcessor(inputMultiplexer);


/*

        Gdx.input.setInputProcessor(stage);

 */

        //

        //calcula el rango y posicion de los puntos de x e y

        rangoX = (float) 800/720;
        rangoY = (float) 410/N;

        origenX = 85;
        origenY = 130;

        int cbx = 785;

        cb_log10.getImage().setScaling(Scaling.fill);
        cb_log10.getCells().get(0).size(30, 30);
        cb_log10.setChecked(true);
        cb_log10.setPosition(105 ,540);

        cb_verSanos.getImage().setScaling(Scaling.fill);
        cb_verSanos.getCells().get(0).size(30, 30);
        cb_verSanos.setChecked(false);
        cb_verSanos.setPosition(cbx ,465);


        cb_verEnfermos.getImage().setScaling(Scaling.fill);
        cb_verEnfermos.getCells().get(0).size(30,30);
        cb_verEnfermos.setChecked(true);
        cb_verEnfermos.setPosition(cbx ,420);


        cb_verCurados.getImage().setScaling(Scaling.fill);
        cb_verCurados.getCells().get(0).size(30,30);
        cb_verCurados.setChecked(false);
        cb_verCurados.setPosition(cbx , 270);


        cb_verMuertos.getImage().setScaling(Scaling.fill);
        cb_verMuertos.getCells().get(0).size(30,30);
        cb_verMuertos.setChecked(false);
        cb_verMuertos.setPosition(cbx, 205);

        //instancia los botones

        m.addBoton(b_terminar,0.65f,1f,100,70,50,0);

        m.addBoton(b_pausar,0.8f,1f,150,70,200,0);
        m.addBoton(b_FF,0.9f,1f,150,70,350,0);
        m.addBoton(b_capturar,0.4f,1f,150,70,500,0);
        m.addBoton(b_escalaY,0.6f,1f,150,70,649,0);
        m.addBoton(b_escalaX,0.6f,1f,150,70,799,0);


        m.addBoton(b_setR0,0.6f,1f,150,70,949,0);
        m.addBoton(b_RoDow,0.9f,1f,150,70,300,290);
        m.addBoton(b_ok,0.9f,1f,150,70,450,290);
        m.addBoton(b_RoUP,0.9f,1f,150,70,600,290);

        m.addBoton(b_si,0.9f,1f,150,70,350,290);

        m.addBoton(b_no,0.9f,1f,150,70,550,290);


        stage.addActor(b_terminar);
        stage.addActor(b_pausar);
        stage.addActor(b_FF);
        stage.addActor(b_capturar);
        stage.addActor(b_escalaX);
        stage.addActor(b_escalaY);
        stage.addActor(b_setR0);
        stage.addActor(b_RoUP);
        stage.addActor(b_RoDow);
        stage.addActor(b_ok);
        stage.addActor(b_si);
        stage.addActor(b_no);



        stage.addActor(cb_log10);
        stage.addActor(cb_verSanos);
        stage.addActor(cb_verCurados);
        stage.addActor(cb_verMuertos);
        stage.addActor(cb_verEnfermos);




        //se agregan los listener para los botones

        b_pausar.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
                m.setDelta();
                m.setDelta5();
                for (int i=0;i<m.getAct().size;i++) {

                    m.getAct().get(i).setTimeEnfermo();//
                }
                m.setPlay(m.getPlay()*(-1)) ;



            }});

        b_FF.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                speed = speed + 1;

                if (speed>5){speed = 1;}

              b_FF.setText(">> "+ speed+"X");

            }});

        /*b_callar.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

               callar = callar*(-1);
                if(callar==1){b_callar.setText("TicTac "+game.getTx().getNo());}
                if(callar==-1){b_callar.setText("TicTac "+game.getTx().getSi());}

               guardarSonido();

            }});

         */

        b_capturar.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                b_terminar.setVisible(false);
                b_pausar.setVisible(false);
                b_FF .setVisible(false);
                b_capturar.setVisible(false);
                b_escalaY.setVisible(false);
                b_escalaX.setVisible(false);
                b_setR0.setVisible(false);
                vercamara = false;

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                m.playClick();
                game.getScreenShot().screenShot(game.getTx().getGrafico()+"SIR");

                b_terminar.setVisible(true);
                b_pausar.setVisible(true);
                b_FF .setVisible(true);
                b_capturar.setVisible(true);
                b_escalaY.setVisible(true);
                b_escalaX.setVisible(true);
                b_setR0.setVisible(true);

                vercamara = true;


            }});




        b_escalaY.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

               escalaY = escalaY*2;

               if(N<=50){if (escalaY>1){escalaY=1;}}
               if(N>50 && N<=100){if (escalaY>4){escalaY=1;}}
               if(N>100 && N<=500){if (escalaY>8){escalaY=1;}}
               if(N>500 && N<=1000){if (escalaY>32){escalaY=1;}}
               if(N>1000 && N<=10000){if (escalaY>64){escalaY=1;}}
                if(N>10000){if (escalaY>256){escalaY=1;}}

            b_escalaY.setText("y= "+ escalaY+ "X");

               // System.out.println("escala x= "+ escalaX);

            }});

        b_escalaX.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                m.playClick();

                escalaX = escalaX*2;

                //System.out.println("escala x= "+ escalaX);

                if (escalaX>32){escalaX=1;}

                b_escalaX.setText("x= "+ escalaX+ "X");


            }});

        b_setR0.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                m.setPlay(0);
                b_ok.setVisible(true);
                b_RoDow.setVisible(true);
                b_RoUP.setVisible(true);

            }});

        b_RoUP.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

             R0 = R0 + 0.1f;
             if(R0> 20f){R0=20f;}

            }});

        b_RoDow.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

               R0 = R0 - 0.1f;

                if(R0*S/N < 0){R0=0f;}

            }});

        b_ok.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                if(intento==0){R01=R0;} //la curava 1 acompaña a la curava 0
                intento = intento +1;
               ac.add(m.getDias2()); //agrego el momento en que cambi el R0
               ar0.add(R0*S/N);         //agrego el nuevo valor de R0
               m.setPlay(1);
                b_ok.setVisible(false);
                b_RoDow.setVisible(false);
                b_RoUP.setVisible(false);

            }});


        b_terminar.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

              // if(m.getPlay()!= 3){
                m.setPlay(-1);
                salir= true;
                b_si.setVisible(true);
                b_no.setVisible(true);
            //}

               //if(m.getPlay()==3){

                 //  game.setScreen(new ModSirMenu(game));

             //  }


            }});

        b_si.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                if(!game.isAndroid()){guardarDatosDeLaPartida();} //Ésta función es solo para PC

               game.setScreen(new ModSirMenu(game));

            }});

        b_no.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
                salir = false;
                m.setPlay(1);
                b_si.setVisible(false);
                b_no.setVisible(false);

               // b_pausar.setText(game.getTx().getPausa());


            }});



        if(game.isSirRapido()){
            m.contadorTiempoSIR(speed);
            correrPandemiaRapida();}



    }


    //comando para controlar al personaje

   public void controles(){

    if (Gdx.input.isTouched()) {

        if(m.getPlay()!=0 && m.getPlay()!=2 && m.getPlay()!=3 && Gdx.input.getX()>origenX && Gdx.input.getX()< origenX+700 && alto-Gdx.input.getY()> origenY){

        posLinea.set(Gdx.input.getX(), alto-Gdx.input.getY());
            verLinea = true;
        }



     }

       if (!Gdx.input.isTouched()) {

           verLinea = false;

       }



    }


    //manejo de archicos para la version de PC


    // colecta datos para graficar
    public void colectarDatos() {

        String datos = game.getTx().getPoblacionTotal() +": "+ m.getPobSIR()+ "\n";
        aStringParaEscribirDatos.add(datos);
        datos = game.getTx().getCoberturaDeVacunacion() +": "+ m.getVacSIR() + " %\n";
        aStringParaEscribirDatos.add(datos);
        datos = game.getTx().getInfectados()+": "+ m.getInfSIR()+ "\n";
        aStringParaEscribirDatos.add(datos);
        datos = game.getTx().getNumeroBasicoDeReproduccion()+": "+ m.getR0SIR()+ "\n";
        aStringParaEscribirDatos.add(datos);
        datos = game.getTx().getTiempoDeInfeccion()+": "+ m.getTinfSIR()+ "\n";
        aStringParaEscribirDatos.add(datos);
        datos = game.getTx().getMortalidad()+": "+ m.getMortalidadSIR()+ "\n\n";
        aStringParaEscribirDatos.add(datos);

        datos = game.getTx().getDia()+";"+"  Rt  "+";"+game.getTx().getSusceptibles() +";"+game.getTx().getEnfermos()+";"+game.getTx().getRecuperados()+";"+game.getTx().getMuertos()+"\n";
        aStringParaEscribirDatos.add(datos);

        for(int i =0 ; i < aS1.size; i++){

            aStringParaEscribirDatos.add(i+";");
            aStringParaEscribirDatos.add(format3.format(R0*aS1.get(i)/N)+";");
            aStringParaEscribirDatos.add(Math.round(aS1.get(i))+";");
            aStringParaEscribirDatos.add(Math.round(aI1.get(i))+ ";");
            aStringParaEscribirDatos.add(Math.round((aR1.get(i)*(100-mort)/100))+";");
            aStringParaEscribirDatos.add(Math.round((aR1.get(i)*(mort)/100))+"\n");

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

        f_Datos.creararchivo( "ResultadoSIR_" +year+strMonth+strDay+strHours+strMinutes+strSecond+".csv");

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

        camera.update(); //

        //vamos a hacer que todos los puntos entren detro del grafico
        float propX = (350f/360f)*escalaX; //esto ajusta la posicion de los puntos de x con el tamaño de grafico
        float propY = rangoY*escalaY; //esto ajusta la posicion de los puntos de y con el tamaño de grafico

        if(m.getDias2()>=720 || (int ) I < 1 ) {m.setPlay(-1);} // salir del juego




        if(m.getPlay()==-1){b_pausar.setText("Play");}
        if(m.getPlay()== 1){b_pausar.setText(game.getTx().getPausa());}


        if(m.getPlay()==1) {

            if(!game.isSirRapido()){ correrPandemia(speed);}
           }//cuenta el tiempo de juego


        stage.getViewport().setCamera(camera);//el estage usará el vieport de la camara
        stage.act(delta);

        batch.setProjectionMatrix(camera.combined); //batch usa el punto de vista de la camara

        caja.setProjectionMatrix(camera.combined); //caja usa el punto de vista de la camara
        linea.setProjectionMatrix(camera.combined);
        linea2.setProjectionMatrix(camera.combined);



        fuente.setColor(Color.WHITE);


        // dibujar los ejes
        caja.begin(ShapeType.Filled);
        caja.setColor(Color.WHITE);
        caja.rect(origenX, origenY,2, 440);//eje de las y
        caja.rect(origenX, origenY,  700, 2); //eje de las x
        caja.end();

   fuente.getData().setScale(0.4f, 0.4f);






/////////////////////////////////////////////////////////////////////////////////////


        //graficar la lineas que marca R0

        for(int i=0; i < ac.size; i++){

            int dias = (int) (origenX + ac.get(i)*propX);
            float R0 = ar0.get(i);
            linea.begin(ShapeType.Line);
            linea.setColor(Color.WHITE);
            linea.line(dias,origenY,dias,origenY+300);
            linea.end();
            batch.begin();
            fuente.draw(batch,"Ro: "+ format3.format(R0),dias+3, origenY+170);
            batch.end();
        }

        //grafica los sanos sin vacunar
        if (cb_verSanos.isChecked()==true) {


            graficarLinea(aS3,propX,propY,Color.WHITE); //linea de puntos
            graficarLinea(aS2,propX,propY,Color.WHITE); //linea de puntos
            graficarLinea(aS1,propX,propY,Color.WHITE); //linea de puntos

            float sanos =0;
            int diax = (int)(origenX+ m.getDias2()*propX);//cordenada x para escrivir el R

            for (int i=0;i<aS1.size;i++) {
                int dias = (int) (origenX + at.get(i)*propX);


            if(cb_log10.isChecked()==false){ sanos = origenY + aS1.get(i)*propY;}
            if(cb_log10.isChecked()==true){ sanos = origenY + escalaLog10(aS1.get(i)*propY);}

            if(sanos<600){
                batch.begin();
                batch.draw(txS,dias-3,sanos-3,6,6);

                batch.end();}


            }}

        //grafica las enfermos
        if (cb_verEnfermos.isChecked()==true) {

            graficarLinea(aI3,propX,propY,colorInfectado); //linea de puntos
            graficarLinea(aI2,propX,propY,colorInfectado); //linea de puntos
            graficarLinea(aI1,propX,propY,colorInfectado); //linea de puntos

            graficarLinea(aIa3,propX,propY,Color.YELLOW); //linea de puntos
            graficarLinea(aIa2,propX,propY,Color.YELLOW); //linea de puntos
            graficarLinea(aIa1,propX,propY,Color.YELLOW); //linea de puntos

            float infectados = 0;
            float infectadosA = 0;

            for (int i=0;i<aIa1.size;i++) {
                int dias = (int) (origenX+ at.get(i)*propX);

            if(cb_log10.isChecked()==false){
                infectados = origenY+ aI1.get(i)*propY;
                infectadosA = origenY+ aIa1.get(i)*propY;
                }
            if(cb_log10.isChecked()==true){
                infectados = origenY+ escalaLog10(aI1.get(i)*propY);
                infectadosA = origenY+ escalaLog10(aIa1.get(i)*propY);
            }
              batch.begin();
           if(infectadosA<600){ batch.draw(txIa,dias-3,infectadosA-3,6,6);}
           if(infectados<600){batch.draw(txI,dias-3,infectados-3,6,6);}
               batch.end();

                }

        }

        //grafica las curados
        if (cb_verCurados.isChecked()==true) {

            graficarLineaCurados(aR3,propX,propY,colorRecuperado);
            graficarLineaCurados(aR2,propX,propY,colorRecuperado);
            graficarLineaCurados(aR1,propX,propY,colorRecuperado);

            float curados=0;
            for (int i = 0; i < aR1.size; i++) {
                int dias = (int) (origenX + at.get(i) * propX);

           if(cb_log10.isChecked()==false){curados=origenY+(aR1.get(i)*(100-mort)/100)*propY;}
           if(cb_log10.isChecked()==true){curados=origenY+escalaLog10((aR1.get(i)*(100-mort)/100)*propY);}

             if(curados<600){
                batch.begin();
                batch.draw(txC, dias-3, curados-3, 6, 6);
                batch.end();
                            }
            }}
        //grafica las muertos
        if (cb_verMuertos.isChecked()==true) {

            graficarLineaMuertos(aR3,propX,propY,Color.RED);
            graficarLineaMuertos(aR2,propX,propY,Color.RED);
            graficarLineaMuertos(aR1,propX,propY,Color.RED);


           float muertos =0;
            for (int i=0;i<aR1.size;i++) {

              int dias = (int) (origenX+ at.get(i)*propX);

             if(cb_log10.isChecked()==false){muertos = origenY+ (aR1.get(i)*(mort)/100)*propY;}
             if(cb_log10.isChecked()==true){muertos = origenY+ escalaLog10((aR1.get(i)*(mort)/100)*propY);}

               spM.setSize(8,8);
               spM.setPosition(dias-4,muertos-4);

            if(muertos<600){
                batch.begin();
                spM.draw(batch);
                batch.end();
                }

            }}


        //grafical los números del eje y
        batch.begin();
        int redondeado= (int)(N/escalaY); //el rango maximo es 100%



        int subdiv= 10;


        for(int i=0; i<=redondeado;i= i+(redondeado/subdiv)) {

            if(cb_log10.isChecked()==false){float personas = (origenY+10)+ i*propY;
                if (i<10){fuente.draw(batch, format.format(i), 60,personas);}
                if (i>=10 && i <100){fuente.draw(batch, format.format(i), 50,personas);}
                if (i >=100 && i <1000){fuente.draw(batch, format.format(i), 40,personas);}
                if (i >=1000 && i <10000){fuente.draw(batch, format.format(i), 30,personas);}
                if (i >=10000){fuente.draw(batch, format2.format(i), 20,personas);}}



            if(cb_log10.isChecked()==true){
                subdiv=5;
                float personas = (origenY+10)+ escalaLog10(i*propY);

                if(i<=10000){
                    fuente.draw(batch, format.format(1), 70,(origenY+10)+escalaLog10(1*propY));
                    fuente.draw(batch, format.format(10), 60,(origenY+10)+escalaLog10(10*propY));
                    fuente.draw(batch, format.format(100), 50,(origenY+10)+escalaLog10(100*propY));
                    fuente.draw(batch, format.format(1000), 35,(origenY+10)+escalaLog10(1000*propY));
                    fuente.draw(batch, format.format(10000), 25,(origenY+10)+escalaLog10(10000*propY));}

                if (i >10000){fuente.draw(batch, format2.format(i), 20,personas);}}

        }

        batch.end();

        // dibujar las mascaras
        caja.begin(ShapeType.Filled);
        caja.setColor(m.getColorFondo().r,m.getColorFondo().g,m.getColorFondo().b,m.getColorFondo().a);
     //   caja.setColor(0f/255f, 25f/255f, 100f/255f, 1);
        caja.rect(0, 552,ancho, 300);//mascara superior
        caja.rect(800, 0,  500, alto); //mascar derecha
        caja.end();

    //////////////////////////////////////////////////////////////////////////////////

        //graficar los datos de los ejes
        fuente.getData().setScale(0.45f, 0.45f);

        batch.begin();




        // graficar los numeros del eje x (días)

        int redondeado2= 700/escalaX; //el rango maximo es 100%
        int subdiv2= 10;


        for(int i=0; i<=redondeado2;i= i+(redondeado2/subdiv2)) {

            float dias = (origenX)+ (i*escalaX);

            if (i<10){fuente.draw(batch, ""+ i, dias-5,125);}
            if (i>=10 && i <100){fuente.draw(batch, ""+ i, dias-10,125);}
            if (i >=100){fuente.draw(batch, ""+ i, dias-20,125);}
        }

        fuente.draw(batch, "x = "+game.getTx().getDias(), 390,100);

        fuente.draw(batch, "y = "+game.getTx().getPersonas(), 5,595);
        fuente.draw(batch, "log10", 150,570);

        batch.end();

//////////////////////////////////////////////////////////////////////////////////////




        //tiempo
        fuente.getData().setScale(0.7f,0.7f);

        batch.begin();
        fuente.draw(batch, "|"+game.getTx().getDias()+": " + m.getDias2()  ,250, 595);

        if(R0*S/N > 1){fuente.draw(batch,  "|R0: "+format3.format(R0*S/N) +" ("+game.getTx().getEpidemia()+")" , 450, 595);}
        if(R0*S/N == 1){fuente.draw(batch,  "|R0: "+format3.format(R0*S/N) +" ("+game.getTx().getEndemia()+")" , 450, 595);}
        if(R0*S/N < 1){fuente.draw(batch,  "|R0: "+format3.format(R0*S/N) +" ("+game.getTx().getRemision()+")" , 450, 595);}


        if(m.getDias2()>=720 || (int ) I < 1 ) {
           fuente.getData().setScale(0.6f, 0.6f);
           fuente.draw(batch, game.getTx().getFinDeLaSimulacion(), 250, 570);

        } // salir del juego

        fuente.getData().setScale(0.5f,0.5f);

        //Poner el sombreado negro
        fuente.setColor(Color.BLACK);

        int xpos = 821; int xpos2 = 836;
        int deltaY = 1;
        fuente.draw(batch, game.getTx().getPoblacionOriginal()+":", xpos,590-deltaY );
        fuente.draw(batch, ""+ format.format((int)(N)), xpos2,570-deltaY );

        fuente.draw(batch, game.getTx().getVacunados()+":", xpos,540-deltaY);
        fuente.draw(batch, ""+ (int)(m.getVacSIR())+ " %", xpos2,520 -deltaY);
     //  fuente.setColor(Color.WHITE);
        fuente.draw(batch, game.getTx().getSusceptibles()+":", xpos,495 -deltaY);
        fuente.draw(batch, "t: "+ format.format((int)(S)), xpos2,475-deltaY);

       // fuente.setColor(colorInfectado);//.RED);
        fuente.draw(batch, game.getTx().getInfectados()+":", xpos,450 -deltaY);
        fuente.draw(batch, "t: "+ format.format((int)(I)), xpos2,430-deltaY );
        fuente.draw(batch, "d: "+ format.format(conteoDiario(aIa1)), xpos2,410 -deltaY);
        fuente.draw(batch, "max: "+ format.format((int)Imax), xpos2,390-deltaY );
        //fuente.setColor(Color.YELLOW);//.ORANGE);
        fuente.getData().setScale(0.45f,0.5f);
        fuente.draw(batch, game.getTx().getInfectadosAcumulados()+":", xpos,365-deltaY);
        fuente.getData().setScale(0.45f,0.5f);
        fuente.draw(batch, "t: "+ format.format((int)(Ia)), xpos2,345-deltaY );
        fuente.draw(batch, ""+ format4.format((Ia/N)*100)+ "% "+game.getTx().getPoblacion(), xpos2,325 -deltaY);
        //fuente.setColor(colorRecuperado);
        fuente.draw(batch, game.getTx().getRecuperados()+":", xpos,300 -deltaY);
        fuente.draw(batch, "t: "+ format.format((int)(R*(100-mort)/100)), xpos2,280-deltaY );
        fuente.draw(batch, "d: "+ format.format(curadosDiario(aR1)), xpos2,260-deltaY );
      //  fuente.setColor(Color.RED);//.RED);
        fuente.draw(batch, game.getTx().getMuertos()+":", xpos,235 -deltaY);
        fuente.draw(batch, "t: "+ format.format((int)(R*mort/100)), xpos2,215 -deltaY);
        fuente.draw(batch, "d: "+ format.format(muertosDiario(aR1)), xpos2,195 -deltaY);
       // fuente.setColor(Color.WHITE);
        fuente.getData().setScale(0.45f,0.5f);
        fuente.draw(batch, game.getTx().getDuplicaAproximadamenteCada(), 800+2,165 -deltaY);
        fuente.getData().setScale(0.45f,0.5f);

        float dias = duplicacionT();

        if(dias>0){fuente.draw(batch, ""+ dias+ " "+game.getTx().getDias(), 850+2,145 -deltaY);}
        if(dias==0){fuente.draw(batch, game.getTx().getYaNoAplica(), 850+2,145 -deltaY);}

        fuente.draw(batch, game.getTx().getApriximadoEn(), 800+2,120 -deltaY);
        dias = ImaxT();

        fuente.getData().setScale(0.45f,0.5f);

        if (dias>0){
            ImaxT = (int) dias+m.getDias2();
            fuente.draw(batch, ""+ dias+ " "+game.getTx().getDias() + " ("+game.getTx().getDia()+": "+ ImaxT +")", 800+1,100 -deltaY);}
        if (dias<=0){fuente.draw(batch, game.getTx().getYaPaso()+ " ("+game.getTx().getDia()+": "+ ImaxT +")", 800+1,100 -deltaY);}




       //Letras de colores

        fuente.getData().setScale(0.5f,0.5f);
        xpos = 820; xpos2 = 835;

        fuente.setColor(Color.WHITE);

        fuente.draw(batch, game.getTx().getPoblacionOriginal()+":", xpos,590 );
        fuente.draw(batch, ""+ format.format((int)(N)), xpos2,570 );

        fuente.setColor(Color.GREEN);
        fuente.draw(batch, game.getTx().getVacunados()+":", xpos,540 );
        fuente.draw(batch, ""+ (int)(m.getVacSIR())+ " %", xpos2,520 );
        fuente.setColor(Color.WHITE);
        fuente.draw(batch, game.getTx().getSusceptibles()+":", xpos,495 );
        fuente.draw(batch, "t: "+ format.format((int)(S)), xpos2,475 );

        fuente.setColor(colorInfectado);//.RED);
        fuente.draw(batch, game.getTx().getInfectados()+":", xpos,450 );
        fuente.draw(batch, "t: "+ format.format((int)(I)), xpos2,430 );
        fuente.draw(batch, "d: "+ format.format(conteoDiario(aIa1)), xpos2,410 );
        fuente.draw(batch, "max: "+ format.format((int)Imax), xpos2,390 );
        fuente.setColor(Color.YELLOW);//.ORANGE);
        fuente.getData().setScale(0.45f,0.5f);
        fuente.draw(batch, game.getTx().getInfectadosAcumulados()+":", xpos,365 );
        fuente.getData().setScale(0.45f,0.5f);
        fuente.draw(batch, "t: "+ format.format((int)(Ia)), xpos2,345 );
        fuente.draw(batch, ""+ format4.format((Ia/N)*100)+ "% "+game.getTx().getPoblacion(), xpos2,325 );
        fuente.setColor(colorRecuperado);
        fuente.draw(batch, game.getTx().getRecuperados()+":", xpos,300 );
        fuente.draw(batch, "t: "+ format.format((int)(R*(100-mort)/100)), xpos2,280 );
        fuente.draw(batch, "d: "+ format.format(curadosDiario(aR1)), xpos2,260 );
        fuente.setColor(Color.RED);//.RED);
        fuente.draw(batch, game.getTx().getMuertos()+":", xpos,235 );
        fuente.draw(batch, "t: "+ format.format((int)(R*mort/100)), xpos2,215 );
        fuente.draw(batch, "d: "+ format.format(muertosDiario(aR1)), xpos2,195 );
        fuente.setColor(Color.WHITE);
        fuente.getData().setScale(0.45f,0.5f);
        fuente.draw(batch, game.getTx().getDuplicaAproximadamenteCada(), 800,165 );
        fuente.getData().setScale(0.45f,0.5f);

         dias = duplicacionT();

        if(dias>0){fuente.draw(batch, ""+ dias+ " "+game.getTx().getDias(), 850,145 );}
        if(dias==0){fuente.draw(batch, game.getTx().getYaNoAplica(), 850,145 );}

        fuente.draw(batch, game.getTx().getApriximadoEn(), 800,120 );
        dias = ImaxT();

        fuente.getData().setScale(0.45f,0.5f);

        if (dias>0){
            ImaxT = (int) dias+m.getDias2();
            fuente.draw(batch, ""+ dias+ " "+game.getTx().getDias() + " ("+game.getTx().getDia()+": "+ ImaxT +")", 800,100 );}
        if (dias<=0){fuente.draw(batch, game.getTx().getYaPaso()+ " ("+game.getTx().getDia()+": "+ ImaxT +")", 800,100 );}





        batch.end();

        if(m.getPlay()==0){

         //dibujar el rectangulo negro
            caja.begin(ShapeType.Filled);
            caja.setColor(Color.BLACK);
            caja.rect(150, 270,  600, 270);
            caja.end();

        fuente.getData().setScale(1f,1f);
        batch.begin();
         fuente.draw(batch, game.getTx().getPausaParacambiarR0(), 220, 510);
         fuente.draw(batch, game.getTx().getOprimaOKparaTerminar(), 200, 460);
         fuente.draw(batch, "R0 = "+format3.format(R0*S/N), 350, 400);
        batch.end();


        }

        if(salir == true){

            //dibujar el rectangulo negro
            caja.begin(ShapeType.Filled);
            caja.setColor(Color.BLACK);
            caja.rect(150, 270,  600, 200);
            caja.end();

            fuente.getData().setScale(0.8f,1f);
            batch.begin();
            fuente.draw(batch, game.getTx().getDeseaTerminarLapartida(),0, 440,900,1,true);

            batch.end();

        }

       /* if(m.getPlay()==3){

            //dibujar el rectangulo negro
            caja.begin(ShapeType.Filled);
            caja.setColor(Color.BLACK);
            caja.rect(190, 350,  530, 100);
            caja.end();

            fuente.getData().setScale(0.8f,1f);
            batch.begin();
            fuente.draw(batch, game.getTx().getFinDeLaSimulacion(), 0, 440,900,1,true);

            batch.end();

        }

        */





   //////////////////////////////////////////////////////////////////
   //mostrar la linea para ver datos anteriores

        if(verLinea) {
            linea2.begin(ShapeType.Filled);
            linea2.setColor(Color.YELLOW);
            linea2.rect(posLinea.x, origenY, 2, 440);//eje de las y
            linea2.end();

            int index = (int)((posLinea.x-origenX)*725f/700f)/escalaX;
            int acomodarX;
            if(posLinea.x<600){ acomodarX =21; }
            else{ acomodarX =-131; }


            fuente.getData().setScale(0.4f,0.45f);

            batch.begin();
            fuente.setColor(Color.BLACK);
            fuente.draw(batch, game.getTx().getDias()+": "+index,posLinea.x+acomodarX, 500-1);
            fuente.setColor(Color.WHITE);
            fuente.draw(batch, game.getTx().getDias()+": "+index,posLinea.x+acomodarX-1, 500);

           if(index>0 && index<= m.getDias2()){//si la posición de la linea esta atras del dia n curso

               // Sombreado en nego

               fuente.setColor(Color.BLACK);
               fuente.draw(batch, "St: "+ format5.format(aS1.get(index)),posLinea.x+acomodarX, 470);
               //fuente.setColor(colorInfectado);
               fuente.draw(batch, "It: "+ format5.format(aI1.get(index)),posLinea.x+acomodarX, 450);
               fuente.draw(batch, " Id: "+ format5.format(conteoDiarioIndexado(aIa1,index)),posLinea.x+acomodarX, 430);
               //fuente.setColor(Color.YELLOW);
               fuente.draw(batch, "Ia: "+ format5.format(aIa1.get(index)),posLinea.x+acomodarX, 410);
               //fuente.setColor(colorRecuperado);
               fuente.draw(batch, "Rt: "+ format5.format(aR1.get(index)*(100-mort)/100),posLinea.x+acomodarX, 390);
               fuente.draw(batch, " Rd: "+ format5.format(curadosDiarioIndexados(aR1,index)),posLinea.x+acomodarX, 370);
               //fuente.setColor(Color.RED);
               fuente.draw(batch, "Dt: "+ format5.format(aR1.get(index)*(mort)/100),posLinea.x+acomodarX, 350);
               fuente.draw(batch, " Dd: "+ format5.format(muertosDiarioIndexados(aR1,index)),posLinea.x+acomodarX, 330);

               if(posLinea.x<600){ acomodarX =20; }
               else{ acomodarX =-130; }

             // Letras en colores
            fuente.setColor(Color.WHITE);
            fuente.draw(batch, "St: "+ format5.format(aS1.get(index)),posLinea.x+acomodarX, 470);
            fuente.setColor(colorInfectado);
            fuente.draw(batch, "It: "+ format5.format(aI1.get(index)),posLinea.x+acomodarX, 450);
            fuente.draw(batch, " Id: "+ format5.format(conteoDiarioIndexado(aIa1,index)),posLinea.x+acomodarX, 430);
            fuente.setColor(Color.YELLOW);
            fuente.draw(batch, "Ia: "+ format5.format(aIa1.get(index)),posLinea.x+acomodarX, 410);
            fuente.setColor(colorRecuperado);
            fuente.draw(batch, "Rt: "+ format5.format(aR1.get(index)*(100-mort)/100),posLinea.x+acomodarX, 390);
            fuente.draw(batch, " Rd: "+ format5.format(curadosDiarioIndexados(aR1,index)),posLinea.x+acomodarX, 370);
            fuente.setColor(Color.RED);
            fuente.draw(batch, "Dt: "+ format5.format(aR1.get(index)*(mort)/100),posLinea.x+acomodarX, 350);
            fuente.draw(batch, " Dd: "+ format5.format(muertosDiarioIndexados(aR1,index)),posLinea.x+acomodarX, 330);
            }


            batch.end();
        }

        //botones
      //  batch.begin();
        stage.draw();//dibuja los botones definidos en resize
    //    batch.end();
        batch.begin();
        if(vercamara) {batch.draw(screenshot, 475, 10, 50,40);}//
        batch.end();



    }

    //leer de disco

    public void leerSonido(){

        if (Gdx.files.local(partida).exists()==true){

            FileHandle file = Gdx.files.local(partida); //leemos el archivo
            String filetext = file.readString();

            if(filetext.equals("1")){ callar = 1;  }
            if(filetext.equals("-1")){ callar = -1;  }


        }}

    public void guardarSonido(){


        FileHandle file = Gdx.files.local(partida);
        file.writeString(""+callar, false);

    }

    //metodo para calcular


    public void correrPandemiaRapida(){

        for(int i = 0; i<730;i++){


            m.contadorTiempoSIR();
            //hacer los calculos de la pandemia
            //primera parte
            float b = S/N;
            if(b<0){b=0;}

            float dS = R0*b*I/Tinf;
            float dI = (R0*b*I/Tinf)-(I/Tinf);
            float dR = I/Tinf;

            S = S-dS;
            I = I + dI;
            Ia = Ia + dS;
            R = R + dR;

            aS1.add(S);
            aI1.add(I);
            aR1.add(R);
            aIa1.add(Ia);
            at.add(m.getDias2());

            //Segunad parte

            float b1 = S1/N;
            if(b1<0){b1=0;}

            float dS1 = R01*b1*I1/Tinf;
            float dI1 = (R01*b1*I1/Tinf)-(I1/Tinf);
            float dR1 = I1/Tinf;

            S1 = S1-dS1;
            I1 = I1 + dI1;
            Ia1 = Ia1 + dS1;
            R1 = R1 + dR1;
            if(dI1>0){Imax = Imax + dI1;} // solo suma si no está en remisión

            aS2.add(S1);
            aI2.add(I1);
            aR2.add(R1);
            aIa2.add(Ia1);
            //Segunda parte

            float b2 = S2/N;
            if(b2<0){b2=0;}

            float dS2 = R02*b2*I2/Tinf;
            float dI2 = (R02*b2*I2/Tinf)-(I2/Tinf);
            float dR2 = I2/Tinf;

            S2 = S2-dS2;
            I2 = I2 + dI2;
            Ia2 = Ia2 + dS2;
            R2 = R2 + dR2;

            aS3.add(S2);
            aI3.add(I2);
            aR3.add(R2);
            aIa3.add(Ia2);

            if((int)I< 1){i = 730;}


        }}







    public void correrPandemia(int speed){

        int delta = 500/speed;

        if (m.deltaTime5() > m.msecondTime(delta)) {

            m.contadorTiempoSIR();

            //hacer los calculos de la pandemia
    //primera parte
            float b = S/N;
            if(b<0){b=0;}

            float dS = R0*b*I/Tinf;
            float dI = (R0*b*I/Tinf)-(I/Tinf);
            float dR = I/Tinf;

            S = S-dS;
            I = I + dI;
            Ia = Ia + dS;
            R = R + dR;

            aS1.add(S);
            aI1.add(I);
            aR1.add(R);
            aIa1.add(Ia);
            at.add(m.getDias2());

      //Segunad parte

            float b1 = S1/N;
            if(b1<0){b1=0;}

            float dS1 = R01*b1*I1/Tinf;
            float dI1 = (R01*b1*I1/Tinf)-(I1/Tinf);
            float dR1 = I1/Tinf;

            S1 = S1-dS1;
            I1 = I1 + dI1;
            Ia1 = Ia1 + dS1;
            R1 = R1 + dR1;
            if(dI1>0){Imax = Imax + dI1;} // solo suma si no está en remisión

            aS2.add(S1);
            aI2.add(I1);
            aR2.add(R1);
            aIa2.add(Ia1);
       //Segunda parte

            float b2 = S2/N;
            if(b2<0){b2=0;}

            float dS2 = R02*b2*I2/Tinf;
            float dI2 = (R02*b2*I2/Tinf)-(I2/Tinf);
            float dR2 = I2/Tinf;

            S2 = S2-dS2;
            I2 = I2 + dI2;
            Ia2 = Ia2 + dS2;
            R2 = R2 + dR2;

            aS3.add(S2);
            aI3.add(I2);
            aR3.add(R2);
            aIa3.add(Ia2);



            m.setDelta5();


         // if(callar==1){  m.playTime();}



        }


    }


    public float escalaLog10(float x){

      float rel = 410/(float)Math.log10(410);

      float res = 0;
      if(x<1){res =0;}
      if(x>=1){res = rel*(float)Math.log10(x);}

    return res;
    }


    public void graficarLineaMuertos(Array<Float> arl,float propX, float propY, Color color){

        int dias = 0;
        int dias2 = 0;
        float datos = 0;
        float datos2 = 0;

        for (int i=1;i<arl.size;i++) {

            dias = (int) (origenX+ at.get(i)*propX);
            dias2 = (int) (origenX + at.get(i-1)*propX);

            if(cb_log10.isChecked()==false){

                datos = origenY + (arl.get(i)*(mort)/100)*propY;
                datos2 = origenY +(arl.get(i-1)*(mort)/100)*propY;}

            if(cb_log10.isChecked()==true){

                datos = origenY + escalaLog10((arl.get(i)*(mort)/100)*propY);
                datos2 = origenY +escalaLog10((arl.get(i-1)*(mort)/100)*propY);}


            linea.begin(ShapeType.Line);
            linea.setColor(color);

            if(datos<600){ linea.line(dias,datos,dias2,datos2);}


            linea.end();
        }}

    public void graficarLineaCurados(Array<Float> arl,float propX, float propY, Color color){

        int dias = 0;
        int dias2 = 0;
        float datos = 0;
        float datos2 = 0;

        for (int i=1;i<arl.size;i++) {

            dias = (int) (origenX+ at.get(i)*propX);
            dias2 = (int) (origenX + at.get(i-1)*propX);

            if(cb_log10.isChecked()==false){
            datos = origenY + (arl.get(i) *(100 - mort) / 100) * propY;
            datos2 = origenY +(arl.get(i-1) *(100 - mort) / 100) * propY;}

            if(cb_log10.isChecked()==true){
                datos = origenY + escalaLog10((arl.get(i) * (100 - mort) / 100) * propY);
                datos2 = origenY +escalaLog10((arl.get(i-1) * (100 - mort) / 100) * propY);}



            linea.begin(ShapeType.Line);
            linea.setColor(color);

            if(datos<600){ linea.line(dias,datos,dias2,datos2);}
            linea.end();
        }}


    public void graficarLinea(Array<Float> arl,float propX, float propY, Color color){

        int dias = 0;
        int dias2 = 0;
        float datos = 0;
        float datos2 = 0;

        for (int i=1;i<arl.size;i++) {

                dias = (int) (origenX+ at.get(i)*propX);
                dias2 = (int) (origenX + at.get(i-1)*propX);

           if(cb_log10.isChecked()==false){
                datos = origenY + arl.get(i)*propY;
                datos2 = origenY + arl.get(i-1)*propY;}

            if(cb_log10.isChecked()==true){

                datos = origenY + escalaLog10(arl.get(i)*propY);
                datos2 = origenY + escalaLog10(arl.get(i-1)*propY);}

                linea.begin(ShapeType.Line);
                linea.setColor(color);

                if(datos<600){ linea.line(dias,datos,dias2,datos2);}
                linea.end();
                }



        }






    public float ImaxT(){

        float k =0;
        float I2 = I;
        float S2 = S;
        float dI2 =0;
        float dS2=0;
        int factor =1;

        for(int i=0; i<730*factor;i++) {

            float b = S2/N;
            if(b<0){b=0;}

            dS2 = (R0*b*I2/Tinf)/factor;
            dI2 = ((R0 * b * I2 / Tinf))/factor - ((I2 / Tinf)/factor);

            S2 = S2 - dS2;
            I2 = I2 + dI2;

            if(dI2<=0){k=i;i=730*factor;}
        }

        return k/factor;

    }


    public float duplicacionT(){

        float k =0;
        float I2 = I;
        float S2 = S;
        float dI2 =0;
        float dS2=0;
        int factor =2;

        for(int i=0; i<730*factor;i++) {

         float b = S2/N;
         if(b<0){b=0;}

         dS2 = (R0*b*I2/Tinf)/factor;
         dI2 = ((R0 * b * I2 / Tinf))/factor - ((I2 / Tinf)/factor);

         S2 = S2 - dS2;
         I2 = I2 + dI2;

        if(I2/I>=2){k=i;i=730*factor;}
             }

       return k/factor;

    }

    public int conteoDiario(Array<Float> ar){

        float delta =0;

        if(ar.size>1){

            float a = ar.get(ar.size-1);
            float b = ar.get(ar.size-2);

            delta = a-b;
            if(delta<0){delta= delta*(-1);}

        }

        return (int) delta;

    }

    public int conteoDiarioIndexado(Array<Float> ar, int index){

        float delta =0;

        if(ar.size>1 && index>0){

            float a = ar.get(index);
            float b = ar.get(index-1);

            delta = a-b;
            if(delta<0){delta= delta*(-1);}

              }

        return (int) delta;

    }

    public int muertosDiarioIndexados(Array<Float> ar, int index){

        float delta =0;

        if(ar.size>1 && index>0){

            float a = ar.get(index)*(mort/100);
            float b = ar.get(index-1)*(mort/100);

            delta = a-b;
            if(delta<0){delta= delta*(-1);}

        }

        return (int) delta;

    }

    public int muertosDiario(Array<Float> ar){

        float delta =0;

        if(ar.size>1){

            float a = ar.get(ar.size-1)*(mort/100);
            float b = ar.get(ar.size-2)*(mort/100);

            delta = a-b;
            if(delta<0){delta= delta*(-1);}

        }

        return (int) delta;

    }

    public int curadosDiario(Array<Float> ar){

        float delta =0;

        if(ar.size>1){

            float a = ar.get(ar.size-1)*((100-mort)/100);
            float b = ar.get(ar.size-2)*((100-mort)/100);

            delta = a-b;
            if(delta<0){delta= delta*(-1);}

        }

        return (int) delta;

    }

    public int curadosDiarioIndexados(Array<Float> ar, int index){

        float delta =0;

        if(ar.size>1 && index >0){

            float a = ar.get(index)*((100-mort)/100);
            float b = ar.get(index-1)*((100-mort)/100);

            delta = a-b;
            if(delta<0){delta= delta*(-1);}

        }

        return (int) delta;

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
      //  img.dispose();
      //  imgR.dispose();
        txS.dispose();
        txI.dispose();
        txIa.dispose();
        txC.dispose();
        ta_atlas.dispose();
         ta_actores.dispose();
        ta_muerto.dispose();
        stage.dispose();
        fuente_boton.dispose();
        fuente.dispose();
        screenshot.dispose();
       // batch.dispose();

        //	System.outf.println("menu prinsipal cerrado");
    }

    public Mundo getM() {
        return m;
    }

    public ShapeRenderer getLinea2() {
        return linea2;
    }

    public boolean isVerLinea() {
        return verLinea;
    }

    public Vector2 getPosLinea() {
        return posLinea;
    }

    public int getOrigenX() {
        return origenX;
    }

    public float getOrigenY() {
        return origenY;
    }

    public void setPosLinea(Vector2 posLinea) {
        this.posLinea = posLinea;
    }

    public void setVerLinea(boolean verLinea) {
        this.verLinea = verLinea;
    }
}

