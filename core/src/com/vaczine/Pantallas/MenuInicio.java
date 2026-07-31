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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

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





public class MenuInicio implements Screen  {


    private VaczineGame game;
    private Mundo m;



    private ShapeRenderer caja;
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private BitmapFont fuente_botones,fuente, fuenteFlotante;
    private TextField tf_poblacion;
    private TextField tf_cobertura;
    private TextField tf_letalidad;
    private TextField tf_infectividad;
    TextFieldStyle tfs_text;

    String nulo = "";
    String cero = "0";

    private TextureAtlas ta_atlas, ta_box;//carga imagenes de atlas de texturas
   // private Texture img;
    private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_play,b_salir, b_listo, b_anterior; //crea botones con texto similares a los de swing

    private TextField tf_enfermos;


    private TextButton b_avanzada;
    private boolean verCaja = false;

    private int musicaOn =1;


    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;

    public MenuInicio(VaczineGame game) {
        this.game = game;


        Adshandler handler = game.getHandler();
        handler.showAds(true);


    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc
        m = game.getM();



     //   img = new Texture("pantalla_inicio.png");//imagen
        fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);
        fuenteFlotante = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuenteFlotante.setColor(Color.BLUE);

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

        fuente.getData().setScale(1,1);

        TextFieldStyle tfs_text = new TextFieldStyle();
        tfs_text.background = sk_skin.getDrawable("text_box");
        tfs_text.cursor = sk_skin.getDrawable("cursor");
        tfs_text.cursor.setMinWidth(4f);
        tfs_text.font = fuente;
        tfs_text.fontColor = Color.BLACK;



        b_play = new TextButton(game.getTx().getSimularEpidemia(), estilo);
        b_anterior = new TextButton(game.getTx().getVolverAresultados(), estilo);
        b_salir = new TextButton(game.getTx().getMenu(), estilo);
        b_listo = new TextButton("OK!", estilo);

        b_listo.setVisible(false);


        //instancia los cuadros de texto




        tf_poblacion = new TextField( Integer.toString(m.getPoblacion()), tfs_text);
        tf_cobertura = new TextField( Integer.toString(m.getCobertura()), tfs_text);
        tf_letalidad = new TextField( Integer.toString(m.getLetalidad()), tfs_text);
        tf_infectividad = new TextField( Integer.toString(m.getInfectividad()), tfs_text);
        tf_enfermos = new TextField( Integer.toString(m.getEnfermos()), tfs_text);


            //stage maneja elementos que reciben entradas como botones o eventos
            //en este caso se us apara los botones

            stage = new Stage();
            stage.clear();
            Gdx.input.setInputProcessor(stage);


            //instencia los TextFields

            m.addTextField(tf_poblacion,tfs_text, 150, 65, 130, 400);

            m.addTextField(tf_cobertura,tfs_text, 150, 65, 130, 320);

            m.addTextField(tf_letalidad,tfs_text, 150, 65, 130, 240);

            m.addTextField(tf_infectividad,tfs_text, 150, 65, 600, 400);

            m.addTextField(tf_enfermos,tfs_text, 150, 65, 600, 320);

            //instancia los botones



            m.addBoton(b_play,0.6f,1.2f,230,60,640,240);
            m.addBoton(b_anterior,0.5f,1.2f,230,60,875,240);
            m.addBoton(b_salir,0.8f,1f,100,60,50,540);
            m.addBoton(b_listo,0.8f,1f,122,70,665,450);

            // agregar loscuadros de text para que se vean en pantalla

            stage.addActor(tf_poblacion);
            stage.addActor(tf_cobertura);
            stage.addActor(tf_letalidad);
            stage.addActor(tf_infectividad);
            stage.addActor(tf_enfermos);

            //agragar los botones  para que se vean en pantalla

            stage.addActor(b_play);

            stage.addActor(b_salir);
            stage.addActor(b_listo);
            if(m.getAct().size>0){stage.addActor(b_anterior);}



            //se agregan los listener para los botones

            b_listo.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    m.playClick();
                  //  m.stopMysic();
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    stage.unfocusAll(); verCaja=false;


