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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Resultado01 implements Screen{
    private VaczineGame game;
    private NumberFormat format = new DecimalFormat("0.00");
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private BitmapFont fuente_boton, fuente;
    private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
    private Texture img, screenshot;
    private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_grafico,b_volver, b_anterior, b_capturar;//crea botones con texto similares a los de swing

    private Mundo m;
    private TextButton b_tabla;
    private boolean verAnterior=false;

    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;

    public Resultado01(VaczineGame game) {
        this.game = game;
        m = game.getM();

        Adshandler handler = game.getHandler();
        handler.showAds(false);

    }

    private boolean verbotones = true;

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc

        img = new Texture("pantalla_resultado01.png");//imagen
        screenshot = new Texture("screenshot.png");
       // img_fondo = m.getImg_fondo();

        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);
        fuente_boton = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);	//
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

        //instancia los botones
        b_volver = new TextButton("<<", estilo);
        b_grafico = new TextButton(" "+game.getTx().getGrafico()+" ", estilo);
        b_anterior= new TextButton(game.getTx().getAnterior(), estilo);
        b_capturar= new TextButton("", estilo);


            //stage maneja elementos que reciben entradas como botones o eventos
            //en este caso se us apara los botones

            stage = new Stage();
            stage.clear();
            Gdx.input.setInputProcessor(stage);




            m.addBoton(b_volver,0.8f,1f,100,60,50,540);

            m.addBoton(b_grafico,0.6f,1f,200,60,512,0);
            m.addBoton(b_anterior,0.5f,1f,200,60,924,0);
            m.addBoton(b_capturar,0.45f,1f,200,60,100,0);

            //stage.addActor(b_back);
            stage.addActor(b_volver);
            stage.addActor(b_grafico);
            stage.addActor(b_anterior);
            stage.addActor(b_capturar);



            //se agregan los listener para los botones

            b_volver.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    game.getM().playClick();
                   // game.getM().stopMysic();
                    game.setScreen(new MenuInicio(game));
                }});







            b_grafico.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    game.getM().playClick();

                    game.setScreen(new Resultado03(game));
                }});

        b_anterior.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.getM().playClick();
                verAnterior = true;
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.getM().playClick();
                verAnterior=false;
            }});

        b_capturar.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.getM().playClick();
                b_volver.setVisible(false);
                b_capturar.setVisible(false);
                b_anterior.setVisible(false);
                b_grafico.setVisible(false);
                verbotones= false;

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {


                game.getScreenShot().screenShot(game.getTx().getTabla());

                b_volver.setVisible(true);
                b_capturar.setVisible(true);
                b_anterior.setVisible(true);
                b_grafico.setVisible(true);
                verbotones= true;
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




        batch.begin();
        batch.draw(img, 0, 0,ancho,alto);
        batch.end();

        //para ver la grilla de control


        //botones

        stage.draw();//dibuja los botones definidos en resize


        batch.begin();
        if(verbotones) {batch.draw(screenshot, 75, 10, 50,40);}//
        batch.end();




       // fuente.setColor(Color.WHITE);
        fuente.getData().setScale(1f,1.2f);

        batch.begin();

        fuente.draw(batch,game.getTx().getResultadoGeneral()+"-("+game.getTx().getTabla()+")",120, 590);
        fuente.getData().setScale(0.75f, 1f);
        fuente.draw(batch,game.getTx().getPoblacionOriginal(),20, 510);
        fuente.draw(batch,game.getTx().getCoberturaDeVacunacion(),20, 475);
        fuente.draw(batch,game.getTx().getLetalidadDeLaEnfermedad(),20, 440);
        fuente.draw(batch,game.getTx().getInfectividadDeLaEnfermedad(),20, 405);

        if(!verAnterior) {

        fuente.getData().setScale(0.75f, 1f);
        fuente.draw(batch," : "+ (int) m.getAct().size+ " "+game.getTx().getPersonas(),450, 510);
        fuente.draw(batch," : "+ (int) m.getCobertura()+" %",450, 475);
        fuente.draw(batch," : "+ (int) m.getLetalidad()+" %",450, 440);
        fuente.draw(batch," : "+ (int) m.getInfectividad()+"",450, 405);
        fuente.getData().setScale(0.7f, 1f);
        fuente.draw(batch, "|" + game.getTx().getAnos() + ": " + m.getAños() + " |" + game.getTx().getMeses() + ": " + m.getMeses() + " |" + game.getTx().getDias() + ": " + m.getDias(),
                    600, 405);

        fuente.draw(batch, game.getTx().getNoVacunado(), 60, 200);
        fuente.draw(batch, "" + m.contarSanos(m.getAct(),9), 110, 170);
        fuente.draw(batch, "(" + format.format(((float) (m.contarSanos(m.getAct(),9)) / (float) (m.getAct().size)) * 100) + "%)", 100, 140);

        fuente.draw(batch, game.getTx().getVacunados(), 295, 200);
        fuente.draw(batch, "" + m.contarVacunados(m.getAct(),9), 305, 170);
        fuente.draw(batch, "(" + format.format(((float) (m.contarVacunados(m.getAct(),9)) / (float) (m.getAct().size)) * 100) + "%)", 295, 140);

        fuente.draw(batch, game.getTx().getCurados(), 495, 200);
        fuente.draw(batch, "" + m.contarCurados(m.getAct(),9), 500, 170);
        fuente.draw(batch, "(" + format.format(((float) (m.contarCurados(m.getAct(),9)) / (float) (m.getAct().size)) * 100) + "%)", 495, 140);

        fuente.draw(batch, game.getTx().getEnfermos(), 680, 200);
        fuente.draw(batch, "" + m.contarEnfermos(m.getAct(),9), 690, 170);
        fuente.draw(batch, "(" + format.format(((float) (m.contarEnfermos(m.getAct(),9)) / (float) (m.getAct().size)) * 100) + "%)", 680, 140);

        fuente.draw(batch, game.getTx().getMuertos(), 870, 200);
        fuente.draw(batch, "" + m.contarMuertos(m.getAct(),9), 880, 170);
        fuente.draw(batch, "(" + format.format(((float) (m.contarMuertos(m.getAct(),9)) / (float) (m.getAct().size)) * 100) + "%)", 870, 140);
        }

        if(verAnterior){

            fuente.getData().setScale(0.75f, 1f);
            fuente.draw(batch," : "+ (int) m.getAct0().size+ " "+game.getTx().getPersonas(),450, 510);
            fuente.draw(batch," : "+ (int) m.getCobertura0()+" %",450, 475);
            fuente.draw(batch," : "+ (int) m.getLetalidad0()+" %",450, 440);
            fuente.draw(batch," : "+ (int) m.getInfectividad0()+"",450, 405);
            fuente.getData().setScale(0.7f, 1f);
            fuente.draw(batch, "|" + game.getTx().getAnos() + ": " + m.getAños0() + " |" + game.getTx().getMeses() + ": " + m.getMeses0() + " |" + game.getTx().getDias() + ": " + m.getDias0(),
                    600, 405);

            fuente.draw(batch, game.getTx().getNoVacunado(), 60, 200);
            fuente.draw(batch, "" + m.contarSanos(m.getAct0(),9), 110, 170);
            fuente.draw(batch, "(" + format.format(((float) (m.contarSanos(m.getAct0(),9)) / (float) (m.getAct0().size)) * 100) + "%)", 100, 140);

            fuente.draw(batch, game.getTx().getVacunados(), 295, 200);
            fuente.draw(batch, "" + m.contarVacunados(m.getAct0(),9), 305, 170);
            fuente.draw(batch, "(" + format.format(((float) (m.contarVacunados(m.getAct0(),9)) / (float) (m.getAct0().size)) * 100) + "%)", 295, 140);

            fuente.draw(batch, game.getTx().getCurados(), 495, 200);
            fuente.draw(batch, "" + m.contarCurados(m.getAct0(),9), 500, 170);
            fuente.draw(batch, "(" + format.format(((float) (m.contarCurados(m.getAct0(),9)) / (float) (m.getAct0().size)) * 100) + "%)", 495, 140);

            fuente.draw(batch, game.getTx().getEnfermos(), 680, 200);
            fuente.draw(batch, "" + m.contarEnfermos(m.getAct0(),9), 690, 170);
            fuente.draw(batch, "(" + format.format(((float) (m.contarEnfermos(m.getAct0(),9)) / (float) (m.getAct0().size)) * 100) + "%)", 680, 140);

            fuente.draw(batch, game.getTx().getMuertos(), 870, 200);
            fuente.draw(batch, "" + m.contarMuertos(m.getAct0(),9), 880, 170);
            fuente.draw(batch, "(" + format.format(((float) (m.contarMuertos(m.getAct0(),9)) / (float) (m.getAct0().size)) * 100) + "%)", 870, 140);


        }


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
        img.dispose();
        stage.dispose();
        fuente_boton.dispose();
        fuente.dispose();
        screenshot.dispose();
      //  batch.dispose();
       // System.out.println("menu prinsipal cerrado");
    }



}
