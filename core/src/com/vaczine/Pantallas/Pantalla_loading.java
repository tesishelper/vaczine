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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;


public class Pantalla_loading implements Screen{
    private VaczineGame game;

    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private TextButton b_esp, b_eng,b_por; //crea botones con texto similares a los de swing
    private TextureAtlas ta_atlas, ta_box;//carga imagenes de atlas de texturas
    private Skin sk_skin;
    private BitmapFont fuente_botones,fuente, fuenteAmarilla;

    private Texture img;
      //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;


    public Pantalla_loading(VaczineGame game) {

        this.game = game;



        Adshandler handler = game.getHandler();
        handler.showAds(false);



    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc



        img = new Texture("pantalla_loading.png");//imagen



        //	img.set

        //crear la camara
        camera = new OrthographicCamera(ancho, alto);
        //crear el viewport
        viewport = new StretchViewport(ancho, alto); //no lo estoy usando ahora
        // viewport.setCamera(camara);
        batch = game.getBatch();//new SpriteBatch(); //se usa para dibujar en la pantalla

        fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);	//

        ta_atlas = new TextureAtlas("pack31.pack");//carga el atlas de texturas donde estan los botones
        sk_skin = new Skin();
        sk_skin.addRegions(ta_atlas);

        //instansia los elemrntos de un boton
        //la posocion up y down usando imagenes y el texto que tiene cada uno

        TextButtonStyle estilo = new TextButtonStyle();
        estilo.up = sk_skin.getDrawable("boton_up");
        estilo.down = sk_skin.getDrawable("boton_down");
        estilo.font = fuente_botones;

        b_esp = new TextButton("Español", estilo);
        b_eng = new TextButton("English", estilo);
        b_por = new TextButton("Português", estilo);

        game.getM().addBoton(b_esp,0.6f,1f,200,60,220,100);
        game.getM().addBoton(b_eng,0.6f,1f,200,60,520,100);
        game.getM().addBoton(b_por,0.6f,1f,200,60,800,100);


        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(b_esp);
        stage.addActor(b_eng);
        stage.addActor(b_por);



        b_eng.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.getM().playClick();

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

                crearArchivoPrimerArranque();

                game.setScreen(new Root(game));

            }});

        b_esp.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
               game.getM().playClick();

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
                crearArchivoPrimerArranque();
                game.setScreen(new Root(game));

            }});

        b_por.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.getM().playClick();

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
                crearArchivoPrimerArranque();
                game.setScreen(new Root(game));

            }});



        if (Gdx.files.local(game.getPrimerArranqueRuta()).exists()==true){

            this.game.setScreen(new Root(this.game));
            System.out.println("Archivo primer arranque leido");

        }



    }


    public void crearArchivoPrimerArranque(){

        FileHandle file = null;
        try {
            file = Gdx.files.local(game.getPrimerArranqueRuta());
            file.writeString("", false);
        } catch (Exception e) {
            e.printStackTrace();
            //   game.mensajeEo.mensajeError(game.tx.archivoNoEncontrado);
        }


    }

    @Override
    public void render(float delta) {
        //Se encarga de dibujar la pantalla

        Gdx.gl.glClearColor(65f/255f, 202f/255f, 225f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update(); //

        stage.getViewport().setCamera(camera);//el estage usará el vieport de la camara
        stage.act(delta);

        batch.setProjectionMatrix(camera.combined); //batch usa el punto de vista de la camara


        batch.begin();
        batch.draw(img, 0, 0, ancho,alto);
        batch.end();


        stage.draw();//dibuja los botones definidos en resize




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
        img.dispose();
        stage.dispose();
       // batch.dispose();

    }







}
