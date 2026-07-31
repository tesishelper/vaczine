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
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;





public class Root implements Screen  {


    private VaczineGame game;
    private Mundo m;



    private ShapeRenderer caja;
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private BitmapFont fuente_botones,fuente, fuenteAmarilla;


   // String nulo = "";
   // String cero = "0";

    private TextureAtlas ta_atlas, ta_box;//carga imagenes de atlas de texturas
    private Texture img,share,share2;
    private Skin sk_skin, sk_skin2;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_vacunas, b_compartir, b_calificar, b_despues, b_verIdioma,b_esp, b_eng,b_por, b_verOcultar, b_misProgramas,b_play,b_sir,b_ayuda,b_salir,b_calendario,b_about,b_game,b_musica, b_share; //crea botones con texto similares a los de swing

    private int verOcultar = -1; //1== ver -1==ocultar
    private int verIdioma = -1; //1== ver -1==ocultar

    private Image cursor;



    private boolean verCaja = false;

    private int musicaOn =1;


    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;

    public Root(VaczineGame game) {
        this.game = game;

        Adshandler handler = game.getHandler();
        handler.showAds(false);

    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc
        m = game.getM();


      // String idioma = java.util.Locale.getDefault().toString();

      //  System.out.println("idioma: " + idioma);

        //Chequear por el mensaje de pedido de soporte

        if (Gdx.files.local(game.getVerPedidoRuta()).exists()==true){

            FileHandle file = Gdx.files.local(game.getVerPedidoRuta()); //leemos el archivo
            String filetext = file.readString();

            if(filetext.equals("si")){game.setMostrarPedido(true);}
            if(filetext.equals("no")){game.setMostrarPedido(false);} // ya colaboró de alguna manera
            if(filetext.equals("despues")){				  // opto por hacerlo despues

                int chose = (int) (Math.random()*1000);

                System.out.println(chose);

                if (chose< 100){ game.setMostrarPedido(true);}
                else {game.setMostrarPedido(false);}
            }
        }

        if (Gdx.files.local(game.getVerPedidoRuta()).exists()==false){

            game.setMostrarPedido(true);

        }



        //pulgar = new Texture("pulgar.png");//imagen
        img = new Texture("pantalla_root.png");//imagen
        share= new Texture("share.png");//imagen
        share2 = new Texture("share2.png");//imagen

        fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);	//
        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuenteAmarilla = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);

        caja = new ShapeRenderer();
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
        estilo.font = fuente_botones;

        TextButtonStyle estilo2 = new TextButtonStyle();
        estilo2.up = sk_skin.getDrawable("botonInvisible");
        estilo2.down = sk_skin.getDrawable("botonInvisible");
        estilo2.font = fuente_botones;



        b_verIdioma = new TextButton(game.getTx().getIdioma(), estilo);
        b_esp = new TextButton("Español", estilo);
        b_eng = new TextButton("English", estilo);
        b_por = new TextButton("Português", estilo);
        b_verOcultar = new TextButton("///", estilo);
        b_misProgramas = new TextButton(game.getTx().getMisProgramas(), estilo);

        b_compartir = new TextButton(game.getTx().getCompartir(), estilo);
        b_calificar = new TextButton(game.getTx().getCalificar(), estilo);
        b_despues = new TextButton(game.getTx().getDespues(), estilo);

        b_about= new TextButton(game.getTx().getAcercaDe(), estilo);
        //  b_musica =  new TextButton(game.getTx().getMusicaOnOff(), estilo);
        b_salir = new TextButton(game.getTx().getSalir(), estilo);

        b_ayuda= new TextButton("",estilo2);
        b_play = new TextButton("", estilo2);
        b_game =  new TextButton("", estilo2);
        b_sir = new TextButton("", estilo2);
        b_calendario = new TextButton("", estilo2);
        b_vacunas = new TextButton("", estilo2);





       // b_misProgramas.setVisible(false);
        b_about.setVisible(false);
        b_salir.setVisible(false);
        b_esp.setVisible(false);
        b_eng.setVisible(false);
        b_por.setVisible(false);

        verBotonesPedido(game.isMostrarPedido());


        b_share =  new TextButton("", estilo);
      //  b_comentar = new TextButton("", estilo);


        cursor = new Image(new Texture("cursor.png"));
        cursor.setWidth(3);
        cursor.setHeight(1);

        //instancia los cuadros de texto

        fuente.getData().setScale(1,1);





        //stage maneja elementos que reciben entradas como botones o eventos
        //en este caso se us apara los botones

        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);




        //instancia los botones


        m.addBoton(b_ayuda,0.8f,1f,201,208,102,210);
        m.addBoton(b_game,0.56f,1f,201,208,511,210);
        m.addBoton(b_play,0.8f,1f,201,208,308,210);
        m.addBoton(b_sir,0.7f,1f,201,208,716,210);
        m.addBoton(b_calendario,0.8f,1f,201,208,919,210);
        m.addBoton(b_vacunas,0.8f,1f,201,208,102,10);


        m.addBoton(b_misProgramas,0.5f,1f,150,60,500,540);
        m.addBoton(b_share,0.8f,1f,150,60,650,540);

        m.addBoton(b_verIdioma,0.65f,1f,150,60,800,540);
        m.addBoton(b_esp,0.6f,1f,150,60,800,480);
        m.addBoton(b_eng,0.6f,1f,150,60,800,420);
        m.addBoton(b_por,0.6f,1f,150,60,800,360);
        m.addBoton(b_verOcultar,0.8f,1f,150,60,949,540);

        m.addBoton(b_about,0.5f,1f,150,60,949,480);

        m.addBoton(b_salir,0.6f,1f,150,60,949,420);


        m.addBoton(b_compartir,0.6f,1f,200,60,220,60);
        m.addBoton(b_calificar,0.4f,1f,200,60,520,60);
        m.addBoton(b_despues,0.4f,1f,200,60,800,60);


        //agragar los botones  para que se vean en pantalla

        stage.addActor(b_play);
        stage.addActor(b_sir);
        stage.addActor(b_calendario);
        stage.addActor(b_ayuda);
        stage.addActor(b_about);
        stage.addActor(b_salir);
        stage.addActor(b_verOcultar);
        stage.addActor(b_misProgramas);
        stage.addActor(b_game);
      //  stage.addActor(b_musica);
        stage.addActor(b_share);
        stage.addActor(b_verIdioma);
        stage.addActor(b_esp);
        stage.addActor(b_eng);
        stage.addActor(b_por);

        stage.addActor(b_compartir);
        stage.addActor(b_calificar);
        stage.addActor(b_despues);
        stage.addActor(b_vacunas);




        //se agregan los listener para los botones




        b_game.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
               // m.stopMysic(); //cotar la musica de este mundo

                game.setScreen(new MinijuegoIntro(game));
            }});

        b_play.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {


                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
               // m.stopMysic();
               // m.dispose();

                m.setPoblacion(500);
                m.setCobertura(0);
                m.setEnfermos(10);
                m.setInfectividad(20);
                m.setLetalidad(3);

                game.setScreen(new MenuInicio(game));


            }});

        b_sir.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setScreen(new ModSIRintro(game));
            }});

        b_calendario.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setScreen(new PantallaPaises(game));
            }});

        b_ayuda.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setScreen(new AyudaIntro(game));
            }});

        b_about.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setScreen(new AcercaDe(game));

            }});

        b_misProgramas.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                if(game.getIdioma().equals("spa")){game.getOpenURL().openURL(game.getURLMisAps());}
                if(game.getIdioma().equals("por")){game.getOpenURL().openURL(game.getURLMisApspor());}
                if(game.getIdioma().equals("eng")){game.getOpenURL().openURL(game.getURLMisApseng());}
            }});

        b_vacunas.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                if(game.getIdioma().equals("spa")){game.getOpenURL().openURL("https://sites.google.com/view/tesishelper/vaczine/vacunas_covid-19");}
                if(game.getIdioma().equals("por")){game.getOpenURL().openURL("https://sites.google.com/view/tesishelper/vaczine/vacinas_covid-19");}
                if(game.getIdioma().equals("eng")){game.getOpenURL().openURL("https://sites.google.com/view/tesishelper/vaczine/vacciness_covid-19");}
            }});

        b_share.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.getShare().share(game.getTx().getShare());
            }});

        b_verIdioma.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                verIdioma = verIdioma*(-1);

                if(verIdioma==-1) {

                    b_eng.setVisible(false);
                    b_esp.setVisible(false);
                    b_por.setVisible(false);
                }

                if(verIdioma==1) {

                    b_eng.setVisible(true);
                    b_esp.setVisible(true);
                    b_por.setVisible(true);
                }

            }});

        b_eng.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setIdioma("eng");

                FileHandle file = null;
                try {
                    file = Gdx.files.local(game.getIdiomaRuta());
                } catch (Exception e) {
                    e.printStackTrace();
                 //   game.mensajeEo.mensajeError(game.tx.archivoNoEncontrado);
                }


                if (game.getIdioma().equals("eng")){game.getTx().setIngles();
                    try {
                        file.writeString("eng", false);
                    } catch (Exception e) {
                        e.printStackTrace();
                       // game.mensajeEo.mensajeError(game.tx.noSePudoGuardar);
                    }
                }


               // b_game.setText(game.getTx().getCampana());
              //  b_play.setText(game.getTx().getSimuladorBasico());
             //   b_sir.setText(game.getTx().getModeloSIR());
             //   b_calendario.setText(game.getTx().getCalendario());
                b_misProgramas.setText(game.getTx().getMisProgramas());
            //    b_ayuda.setText(game.getTx().getAyuda());
                b_about.setText(game.getTx().getAcercaDe());
                b_salir.setText(game.getTx().getSalir());
                b_verIdioma.setText(game.getTx().getIdioma());
                //img = new Texture("pantalla_root_"+game.getIdioma() +".png");//imagen

                verIdioma= -1;
                b_eng.setVisible(false);
                b_esp.setVisible(false);
                b_por.setVisible(false);


            }});

        b_esp.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setIdioma("spa");

                FileHandle file = null;
                try {
                    file = Gdx.files.local(game.getIdiomaRuta());
                } catch (Exception e) {
                    e.printStackTrace();
                    //   game.mensajeEo.mensajeError(game.tx.archivoNoEncontrado);
                }


                if (game.getIdioma().equals("spa")){game.getTx().setEspanol();
                    try {
                        file.writeString("spa", false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // game.mensajeEo.mensajeError(game.tx.noSePudoGuardar);
                    }
                }


             //   b_game.setText(game.getTx().getCampana());
              //  b_play.setText(game.getTx().getSimuladorBasico());
             //   b_sir.setText(game.getTx().getModeloSIR());
             //   b_calendario.setText(game.getTx().getCalendario());
                b_misProgramas.setText(game.getTx().getMisProgramas());
            //    b_ayuda.setText(game.getTx().getAyuda());
                b_about.setText(game.getTx().getAcercaDe());
                b_salir.setText(game.getTx().getSalir());
                b_verIdioma.setText(game.getTx().getIdioma());
              //  img = new Texture("pantalla_root_"+game.getIdioma() +".png");//imagen

                verIdioma= -1;
                b_eng.setVisible(false);
                b_esp.setVisible(false);
                b_por.setVisible(false);
            }});

        b_por.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setIdioma("por");

                FileHandle file = null;
                try {
                    file = Gdx.files.local(game.getIdiomaRuta());
                } catch (Exception e) {
                    e.printStackTrace();
                    //   game.mensajeEo.mensajeError(game.tx.archivoNoEncontrado);
                }


                if (game.getIdioma().equals("por")){game.getTx().setPortugues();
                    try {
                        file.writeString("por", false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // game.mensajeEo.mensajeError(game.tx.noSePudoGuardar);
                    }
                }


             //   b_game.setText(game.getTx().getCampana());
             //   b_play.setText(game.getTx().getSimuladorBasico());
            //    b_sir.setText(game.getTx().getModeloSIR());
             //   b_calendario.setText(game.getTx().getCalendario());
                b_misProgramas.setText(game.getTx().getMisProgramas());
             //   b_ayuda.setText(game.getTx().getAyuda());
                b_about.setText(game.getTx().getAcercaDe());
                b_salir.setText(game.getTx().getSalir());
                b_verIdioma.setText(game.getTx().getIdioma());
               // img = new Texture("pantalla_root_"+game.getIdioma() +".png");//imagen

                verIdioma= -1;
                b_eng.setVisible(false);
                b_esp.setVisible(false);
                b_por.setVisible(false);
            }});





        /*b_musica.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                game.setMusicaOn(game.getMusicaOn()*(-1));

                System.out.println(musicaOn);

              //  if(game.getMusicaOn() == 1){m.playMusic();}
              //  if(game.getMusicaOn()== -1){m.stopMysic();}

            }});

         */

        b_verOcultar.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                verOcultar = verOcultar*(-1);

                if(verOcultar==-1){

                    b_about.setVisible(false);
                   // b_misProgramas.setVisible(false);
                     b_salir.setVisible(false);}

                if(verOcultar==1){

                    b_about.setVisible(true);
                  //  b_misProgramas.setVisible(true);

                b_salir.setVisible(true);}


            }});


        b_compartir.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setMostrarPedido(false);
                verBotonesPedido(game.isMostrarPedido());

                FileHandle file;

                try {
                    file = Gdx.files.local(game.getVerPedidoRuta());
                    file.writeString("no", false);
                } catch (Exception e) {
                    e.printStackTrace();
                                    }
                game.getShare().share(game.getTx().getShare());

            }});

        b_calificar.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setMostrarPedido(false);
                verBotonesPedido(game.isMostrarPedido());

                FileHandle file;

                try {
                    file = Gdx.files.local(game.getVerPedidoRuta());
                    file.writeString("no", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                game.getOpenURL().openURL(game.getURLVaczinePlay());

            }});

        b_despues.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();

                game.setMostrarPedido(false);
                verBotonesPedido(game.isMostrarPedido());

                FileHandle file;

                try {
                    file = Gdx.files.local(game.getVerPedidoRuta());
                    file.writeString("despues", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }});


        b_salir.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
             //   m.stopMysic();
                game.dispose();
                System.exit(0);
            }});

    }


    public void verBotonesPedido(boolean ver){

        b_compartir.setVisible(ver);
        b_calificar.setVisible(ver);
        b_despues.setVisible(ver);

        b_ayuda.setVisible(!ver);
        b_game.setVisible(!ver);
        b_play.setVisible(!ver);
        b_sir.setVisible(!ver);
        b_calendario.setVisible(!ver);

    }

    @Override
    public void render(float delta) {
        //Se encarga de dibujar la pantalla
        Gdx.gl.glClearColor(m.getColorFondo().r,m.getColorFondo().g,m.getColorFondo().b,m.getColorFondo().a);
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update(); //

        caja.setProjectionMatrix(camera.combined); //caja usa el punto de vista de la camara
        stage.getViewport().setCamera(camera);//el estage usará el vieport de la camara
        stage.act(delta);

        batch.setProjectionMatrix(camera.combined); //batch usa el punto de vista de la camara

        //la textura se adapta al tamaño de la ventana
        batch.begin();
        //batch.draw(fondo,0,0,ancho,alto);
        batch.draw(img, 0, 0, ancho,alto);//



        //batch.draw(img, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//
        batch.end();

        batch.begin();

        fuente.getData().setScale(1.45f,3f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);

       // fuente.draw(batch, "VacZine", 200, 580);

        fuente.getData().setScale(0.8f,1f);
        fuente.draw(batch, game.getTx().getSimuladorDigitalDeVacunacion(), 180, 480);

        fuente.getData().setScale(1f,1.4f);
        fuente.setColor(79f/255f,27f/255f,44f/255f,1f);

       // fuente.draw(batch, game.getTx().getElijaEntreLasOcionesHabilitadas(), 70, 400,m.getAncho(),1,true);

        //Ayuda

        fuente.getData().setScale(0.6f,0.7f);
        fuente.setColor(Color.BLACK);

        fuente.draw(batch, game.getTx().getAyuda(), 5, 410,200,1,true);

        fuente.getData().setScale(0.35f,0.5f);
        fuente.draw(batch, game.getTx().getComofunionan(), 5, 375,200,1,true);

        //Simulador basico
        fuente.getData().setScale(0.5f,0.5f);
        fuente.setColor(Color.BLACK);

        fuente.draw(batch, game.getTx().getSimuladorBasico(), 207, 410,200,1,true);

        fuente.getData().setScale(0.37f,0.5f);
        fuente.draw(batch, game.getTx().getSimuladorBasicoTexto1(), 207, 385,200,1,true);
        fuente.draw(batch, game.getTx().getSimuladorBasicoTexto2(), 207, 365,200,1,true);
        fuente.draw(batch, game.getTx().getSimuladorBasicoTexto3(), 207, 345,200,1,true);
        fuente.draw(batch, game.getTx().getSimuladorBasicoTexto4(), 207, 325,200,1,true);

        //Campaña de vacunación
        fuente.getData().setScale(0.4f,0.6f);


        fuente.draw(batch, game.getTx().getCampanaDeVacunacion(), 411, 410,200,1,true);

        fuente.getData().setScale(0.35f,0.50f);
        fuente.draw(batch, game.getTx().getCampanaDeVacunaciontexto1(), 411, 385,200,1,true);
        fuente.draw(batch, game.getTx().getCampanaDeVacunaciontexto2(), 411, 365,200,1,true);
        fuente.draw(batch, game.getTx().getCampanaDeVacunaciontexto3(), 411, 345,200,1,true);

        //modelo epidemiologico SIR

        fuente.getData().setScale(0.4f,0.6f);


        fuente.draw(batch, game.getTx().getModeloDePandemia(), 615, 410,200,1,true);

        fuente.getData().setScale(0.40f,0.50f);
        fuente.draw(batch, game.getTx().getModeloDePandemiaTexto1(), 615, 385,200,1,true);
        fuente.draw(batch, game.getTx().getModeloDePandemiaTexto2(), 615, 365,200,1,true);
        fuente.getData().setScale(0.4f,0.7f);
        fuente.draw(batch, game.getTx().getModeloDePandemiaTexto3(), 615, 240,200,1,true);

        //Calendario de vacunacion

        fuente.getData().setScale(0.41f,0.6f);

        fuente.draw(batch, game.getTx().getEsquemaDevacunacion1(), 795, 410,250,1,true);
        fuente.draw(batch, game.getTx().getEsquemaDevacunacion2(), 795, 385,250,1,true);


        // tarjetas covid-19

        fuente.getData().setScale(0.6f,0.7f);
        fuente.setColor(Color.BLACK);

        fuente.draw(batch, game.getTx().getVacunasContra(), 5, 200,200,1,true);

        fuente.getData().setScale(0.40f,0.5f);
        fuente.draw(batch, "Web", 5, 50,200,1,true);
        fuente.draw(batch, "LaPipette", 5, 30,200,1,true);

        // mensaje

        fuente.getData().setScale(0.65f,0.65f);
        fuente.draw(batch, game.getTx().getMensaje1(), 220, 200,800,-1,true);
        fuente.draw(batch, game.getTx().getMensaje2(), 220, 70,800,-1,true);

        batch.end();


        //recuadro de mensaje para pedir colaboracion

        // recuadro

       if(game.isMostrarPedido()) {

            caja.begin(ShapeRenderer.ShapeType.Filled);

            caja.setColor(Color.BLACK);
            caja.rect(95, 45, ancho - 190, alto - 140);//mascara superior
            caja.setColor(255f/255f, 228f/255f, 112f/255f, 1);
            caja.rect(100, 50, ancho - 200, alto - 150);//mascara superior
            caja.end();

           batch.begin();

           fuente.getData().setScale(0.7f,0.7f);

           fuente.draw(batch, game.getTx().getMensajeImportante(), 110, alto-110,ancho - 220,-1,true);

           fuente.getData().setScale(0.45f,0.6f);

           fuente.draw(batch, game.getTx().getPedido(), 110, alto-150,ancho - 220,-1,true);
           batch.end();

        }



        stage.draw();//dibuja los botones definidos en resize
        //botones
        batch.begin();
        batch.draw(share, 620, alto-49, 50,40);//
        batch.draw(share2, 580, alto-90, 140,35);//
       // batch.draw(pulgar,470, alto-49, 50,40);
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

        dispose();

    }

    @Override
    public void dispose() {
        sk_skin.dispose();
        fuente_botones.dispose();
        fuenteAmarilla.dispose();
        fuente.dispose();
        ta_atlas.dispose();
        stage.dispose();
        img.dispose();
        share.dispose();
        share2.dispose();
        caja.dispose();

    }




}
