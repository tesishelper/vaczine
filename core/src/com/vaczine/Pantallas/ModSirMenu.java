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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;





public class ModSirMenu implements Screen  {


    private VaczineGame game;
    private Mundo m;



    private ShapeRenderer caja;
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private BitmapFont fuente_botones,fuente, fuenteFlotante, fu_fuente;
    private TextField tf_poblacion;
    private TextField tf_cobertura;
    private TextField tf_infectados;
    private TextField tf_R0;
    private TextField tf_Tinf;
    private TextField tf_mortalidad;
    TextFieldStyle tfs_text;

    String nulo = "";
    String cero = "0";

    private TextureAtlas ta_atlas, ta_box;//carga imagenes de atlas de texturas

    private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_play,b_salir, b_listo,b_ayuda; //crea botones con texto similares a los de swing
    private CheckBox cb_cheq;


    private Image cursor;

    private boolean verCaja = false;

    private int musicaOn =1;


    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;

    public ModSirMenu(VaczineGame game) {
        this.game = game;


        Adshandler handler = game.getHandler();
        handler.showAds(true);


    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc
        m = game.getM2();


        fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fu_fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente_botones.getData().setScale(0.8f,0.8f);
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

        fu_fuente.getData().setScale(0.8f,0.8f);
        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.checkboxOff = sk_skin.getDrawable("check_box_off");
        checkBoxStyle.checkboxOn = sk_skin.getDrawable("check_box_on");
        checkBoxStyle.font = fu_fuente;
        //instancia los botones

        cb_cheq = new CheckBox(game.getTx().getNoQuieroEsperar(), checkBoxStyle);


        fuente.getData().setScale(0.8f,0.8f);

        TextFieldStyle tfs_text = new TextFieldStyle();
        tfs_text.background = sk_skin.getDrawable("text_box");
        tfs_text.cursor = sk_skin.getDrawable("cursor");
        tfs_text.cursor.setMinWidth(4f);
        tfs_text.font = fuente_botones;
        tfs_text.fontColor = Color.BLACK;

        b_ayuda = new TextButton(game.getTx().getAyuda(), estilo);
        b_play = new TextButton(game.getTx().getSimularEpidemia(), estilo);
        b_salir = new TextButton(game.getTx().getMenu(), estilo);
        b_listo = new TextButton("OK!", estilo);
        b_listo.setVisible(false);


        tf_poblacion =  new TextField( Integer.toString(m.getPobSIR()), tfs_text);
        tf_cobertura =  new TextField( Integer.toString(m.getVacSIR()), tfs_text);
        tf_infectados = new TextField( Integer.toString(m.getInfSIR()), tfs_text);
        tf_R0 =         new TextField( Float.toString(m.getR0SIR()), tfs_text);
        tf_Tinf =       new TextField( Integer.toString(m.getTinfSIR()), tfs_text);
        tf_mortalidad = new TextField( Float.toString(m.getMortalidadSIR()), tfs_text);


        //stage maneja elementos que reciben entradas como botones o eventos
        //en este caso se us apara los botones

        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);


        int y = 20;
        //instencia los TextFields

        m.addTextField(tf_poblacion,tfs_text, 200, 60, 105, 380+y);
        m.addTextField(tf_cobertura,tfs_text, 150, 60, 130, 310+y);
        m.addTextField(tf_infectados,tfs_text, 150, 60, 130, 240+y);
        m.addTextField(tf_R0,tfs_text, 150, 60, 580, 380+y);
        m.addTextField(tf_Tinf,tfs_text, 150, 60, 580, 310+y);
        m.addTextField(tf_mortalidad,tfs_text, 150, 60, 580, 240+y);

        //instencia los TextFields



        cb_cheq.getImage().setScaling(Scaling.fill);
        cb_cheq.getLabelCell().padLeft((int) (alto*(20f/600f)));
        cb_cheq.getCells().get(0).size(40, 40);
        cb_cheq.setChecked(false);
        cb_cheq.setPosition(540 ,150+y);

        //instancia los botones
        m.addBoton(b_play,0.5f,1f,200,60,400,140+y);
        m.addBoton(b_listo,0.8f,1f,122,70,665,450);
        m.addBoton(b_salir,0.8f,1f,100,60,50,540);
        m.addBoton(b_ayuda,0.8f,1f,100,60,974,540);



        // agregar loscuadros de text para que se vean en pantalla

        stage.addActor(tf_poblacion);
        stage.addActor(tf_cobertura);
        stage.addActor(tf_infectados);
        stage.addActor(tf_R0);
        stage.addActor(tf_Tinf);
        stage.addActor(tf_mortalidad);
        stage.addActor(cb_cheq);

        //agragar los botones  para que se vean en pantalla
        stage.addActor(b_ayuda);
        stage.addActor(b_play);
        stage.addActor(b_salir);
        stage.addActor(b_listo);


        //se agregan los listener para los botones

