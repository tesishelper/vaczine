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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Resultado03 implements Screen{
    private VaczineGame game;

    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private BitmapFont fuente_boton, fuente;
    private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
    private Texture img,screenshot;
    private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_menu; //crea botones con texto similares a los de swing

    private Mundo m;
    private TextButton b_anterior,b_capturar, b_tabla;//crea botones con texto similares a los de swing
    private float rangoX, rangoY;

    private ShapeRenderer caja, barra;

    private CheckBox cb_verSanos;

    private TextureAtlas ta_atlas2;

    private CheckBox cb_verEnfermos;

    private CheckBox cb_verVacunados;

    private CheckBox cb_verCurados;

    private CheckBox cb_verMuertos,cb_verMovilidad;

    private Sprite sp_muerto;

    private TextureAtlas ta_muerto;

    Array<Integer> at;
    ShapeRenderer linea;


    private int origenX;

    private float origenY;
    private boolean verAnterior= false;
    private boolean verbotones = true;


    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;

    public Resultado03(VaczineGame game) {
        this.game = game;
        m = game.getM();


        Adshandler handler = game.getHandler();
        handler.showAds(false);

    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc



        caja = new ShapeRenderer();
        barra = new ShapeRenderer();


        at  = new Array<>();
        linea = new ShapeRenderer();

        img = new Texture("pantalla_resultado03.png");//imagen
        screenshot = new Texture("screenshot.png");


        ta_muerto = new TextureAtlas("actores.pack");//imagen
        sp_muerto = new Sprite(ta_muerto.findRegion("muerto"));//sprite para usar la imagen con mas libertad


        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
      //  fuente.setColor(255f/255f, 255f/255f, 135f/255f, 1);
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

        fuente.getData().setScale(1,1);


        CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
        checkBoxStyle.checkboxOff = sk_skin.getDrawable("check_box_off");
        checkBoxStyle.checkboxOn = sk_skin.getDrawable("check_box_on");
        checkBoxStyle.font = fuente_boton;

        cb_verMovilidad = new CheckBox("", checkBoxStyle);

        cb_verSanos = new CheckBox("", checkBoxStyle);

        cb_verVacunados = new CheckBox("", checkBoxStyle);

        cb_verCurados = new CheckBox("", checkBoxStyle);

        cb_verEnfermos = new CheckBox("", checkBoxStyle);

        cb_verMuertos = new CheckBox("", checkBoxStyle);


        b_menu = new TextButton("<<", estilo);


        b_tabla = new TextButton(" "+game.getTx().getTabla()+" ", estilo);

        b_anterior= new TextButton(game.getTx().getAnterior(), estilo);
        b_capturar = new TextButton("", estilo);

            //stage maneja elementos que reciben entradas como botones o eventos
            //en este caso se us apara los botones

            stage = new Stage();
            stage.clear();
            Gdx.input.setInputProcessor(stage);

            //

            //calcula el rango y posicion de los puntos de x e y

            rangoX = (float) 800/m.getDias2();
            rangoY = (float) 380/m.getAct().size;

            origenX = 105;
            origenY = 120;


            cb_verMovilidad.getImage().setScaling(Scaling.fill);
            cb_verMovilidad.getCells().get(0).size(30, 30);
            cb_verMovilidad.setChecked(false);
            cb_verMovilidad.setPosition(940 ,500);


            cb_verSanos.getImage().setScaling(Scaling.fill);
            cb_verSanos.getCells().get(0).size(30, 30);
            cb_verSanos.setChecked(false);
            cb_verSanos.setPosition(940 ,427);


            cb_verVacunados.getImage().setScaling(Scaling.fill);
            cb_verVacunados.getCells().get(0).size(30,30);
            cb_verVacunados.setChecked(false);
            cb_verVacunados.setPosition(940 , 345);


            cb_verCurados.getImage().setScaling(Scaling.fill);
            cb_verCurados.getCells().get(0).size(30,30);
            cb_verCurados.setChecked(false);
            cb_verCurados.setPosition(940 ,267);


            cb_verEnfermos.getImage().setScaling(Scaling.fill);
            cb_verEnfermos.getCells().get(0).size(30,30);
            cb_verEnfermos.setChecked(true);
            cb_verEnfermos.setPosition(940 , 187);


            cb_verMuertos.getImage().setScaling(Scaling.fill);
            cb_verMuertos.getCells().get(0).size(30,30);
            cb_verMuertos.setChecked(false);
            cb_verMuertos.setPosition(940, 115);

            //instancia los botones

        m.addBoton(b_menu,0.8f,1f,100,60,50,540);
        m.addBoton(b_tabla,0.6f,1f,200,60,512,0);
        m.addBoton(b_anterior,0.5f,1f,200,60,924,0);
        m.addBoton(b_capturar,0.45f,1f,200,60,100,0);




            //stage.addActor(b_back);

            stage.addActor(b_tabla);
            stage.addActor(b_anterior);
            stage.addActor(b_capturar);


            stage.addActor(b_menu);

            stage.addActor(cb_verMovilidad);
            stage.addActor(cb_verSanos);
            stage.addActor(cb_verVacunados);
            stage.addActor(cb_verCurados);
            stage.addActor(cb_verMuertos);
            stage.addActor(cb_verEnfermos);




            //se agregan los listener para los botones



            b_menu.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    //creamos un nuevo menuInicio con los parametros del anterior
                    m.playClick();
                  //  m.stopMysic();
                    MenuInicio mi= new MenuInicio(game);


                    game.setScreen(mi);
                }});
            b_tabla.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    game.getM().playClick();

                    game.setScreen(new Resultado01(game));
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
                b_menu.setVisible(false);
                b_capturar.setVisible(false);
                b_anterior.setVisible(false);
                b_tabla.setVisible(false);
                verbotones = false;

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {


                game.getScreenShot().screenShot(game.getTx().getGrafico());

                b_menu.setVisible(true);
                b_capturar.setVisible(true);
                b_anterior.setVisible(true);
                b_tabla.setVisible(true);
                verbotones = true;
            }});





    }

    @Override
    public void render(float delta) {
        //Se encarga de dibujar la pantalla

        Gdx.gl.glClearColor(m.getColorFondo().r,m.getColorFondo().g,m.getColorFondo().b,m.getColorFondo().a);
     //   Gdx.gl.glClearColor(0f/255f, 25f/255f, 100f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update(); //

        stage.getViewport().setCamera(camera);//el estage usará el vieport de la camara
        stage.act(delta);

        batch.setProjectionMatrix(camera.combined); //batch usa el punto de vista de la camara

        caja.setProjectionMatrix(camera.combined); //caja usa el punto de vista de la camara
        barra.setProjectionMatrix(camera.combined);
        linea.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(img, 0, 0, ancho,alto);
        batch.end();

        fuente.getData().setScale(1f,1.2f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);

        batch.begin();
        //resultado
        fuente.draw(batch,game.getTx().getResultadoGeneral()+"-("+game.getTx().getGrafico()+")",120, 590);
        batch.end();


        //botones

        stage.draw();//dibuja los botones definidos en resize

        batch.begin();
        if(verbotones) {batch.draw(screenshot, 75, 10, 50,40);}//
        batch.end();


        fuente.setColor(Color.WHITE);
        fuente.getData().setScale(0.6f, 0.6f);




        caja.begin(ShapeType.Filled);
        caja.setColor(Color.WHITE);
        caja.rect(102, 120,3, 390);//eje de las y
        caja.rect(102, 120,  820, 3); //eje de las x


        caja.end();


        //esto altera el rango de lo que se ve en el ej ede las x
        if(!verAnterior){rangoX = (float) 800/m.getDias2();}
        if(verAnterior){rangoX = (float) 800/m.getDias20();}


        //graficar cuarentena
        barra.begin(ShapeType.Filled);
        barra.setColor(Color.WHITE);

        for (int i=0;i<m.getaCuarentena().size;i++) {
            int dias = (int) (origenX+ rangoX*(m.getaCuarentena().get(i).getX()));
            float cuarentena = 380;
            barra.rect(dias, origenY,1,cuarentena);
            batch.begin();
            fuente.draw(batch, "L" + m.getaCuarentena().get(i).getY(), dias-5, 525);
            batch.end();
            }
        barra.end();


///////////////////////////////////////////////////////////////////////////////
        //Graficar las lineas de los graficos
        linea.begin(ShapeType.Line);


        //grafica los sanos sin vacunar
        if (cb_verSanos.isChecked()==true) {

           if(!verAnterior) {
            linea.setColor(Color.WHITE);
            for (int i=1;i<m.getaSanos().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaSanos().get(i).getX()));
                float sanos = origenY+ (float)rangoY*(m.getaSanos().get(i).getY());
                int dias2 = (int) (origenX+ rangoX*(m.getaSanos().get(i-1).getX()));
                float sanos2 = origenY+ (float)rangoY*(m.getaSanos().get(i-1).getY());

                linea.line(dias,sanos,dias2,sanos2);
                }}

            if(verAnterior) {
                linea.setColor(Color.WHITE);
                for (int i=1;i<m.getaSanos0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaSanos0().get(i).getX()));
                    float sanos = origenY+ (float)rangoY*(m.getaSanos0().get(i).getY());
                    int dias2 = (int) (origenX+ rangoX*(m.getaSanos0().get(i-1).getX()));
                    float sanos2 = origenY+ (float)rangoY*(m.getaSanos0().get(i-1).getY());

                    linea.line(dias,sanos,dias2,sanos2);
                }}


        }

        //graficar Vacunados
        if (cb_verVacunados.isChecked()==true) {

            if(!verAnterior){
            linea.setColor(Color.GREEN);
            for (int i=1;i<m.getaVacunados().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaVacunados().get(i).getX()));
                float sanos = origenY+ (float)rangoY*(m.getaVacunados().get(i).getY());
                int dias2 = (int) (origenX+ rangoX*(m.getaVacunados().get(i-1).getX()));
                float sanos2 = origenY+ (float)rangoY*(m.getaVacunados().get(i-1).getY());
                linea.line(dias,sanos,dias2,sanos2);
            }}

            if(verAnterior){
                linea.setColor(Color.GREEN);
                for (int i=1;i<m.getaVacunados0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaVacunados0().get(i).getX()));
                    float sanos = origenY+ (float)rangoY*(m.getaVacunados0().get(i).getY());
                    int dias2 = (int) (origenX+ rangoX*(m.getaVacunados0().get(i-1).getX()));
                    float sanos2 = origenY+ (float)rangoY*(m.getaVacunados0().get(i-1).getY());
                    linea.line(dias,sanos,dias2,sanos2);
                }}


        }
        //grafica los muertos
        if (cb_verMuertos.isChecked()==true) {

           if(!verAnterior){
            linea.setColor(Color.BLACK);
            for (int i=1;i<m.getaMuertos().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaMuertos().get(i).getX()));
                float muertos = origenY+ (float)rangoY*(m.getaMuertos().get(i).getY());
                int dias2 = (int) (origenX+ rangoX*(m.getaMuertos().get(i-1).getX()));
                float muertos2 = origenY+ (float)rangoY*(m.getaMuertos().get(i-1).getY());
                linea.line(dias,muertos,dias2,muertos2);
            } }

            if(verAnterior){
                linea.setColor(Color.BLACK);
                for (int i=1;i<m.getaMuertos0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaMuertos0().get(i).getX()));
                    float muertos = origenY+ (float)rangoY*(m.getaMuertos0().get(i).getY());
                    int dias2 = (int) (origenX+ rangoX*(m.getaMuertos0().get(i-1).getX()));
                    float muertos2 = origenY+ (float)rangoY*(m.getaMuertos0().get(i-1).getY());
                    linea.line(dias,muertos,dias2,muertos2);
                } }


        }

        //grafica los enfermos
        if (cb_verEnfermos.isChecked()==true) {

           if(!verAnterior){
            linea.setColor(Color.RED);
            for (int i=1;i<m.getaEnfermos().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaEnfermos().get(i).getX()));
                float enfermo = origenY+ (float)rangoY*(m.getaEnfermos().get(i).getY());
                int dias2 = (int) (origenX+ rangoX*(m.getaEnfermos().get(i-1).getX()));
                float enfermo2 = origenY+ (float)rangoY*(m.getaEnfermos().get(i-1).getY());
                linea.line(dias,enfermo,dias2,enfermo2);
            } }

            if(verAnterior){
                linea.setColor(Color.RED);
                for (int i=1;i<m.getaEnfermos0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaEnfermos0().get(i).getX()));
                    float enfermo = origenY+ (float)rangoY*(m.getaEnfermos0().get(i).getY());
                    int dias2 = (int) (origenX+ rangoX*(m.getaEnfermos0().get(i-1).getX()));
                    float enfermo2 = origenY+ (float)rangoY*(m.getaEnfermos0().get(i-1).getY());
                    linea.line(dias,enfermo,dias2,enfermo2);
                } }


        }

        //grafica los Curados
        if (cb_verCurados.isChecked()==true) {

            if(!verAnterior){
            linea.setColor(Color.BLACK);
            for (int i=1;i<m.getaCurados().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaCurados().get(i).getX()));
                float curado= origenY+ (float)rangoY*(m.getaCurados().get(i).getY());
                int dias2 = (int) (origenX+ rangoX*(m.getaCurados().get(i-1).getX()));
                float curado2 = origenY+ (float)rangoY*(m.getaCurados().get(i-1).getY());
                linea.line(dias,curado,dias2,curado2);
            }}

            if(verAnterior){
                linea.setColor(Color.BLACK);
                for (int i=1;i<m.getaCurados0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaCurados0().get(i).getX()));
                    float curado= origenY+ (float)rangoY*(m.getaCurados0().get(i).getY());
                    int dias2 = (int) (origenX+ rangoX*(m.getaCurados0().get(i-1).getX()));
                    float curado2 = origenY+ (float)rangoY*(m.getaCurados0().get(i-1).getY());
                    linea.line(dias,curado,dias2,curado2);
                }}


        }




        linea.end();

