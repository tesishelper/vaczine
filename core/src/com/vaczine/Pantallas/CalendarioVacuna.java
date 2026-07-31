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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Calendario;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class CalendarioVacuna implements Screen{
    private ShapeRenderer caja;
    private VaczineGame game;
    private Mundo m;
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;

    private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
   // private Texture img;
    private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_volver, b_masVacuna, b_menosVacuna; //crea botones con texto similares a los de swing

    private BitmapFont fuente, fuente_botones;
    private float rX;
    private float rY;

    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;

    private Calendario cal;

    ArrayList<String[]> listaVacuna;

    private int vacuna=0;
    private int info =1;
    private int posVacuna = 1;


    public CalendarioVacuna (VaczineGame game) {
        this.game = game;

        Adshandler handler = game.getHandler();
        handler.showAds(false);


        cal = new Calendario();

        listaVacuna = cal.getVacunas();//lista edad cargo la lista de las edades






    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc

        m = game.getM();

        caja = new ShapeRenderer();
       // img = new Texture("fondo.jpg");//imagen
        //	img.set
        fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente.setColor(255f/255f, 255f/255f, 135f/255f, 1);
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

        //instancia los botones


        b_volver = new TextButton("<<", estilo);

        b_masVacuna = new TextButton("\\/", estilo);
        b_menosVacuna = new TextButton("/\\", estilo);

        //stage maneja elementos que reciben entradas como botones o eventos
        //en este caso se us apara los botones

        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);



        m.addBoton(b_volver,1f,1f,100,60,50,540);
        m.addBoton(b_menosVacuna,1f,1f,40,100,30,400);
        m.addBoton(b_masVacuna,1f,1f,40,100,30,100);



        stage.addActor(b_volver);

        stage.addActor(b_masVacuna);
        stage.addActor(b_menosVacuna);


        //se agregan los listener para los botones

        b_volver.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
               // m.stopMysic();
                game.setScreen(new CalendarioIntro(game));
            }});


        b_masVacuna.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
                vacuna += 1;

                posVacuna=1;
                if(vacuna==listaVacuna.size()){vacuna=0;}

            }});

        b_menosVacuna.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
                vacuna -= 1;

                posVacuna=1;
                if(vacuna<0){vacuna=listaVacuna.size()-1;}

            }});






    }


    int contarVacunas(Array<String> lista){

        int nvacunas=0;

        for (int i=1; i<lista.size; i+=2){

            if(lista.get(i).equals("null") || lista.get(i).equals("")){

                nvacunas+=1;
            }
        }

        return ((lista.size-1)/2)-nvacunas;
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
        caja.setProjectionMatrix(camera.combined); //caja usa el punto de vista de la camara

      //  batch.begin();
     //   batch.draw(img, 0, 0, ancho,alto);
       // batch.end();

        caja.begin(ShapeRenderer.ShapeType.Filled);
        caja.setColor(Color.WHITE);
        caja.rect(380, 0,3, 530);//eje de las y
        caja.rect(0, 530,ancho, 3);//eje de las x
        caja.end();


        fuente.getData().setScale(0.75f,1f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);


        batch.begin();

        fuente.draw(batch,game.getTx().getCalendarioDeVacunacion()+ "-"+ game.getTx().getPorVacunas()+" (Argentina)", 120, 580,900,-1,true);


        // mostrar las edades
        fuente.getData().setScale(0.47f,0.70f);
        int y = 520;

        for(int i=0; i< listaVacuna.size(); i++){

            if(i==vacuna){fuente.draw(batch,">", 55, y);}//marca la edad que esta en uso

            fuente.draw(batch,listaVacuna.get(i)[0], 70, y);


            y-=30;

        }

        fuente.getData().setScale(0.6f,0.7f);
        fuente.setColor(Color.BROWN);
        //mostrar las vacunas en cada edad
        fuente.draw(batch,listaVacuna.get(vacuna)[info], 390, 520,630,-1,true);


        //mostrar las indicaciones de cada vacuna

        fuente.getData().setScale(0.65f,0.7f);
        fuente.setColor(255f/255f, 255f/255f, 135f/255f, 1);

        fuente.draw(batch,listaVacuna.get(vacuna)[info+1], 390, 300, 630,-1,true);

        //mostrar cuantas vacunas hay por edad

       // fuente.getData().setScale(0.8f,0.8f);

       // fuente.draw(batch,"Vacunas encontradas "+posVacuna+"/"+ contarVacunas(listaEdad.get(edad)), 320, 35);

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
      //  img.dispose();
        sk_skin.dispose();
        ta_atlas.dispose();
        stage.dispose();
        fuente.dispose();
        fuente_botones.dispose();
        // batch.dispose();

    }







}

