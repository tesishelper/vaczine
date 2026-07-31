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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;

public class MinijuegoIntro implements Screen{
    private VaczineGame game;
    private Mundo m;
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;

    private TextureAtlas ta_atlas, ta_box;//carga imagenes de atlas de texturas
    private Texture img, img2;
    private Skin sk_skin, sk_skin2;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_volver, b_ayuda,b_N1,b_N2,b_N3,b_N4; //crea botones con texto similares a los de swing
    private boolean verAyuda = false;
    private Image cursor;



    private BitmapFont fuente_boton, fuente;

    private boolean verCaja = false;

    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;


    public MinijuegoIntro(VaczineGame game) {
        this.game = game;





    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc

        m = game.getM2();

        Adshandler handler = game.getHandler();
        handler.showAds(true);



        img = new Texture("pantalla_MinijuegoIntro.png");//imagen
        img2 = new Texture("pantalla_MinijuegoAyuda.jpg");//imagen
        //	img.set


        fuente_boton = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);
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


        //instancia los botones


        b_volver = new TextButton(game.getTx().getMenu(), estilo);
        b_ayuda = new TextButton(game.getTx().getAyuda(), estilo);

        b_N1 = new TextButton("Noob", estilo);
        b_N2 = new TextButton("Normal", estilo);
        b_N3 = new TextButton("Pro", estilo);
        b_N4 = new TextButton("Hacker", estilo);



        cursor = new Image(new Texture("cursor.png"));
        cursor.setWidth(3);
        cursor.setHeight(2);

        //instancia los cuadros de texto




            //stage maneja elementos que reciben entradas como botones o eventos
            //en este caso se us apara los botones

            stage = new Stage();
            stage.clear();
            Gdx.input.setInputProcessor(stage);
            //acomoda loselementos relativo al tamño de la pantalla


            //instencia los TextFields

        m.addBoton(b_volver,0.8f,1f,100,60,50,540);
        m.addBoton(b_ayuda,0.8f,1f,100,60,974,540);
        m.addBoton(b_N1,0.8f,1f,180,60,250,200);
        m.addBoton(b_N2,0.8f,1f,180,60,440,200);
        m.addBoton(b_N3,0.8f,1f,180,60,630,200);
        m.addBoton(b_N4,0.8f,1f,180,60,820,200);



        stage.addActor(b_volver);
        stage.addActor(b_ayuda);
        stage.addActor(b_N1);
        stage.addActor(b_N2);
        stage.addActor(b_N3);
        stage.addActor(b_N4);



            //se agregan los listener para los botones



            b_volver.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    m.playClick();
                //    m.stopMysic();
                    game.setScreen(new Root(game));
                }});

        b_ayuda.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                verAyuda=true;
                return true;


            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
                //    m.stopMysic();
               verAyuda=false;
            }});

            b_N1.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {



                    m.dispose();
                    game.setM2(new Mundo(game));
                    m = game.getM2();
                    m.playClick();
                    m.setNivel(1);
                    m.setInGame(true);
                    m.setSegundos(60);
                    m.setPoblacion(100);
                    m.setCobertura(0);
                    m.setInfectividad(20);
                    m.setLetalidad(100);
                    m.setEnfermos(0);

                    //cargar los actores
                    m.poblacionInicial();
                    m.agregarActores();


                    game.setScreen(new Minijuego(game));
                }});
        b_N2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {


            //    m.stopMysic();
                m.dispose();
                game.setM2(new Mundo(game));
                m = game.getM2();
                m.playClick();
                m.setNivel(2);
                m.setInGame(true);
                m.setSegundos(60);
                m.setPoblacion(200);
                m.setCobertura(0);
                m.setInfectividad(30);
                m.setLetalidad(100);
                m.setEnfermos(0);
                //cargar los actores
                m.poblacionInicial();
                m.agregarActores();
                game.setScreen(new Minijuego(game));
            }});

        b_N3.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {


             //   m.stopMysic();
                m.dispose();
                game.setM2(new Mundo(game));
                m = game.getM2();
                m.playClick();
                m.setNivel(3);
                m.setInGame(true);
                m.setSegundos(120);
                m.setPoblacion(300);
                m.setCobertura(0);
                m.setInfectividad(50);
                m.setLetalidad(100);
                m.setEnfermos(0);
                //cargar los actores
                m.poblacionInicial();
                m.agregarActores();
                game.setScreen(new Minijuego(game));
            }});

        b_N4.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {


           //     m.stopMysic();
                m.dispose();
                game.setM2(new Mundo(game));
                m = game.getM2();
                m.playClick();
                m.setNivel(4);
                m.setInGame(true);
                m.setSegundos(120);
                m.setPoblacion(400);
                m.setCobertura(0);
                m.setInfectividad(80);
                m.setLetalidad(100);
                m.setEnfermos(0);
                //cargar los actores
                m.poblacionInicial();
                m.agregarActores();
                game.setScreen(new Minijuego(game));
            }});





    }

    @Override
    public void render(float delta) {
        //Se encarga de dibujar la pantalla
        Gdx.gl.glClearColor(m.getColorFondo().r,m.getColorFondo().g,m.getColorFondo().b,m.getColorFondo().a);

      //  Gdx.gl.glClearColor(0f/255f, 25f/255f, 100f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update(); //

        stage.getViewport().setCamera(camera);//el estage usará el vieport de la camara
        stage.act(delta);

        batch.setProjectionMatrix(camera.combined); //batch usa el punto de vista de la camara
        //img

        batch.begin();
        batch.draw(img, 0, 0, ancho,alto);
        batch.end();

        batch.begin();

        fuente.getData().setScale(1.1f,1.2f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);

        fuente.draw(batch, game.getTx().getCampañaDeVacunaciónMiniJuego(), 120, 590);

        fuente.getData().setScale(0.8f,0.8f);
        fuente.setColor(Color.BLACK);
        fuente.draw(batch, game.getTx().getAyudaAnuestroHeroe(), 200, 520,600,-1,true);
        //fuente.draw(batch, game.getTx().getPersonasantesDeQueSeAcabeElTiempo(), 200, 490);


       // fuente.draw(batch, game.getTx().getNoTeDejesTocarPorLas2(), 200, 450);

        batch.end();

        //botones
        batch.begin();
        stage.draw();//dibuja los botones definidos en resize
        batch.end();

        if(verAyuda){

            batch.begin();
            batch.draw(img2, 0, 150, ancho-100,alto-150);

            fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);

            fuente.getData().setScale(0.6f,0.8f);
            fuente.draw(batch, game.getTx().getNoTeDejesTocarPorLas1(), 520, 530, 300,-1,true);

            fuente.draw(batch, game.getTx().getLanzaLasDosisDevacunas(), 230, 250);
            fuente.draw(batch, game.getTx().getElBotonVacunar(), 230, 220);
            fuente.draw(batch, game.getTx().getTeclaAparaLaVersionDePc(), 230, 190);

            batch.end();

        }











        //si los cuadros de texto son tocados, se debe ver una caja con texto arriva


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
        img.dispose();
        img2.dispose();
        ta_atlas.dispose();
        stage.dispose();
        fuente_boton.dispose();
        fuente.dispose();
      //  batch.dispose();

      //  System.out.println("menu prinsipal cerrado");
    }







}