//////////////////////////////////////////////////////////////////////////////////////////
      //Graficar los puntos de los graficos

        caja.begin(ShapeType.Filled);

        //grafica los sanos sin vacunar
        if (cb_verSanos.isChecked()==true) {

            if(!verAnterior){
            for (int i=0;i<m.getaSanos().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaSanos().get(i).getX()));
                float sanos = origenY+ (float)rangoY*(m.getaSanos().get(i).getY());
                caja.setColor(Color.BLACK);
                caja.circle(dias, sanos, 5);
                caja.setColor(Color.WHITE);
                caja.circle(dias, sanos, 4);}}

            if(verAnterior){
                for (int i=0;i<m.getaSanos0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaSanos0().get(i).getX()));
                    float sanos = origenY+ (float)rangoY*(m.getaSanos0().get(i).getY());
                    caja.setColor(Color.BLACK);
                    caja.circle(dias, sanos, 5);
                    caja.setColor(Color.WHITE);
                    caja.circle(dias, sanos, 4);}}

        }
        //grafica los vacunados

        if (cb_verVacunados.isChecked()==true) {

            if(!verAnterior){
            for (int i=0;i<m.getaVacunados().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaVacunados().get(i).getX()));
                float vacunados = origenY+ (float)rangoY*(m.getaVacunados().get(i).getY());
                caja.setColor(Color.BLACK);
                caja.circle(dias, vacunados, 5);
                caja.setColor(Color.GREEN);
                caja.circle(dias, vacunados, 4);}}

            if(verAnterior){
                for (int i=0;i<m.getaVacunados0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaVacunados0().get(i).getX()));
                    float vacunados = origenY+ (float)rangoY*(m.getaVacunados0().get(i).getY());
                    caja.setColor(Color.BLACK);
                    caja.circle(dias, vacunados, 5);
                    caja.setColor(Color.GREEN);
                    caja.circle(dias, vacunados, 4);}}


        }

        //grafica los muertos

        if (cb_verMuertos.isChecked()==true) {
            batch.begin();

            if(!verAnterior){
            for (int i=0;i<m.getaMuertos().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaMuertos().get(i).getX()));
                float muertos = origenY+ (float)rangoY*(m.getaMuertos().get(i).getY());
                sp_muerto.setSize(10,10);
                sp_muerto.setPosition(dias-(10/2),muertos-(10/2));
                sp_muerto.draw(batch);}}

            if(verAnterior){
                for (int i=0;i<m.getaMuertos0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaMuertos0().get(i).getX()));
                    float muertos = origenY+ (float)rangoY*(m.getaMuertos0().get(i).getY());
                    sp_muerto.setSize(10,10);
                    sp_muerto.setPosition(dias-(10/2),muertos-(10/2));
                    sp_muerto.draw(batch);}}


            batch.end();}

        //grafica los curados
        if (cb_verCurados.isChecked()==true) {

            if(!verAnterior){
            for (int i=0;i<m.getaCurados().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaCurados().get(i).getX()));
                float curados = origenY+ (float)rangoY*(m.getaCurados().get(i).getY());
                caja.setColor(Color.BLACK);
                caja.circle(dias, curados, 5);
                caja.setColor(Color.CYAN);
                caja.circle(dias, curados, 4);}}

            if(verAnterior){
                for (int i=0;i<m.getaCurados0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaCurados0().get(i).getX()));
                    float curados = origenY+ (float)rangoY*(m.getaCurados0().get(i).getY());
                    caja.setColor(Color.BLACK);
                    caja.circle(dias, curados, 5);
                    caja.setColor(Color.CYAN);
                    caja.circle(dias, curados, 4);}}

        }



        //grafica las enfermos
        if (cb_verEnfermos.isChecked()==true) {

            if(!verAnterior){
            for (int i=0;i<m.getaEnfermos().size;i++) {
                int dias = (int) (origenX+ rangoX*(m.getaEnfermos().get(i).getX()));
                float enfermos = origenY+ (float)rangoY*(m.getaEnfermos().get(i).getY());

                caja.setColor(Color.BLACK);
                caja.circle(dias, enfermos, 5);
                caja.setColor(Color.RED);
                caja.circle(dias, enfermos, 4);}}

            if(verAnterior){
                for (int i=0;i<m.getaEnfermos0().size;i++) {
                    int dias = (int) (origenX+ rangoX*(m.getaEnfermos0().get(i).getX()));
                    float enfermos = origenY+ (float)rangoY*(m.getaEnfermos0().get(i).getY());

                    caja.setColor(Color.BLACK);
                    caja.circle(dias, enfermos, 5);
                    caja.setColor(Color.RED);
                    caja.circle(dias, enfermos, 4);}}

        }

        caja.end();


        fuente.getData().setScale(0.4f, 0.4f);

        //grafica la movilidad
        if(cb_verMovilidad.isChecked()==true) {
            batch.begin();

            if(!verAnterior){

            for (int i = 0; i < m.getaMovilidad().size; i++) {

                float movilidad = origenY + (m.getaMovilidad().get(i).getY()*38);
                int dias = (int) (origenX + rangoX * (m.getaMovilidad().get(i).getX()));

            fuente.draw(batch, "M", dias-6, movilidad +5);

            }}

            if(verAnterior){

                for (int i = 0; i < m.getaMovilidad0().size; i++) {

                    float movilidad = origenY + (m.getaMovilidad0().get(i).getY()*38);
                    int dias = (int) (origenX + rangoX * (m.getaMovilidad0().get(i).getX()));

                    fuente.draw(batch, "M", dias-6, movilidad +5);

                }}


            batch.end();
        }
        //graficar los dias del eje x

        //grafica los sanos totales vacunados, sin vacunar y curados
        batch.begin();

        if(!verAnterior){
        for (int i=0;i<m.getaEnfermos().size;i++) {

            float sanosTotales = origenY + (float)rangoY*(m.getaSanos().get(i).getY())+
                    (float)rangoY*(m.getaCurados().get(i).getY())+
                    (float)rangoY*(m.getaVacunados().get(i).getY());

            int dias = (int) (origenX+ rangoX*(m.getaEnfermos().get(i).getX()));

            fuente.draw(batch, "O", dias-6,sanosTotales+10);}}

        if(verAnterior){
            for (int i=0;i<m.getaEnfermos0().size;i++) {

                float sanosTotales = origenY + (float)rangoY*(m.getaSanos0().get(i).getY())+
                        (float)rangoY*(m.getaCurados0().get(i).getY())+
                        (float)rangoY*(m.getaVacunados0().get(i).getY());

                int dias = (int) (origenX+ rangoX*(m.getaEnfermos0().get(i).getX()));

                fuente.draw(batch, "O", dias-6,sanosTotales+10);}}



        batch.end();


         //graficar los dias del eje x
        batch.begin();
        fuente.getData().setScale(0.7f, 0.7f);

        fuente.draw(batch, "x = "+game.getTx().getDias(), 390,90);
        fuente.draw(batch, "y = "+game.getTx().getPersonas()+ " (%)", 5,540);

        fuente.getData().setScale(0.5f,0.6f);


        fuente.draw(batch, game.getTx().getMovilidad() + "(M)", 890,565 );

        fuente.draw(batch, game.getTx().getSanos(), 750,565 );


        if(!verAnterior){

        if (m.getDias2()<=100) {
            for (int i=0;i<m.getaEnfermos().size;i = i+3) {
                int dias = (int) (origenX+ rangoX*(m.getaEnfermos().get(i).getX()));

                fuente.draw(batch, ""+ m.getaEnfermos().get(i).getX(), dias, 115);}}

        if (m.getDias2()>100 && m.getDias2()<=200) {
            for (int i=0;i<m.getaEnfermos().size;i = i+6) {
                int dias = (int) (origenX+ rangoX*(m.getaEnfermos().get(i).getX()));

                fuente.draw(batch, ""+ m.getaEnfermos().get(i).getX(), dias, 115);}}

        if (m.getDias2()>200) {
            for (int i=0;i<m.getaEnfermos().size;i = i+9) {
                int dias = (int) (origenX+ rangoX*(m.getaEnfermos().get(i).getX()));

                fuente.draw(batch, ""+ m.getaEnfermos().get(i).getX(), dias, 115);}}}

        if(verAnterior){

            if (m.getDias20()<=100) {
                for (int i=0;i<m.getaEnfermos0().size;i = i+3) {
                    int dias = (int) (origenX+ rangoX*(m.getaEnfermos0().get(i).getX()));

                    fuente.draw(batch, ""+ m.getaEnfermos0().get(i).getX(), dias, 115);}}

            if (m.getDias20()>100 && m.getDias20()<=200) {
                for (int i=0;i<m.getaEnfermos0().size;i = i+6) {
                    int dias = (int) (origenX+ rangoX*(m.getaEnfermos0().get(i).getX()));

                    fuente.draw(batch, ""+ m.getaEnfermos0().get(i).getX(), dias, 115);}}

            if (m.getDias20()>200) {
                for (int i=0;i<m.getaEnfermos0().size;i = i+9) {
                    int dias = (int) (origenX+ rangoX*(m.getaEnfermos0().get(i).getX()));

                    fuente.draw(batch, ""+ m.getaEnfermos0().get(i).getX(), dias, 115);}}}




        batch.end();

        batch.begin();

        //grafical los numeros del eje y

        int maximo = m.getAct().size;
        int redondeado= 100; //el rango maximo es 100%


        for(int i=0; i<=redondeado;i= i+(redondeado/5)) {

            float enfermos = 130+ 3.8f*i;
            fuente.draw(batch, ""+ i, 50,enfermos);
            //	System.out.println(i);
        }


        batch.end();

    }

  /*
    public void graficarLinea(Array<Float> arl, float propX, float propY, Color color){

        int dias = 0;
        int dias2 = 0;
        float datos = 0;
        float datos2 = 0;

        for (int i=1;i<arl.size;i++) {

            dias = (int) (origenX+ rangoX*(m.getaVacunados().get(i).getX()));
            dias2 = (int)(origenX+ rangoX*(m.getaVacunados().get(i-1).getX()));


                datos = origenY + arl.get(i)*propY;
                datos2 = origenY + arl.get(i-1)*propY;



            linea.begin(ShapeType.Line);
            linea.setColor(color);

            if(datos<600){ linea.line(dias,datos,dias2,datos2);}
            linea.end();
        }





    }

   */






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
        ta_atlas.dispose();
        ta_muerto.dispose();
        stage.dispose();
        fuente.dispose();
        fuente_boton.dispose();
        screenshot.dispose();
     //   batch.dispose();

        //	System.out.println("menu prinsipal cerrado");
    }



}