        b_ayuda.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
           //     m.stopMysic();
                game.setScreen(new Ayuda1SIR(game));
            }});

        b_listo.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
             //   m.stopMysic();
                Gdx.input.setOnscreenKeyboardVisible(false);
                stage.unfocusAll(); verCaja=false;


                b_listo.setVisible(false);
            }});


        b_play.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {


                return true;

            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {



                m.dispose();
                game.setM2(new Mundo(game));
                m = game.getM2();
                m.playClick();

                if(cb_cheq.isChecked()){

                    game.setSirRapido(true);
                }
                if(!cb_cheq.isChecked()){

                    game.setSirRapido(false);
                }


                chequeoTexto();//por las dudas hay espacios en blanco
                //stage.act();
                try {
                    m.setPobSIR(Integer.parseInt(tf_poblacion.getText()));

                    if(m.getPobSIR()<0) {m.setPobSIR(0);}

                    m.setVacSIR(Integer.parseInt(tf_cobertura.getText()));

                    if (m.getVacSIR()<0) {m.setVacSIR(0);tf_cobertura.setText("0");}
                    if (m.getVacSIR()>100){m.setVacSIR(100);tf_cobertura.setText("100");}

                    m.setInfSIR(Integer.parseInt(tf_infectados.getText()));

                    if(m.getInfSIR()<0){m.setInfSIR(0);}
                    if(m.getInfSIR() >m.getPobSIR()){m.setInfSIR(m.getPobSIR());}

                    m.setMortalidadSIR(Float.parseFloat(tf_mortalidad.getText()));

                    if (m.getMortalidadSIR()<0) {m.setMortalidadSIR(0);tf_mortalidad.setText("0");}
                    if (m.getMortalidadSIR()>100){m.setMortalidadSIR(100);tf_mortalidad.setText("100");}

                    m.setR0SIR(Float.parseFloat(tf_R0.getText()));

                    if (m.getR0SIR()<0) {m.setR0SIR(0);tf_R0.setText("0");}
                    if (m.getR0SIR()>20){m.setR0SIR(20);tf_R0.setText("20");}

                    m.setTinfSIR(Integer.parseInt(tf_Tinf.getText()));

                    if (m.getTinfSIR()<=0) {m.setTinfSIR(1);tf_R0.setText("1");}


                } catch (NumberFormatException e) {

                    m.setPoblacion(0);
                    m.setCobertura(0);
                    m.setLetalidad(0);
                    m.setInfectividad(0);

                }

                game.setScreen(new ModeloSIR(game));

            }});

        b_salir.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
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
        batch.begin();

        fuente.getData().setScale(1f,1.2f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);

        fuente.draw(batch, game.getTx().getSimulacionMedianteModeloSir(), 120, 580);

        fuente.getData().setScale(0.6f,1f);
        fuente.setColor(79f/255f,27f/255f,44f/255f,1f);

        fuente.draw(batch, game.getTx().getPorFavorIngreseLosValoresDeseados(), 5, 520);

        fuente.getData().setScale(0.5f,0.9f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);

        fuente.draw(batch, game.getTx().getPoblacionTotal(), 210, 445);
        fuente.draw(batch, game.getTx().getPoblacionVacunad(), 210, 375);
        fuente.draw(batch, game.getTx().getInfectados(), 210, 305);
        fuente.draw(batch, game.getTx().getNumeroBasicoDeReproduccion(), 665, 445);
        fuente.draw(batch, game.getTx().getTiempoDeInfeccion(), 665, 375);
        fuente.draw(batch, game.getTx().getMortalidad(), 665, 305);

        fuente.getData().setScale(0.7f,0.7f);
        fuente.setColor(79f/255f,27f/255f,44f/255f,1f);
        fuente.draw(batch, game.getTx().getPoblacionSusceptible(), 5, 245,1000,-1,true);

        batch.end();


        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_poblacion){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_cobertura){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_infectados){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_R0){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_Tinf){verCaja=true; b_listo.setVisible(true);}
        if (game.isAndroid()==true && stage.getKeyboardFocus() == tf_mortalidad){verCaja=true; b_listo.setVisible(true);}


        //ver la caja con texto

        if(verCaja==true){


            Adshandler handler = game.getHandler();
            handler.showAds(false);


            fuenteFlotante.getData().setScale(1.5f,1.5f);

            batch.begin();
            if (stage.getKeyboardFocus() == tf_poblacion){fuenteFlotante.draw(batch,tf_poblacion.getText() ,0f,580,m.getAncho(),1,true);}
            if (stage.getKeyboardFocus() == tf_cobertura){fuenteFlotante.draw(batch,tf_cobertura.getText() ,0f,580,m.getAncho(),1,true);}
            if (stage.getKeyboardFocus() == tf_infectados){fuenteFlotante.draw(batch,tf_infectados.getText() ,0f,580,m.getAncho(),1,true);}
            if (stage.getKeyboardFocus() == tf_R0){fuenteFlotante.draw(batch,tf_R0.getText() ,0f,580,m.getAncho(),1,true);}
            if (stage.getKeyboardFocus() == tf_Tinf){fuenteFlotante.draw(batch,tf_Tinf.getText() ,0f,580,m.getAncho(),1,true);}
            if (stage.getKeyboardFocus() == tf_mortalidad){fuenteFlotante.draw(batch,tf_mortalidad.getText() ,0f,580,m.getAncho(),1,true);}
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
        if (tf_infectados.getText().equals(nulo)) {tf_infectados.setText(cero);}
        if (tf_R0.getText().equals(nulo)) {tf_R0.setText(cero);}
        if (tf_Tinf.getText().equals(nulo)) {tf_Tinf.setText("1");}
        if (tf_mortalidad.getText().equals(nulo)) {tf_Tinf.setText(cero);}

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
        fuente.dispose();
        fu_fuente.dispose();
        fuenteFlotante.dispose();
        fuente_botones.dispose();
        ta_atlas.dispose();
        stage.dispose();




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

    public TextField getTf_infectados() {
        return tf_infectados;
    }

    public void setTf_infectados(TextField tf_infectados) {
        this.tf_infectados = tf_infectados;
    }

    public TextField getTf_R0() {
        return tf_R0;
    }

    public void setTf_R0(TextField tf_R0) {
        this.tf_R0 = tf_R0;
    }

    public TextField getTf_Tinf() {
        return tf_Tinf;
    }

    public void setTf_Tinf(TextField tf_Tinf) {
        this.tf_Tinf = tf_Tinf;
    }
}