                    b_listo.setVisible(false);
                }});

        b_anterior.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.getM().playClick();

                game.setScreen(new Resultado01(game));
            }});


            b_play.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {


                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                    game.getM().playClick();

                    m.resetMundo();

                   // game.setM(new Mundo(game));


                   // game.getM().stopMysic();

                    chequeoTexto();//por las dudas hay espacios en blanco
                    //stage.act();
                    try {
                        game.getM().setPoblacion(Integer.parseInt(tf_poblacion.getText()));

                        if(game.getM().getPoblacion()<0) {game.getM().setPoblacion(0);}

                        game.getM().setCobertura(Integer.parseInt(tf_cobertura.getText()));

                        if (game.getM().getCobertura()<0) {game.getM().setCobertura(0);tf_cobertura.setText("0");}
                        if (game.getM().getCobertura()>100){game.getM().setCobertura(100);tf_cobertura.setText("100");}

                        game.getM().setLetalidad(Integer.parseInt(tf_letalidad.getText()));

                        if (game.getM().getLetalidad()<0) {game.getM().setLetalidad(0);tf_letalidad.setText("0");}
                        if (game.getM().getLetalidad()>100){game.getM().setLetalidad(100);tf_letalidad.setText("100");}

                        game.getM().setInfectividad(Integer.parseInt(tf_infectividad.getText()));

                        if (game.getM().getInfectividad()<0) {game.getM().setInfectividad(0);tf_infectividad.setText("0");}
                        if (game.getM().getInfectividad()>100){game.getM().setInfectividad(100);tf_infectividad.setText("100");}

                        game.getM().setEnfermos(Integer.parseInt(tf_enfermos.getText()));

                        game.getM().agregarenfermo(game.getM().getEnfermos());//agregar los enfermos programados

                    } catch (NumberFormatException e) {

                        game.getM().setPoblacion(0);
                        game.getM().setCobertura(0);
                        game.getM().setLetalidad(0);
                        game.getM().setInfectividad(0);

                    }

                    //cargar los actores
                    game.getM().poblacionInicial();
                    game.getM().agregarActores();




                    game.setScreen(new Juego(game));


                }});


            b_salir.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    game.getM().playClick();
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    game.setScreen(new Root(game));
                }});

        }

    @Override
    public void render(float delta) {
        //Se encarga de dibujar la pantalla

        Gdx.gl.glClearColor(m.getColorFondo().r,m.getColorFondo().g,m.getColorFondo().b,m.getColorFondo().a);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update(); //

        stage.getViewport().setCamera(camera);//el estage usará el vieport de la camara
        stage.act(delta);

        batch.setProjectionMatrix(camera.combined); //batch usa el punto de vista de la camara

        //la textura se adapta al tamaño de la ventana
      //  batch.begin();
      //  batch.draw(img, 0, 0, ancho,alto);//
        //batch.draw(img, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//
      //  batch.end();

        batch.begin();

        fuente.getData().setScale(1.5f,1.5f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);

        fuente.getData().setScale(1f,1.2f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);

        fuente.draw(batch, game.getTx().getSimualdorBasico(), 120, 580);


        fuente.getData().setScale(0.6f,1.1f);
        fuente.setColor(79f/255f,27f/255f,44f/255f,1f);

        fuente.draw(batch, game.getTx().getPorFavorIngreseLosValoresDeseados(), 5, 520);

        fuente.getData().setScale(0.7f,1f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);


        fuente.draw(batch, game.getTx().getPoblacionSana(), 220, 450);
        fuente.draw(batch, game.getTx().getCobertura0100(), 220, 370);
        fuente.draw(batch, game.getTx().getMortalidad0100(), 220, 290);
        fuente.draw(batch, game.getTx().getInfectividad0100(), 700, 450);
        fuente.draw(batch, game.getTx().getAgregarPersonasEnfermas(), 700, 370);



        batch.end();


        //si los cuadros de texto son tocados, se debe ver una caja con texto arriva

        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_poblacion){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_cobertura){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_letalidad){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_infectividad){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_enfermos){verCaja=true; b_listo.setVisible(true);}


        //ver la caja con texto

        if(verCaja==true){

            Adshandler handler = game.getHandler();
            handler.showAds(false);

        fuenteFlotante.getData().setScale(1.5f,1.5f);

        batch.begin();
        if (stage.getKeyboardFocus() == tf_poblacion){fuenteFlotante.draw(batch,tf_poblacion.getText() ,0f,580,m.getAncho(),1,true);}
        if (stage.getKeyboardFocus() == tf_cobertura){fuenteFlotante.draw(batch,tf_cobertura.getText() ,0f,580,m.getAncho(),1,true);}
        if (stage.getKeyboardFocus() == tf_letalidad){fuenteFlotante.draw(batch,tf_letalidad.getText() ,0f,580,m.getAncho(),1,true);}
        if (stage.getKeyboardFocus() == tf_infectividad){fuenteFlotante.draw(batch,tf_infectividad.getText() ,0f,580,m.getAncho(),1,true);}
        if (stage.getKeyboardFocus() == tf_enfermos){fuenteFlotante.draw(batch,tf_enfermos.getText() ,0f,580,m.getAncho(),1,true);}
        batch.end();}


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


    public void chequeoTexto() {

        if (tf_poblacion.getText().equals(nulo)) {tf_poblacion.setText(cero);}
        if (tf_cobertura.getText().equals(nulo)) {tf_cobertura.setText(cero);}
        if (tf_letalidad.getText().equals(nulo)) {tf_letalidad.setText(cero);}
        if (tf_infectividad.getText().equals(nulo)) {tf_infectividad.setText(cero);}
        if (tf_enfermos.getText().equals(nulo)) {tf_enfermos.setText(cero);}

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
        fuenteFlotante.dispose();
        fuente.dispose();
        ta_atlas.dispose();
        stage.dispose();
      //  img.dispose();


    }

    public TextField getTf_poblacion() {
        return tf_poblacion;
    }

    public void setTf_poblacion(TextField tf_poblacion) {
        this.tf_poblacion = tf_poblacion;
    }

    public TextField getTf_cobertura() {
        return tf_cobertura;
    }

    public void setTf_cobertura(TextField tf_cobertura) {
        this.tf_cobertura = tf_cobertura;
    }

    public TextField getTf_letalidad() {
        return tf_letalidad;
    }

    public void setTf_letalidad(TextField tf_letalidad) {
        this.tf_letalidad = tf_letalidad;
    }

    public TextField getTf_infectividad() {
        return tf_infectividad;
    }

    public void setTf_infectividad(TextField tf_infectividad) {
        this.tf_infectividad = tf_infectividad;
    }






}
