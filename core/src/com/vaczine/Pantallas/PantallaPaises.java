package com.vaczine.Pantallas;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.Pais;
import com.vaczine.Game.VaczineGame;


public class PantallaPaises implements Screen{
    private VaczineGame game;
    private Mundo m;
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
   // private Texture  img;

    private TextureAtlas ta_atlas,ta_banderas;//carga imagenes de atlas de texturas
    private Skin sk_skin,sk_skin2;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_volver, b_otros,b_correo; //crea botones con texto similares a los de swing

    private BitmapFont fuente, fuente_botones;
    private float rX;
    private float rY;

    private int indexPais=1;

    private Array<Pais> aPaises;

    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;


    public PantallaPaises (VaczineGame game) {
        this.game = game;



        Adshandler handler = game.getHandler();
        handler.showAds(true);

    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc

        m = game.getM();


        //  img = new Texture("pantalla_paises.png");//imagen
        //	img.set
        fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);
        batch = game.getBatch();//new SpriteBatch(); //se usa para dibujar en la pantalla

        //crear la camara
        camera = new OrthographicCamera(ancho, alto);
        //crear el viewport
        viewport = new StretchViewport(ancho, alto); //no lo estoy usando ahora



        ta_atlas = new TextureAtlas("pack31.pack");//carga el atlas de texturas donde estan los botones
        sk_skin = new Skin();
        sk_skin.addRegions(ta_atlas);

        ta_banderas = new TextureAtlas("banderas.pack");//carga el atlas de texturas donde estan los botones
        sk_skin2 = new Skin();
        sk_skin2.addRegions(ta_banderas);

         //instansia los elemrntos de un boton
        //la posocion up y down usando imagenes y el texto que tiene cada uno

        // viewport.setCamera(camara);

        aPaises = new Array<>();
        aPaises.add(new Pais(this,"Argentina",""));
        aPaises.add(new Pais(this,"Bolivia","https://pai.minsalud.gob.bo/"));
        aPaises.add(new Pais(this,"Brasil","https://www.gov.br/saude/pt-br/vacinacao/calendario" ));
        aPaises.add(new Pais(this,"Chile","https://www.minsal.cl/programa-nacional-de-inmunizaciones/"));
        aPaises.add(new Pais(this,"Colombia", "https://www.minsalud.gov.co/salud/publica/Vacunacion/Paginas/pai.aspx"));
        aPaises.add(new Pais(this,"CostaRica", "https://www.ministeriodesalud.go.cr/index.php/biblioteca/material-educativo/material-de-comunicacion/vacunas"));
        aPaises.add(new Pais(this,"Ecuador", "https://www.salud.gob.ec/programa-ampliado-de-inmunizaciones-pai/"));
        aPaises.add(new Pais(this,"España", "https://www.sanidad.gob.es/areas/promocionPrevencion/vacunaciones/home.htm"));
        aPaises.add(new Pais(this,"EstadosUnidos", "https://www.cdc.gov/vaccines/schedules/index.html",
                                                    "https://www.cdc.gov/vaccines/schedules/easy-to-read/child-shell-easyread-sp.html"));
        aPaises.add(new Pais(this,"Honduras", "https://www.salud.gob.hn/sshome/index.php/pai#informacion"));
        aPaises.add(new Pais(this,"Italia", "http://www.salute.gov.it/portale/vaccinazioni/dettaglioContenutiVaccinazioni.jsp?lingua=italiano&id=4829&area=vaccinazioni&menu=vuoto"));
        aPaises.add(new Pais(this,"Mexico", "https://www.gob.mx/salud%7Ccensia/articulos/esquema-de-vacunacion-131150?idiom=es"));
        aPaises.add(new Pais(this,"Panama", "http://www.minsa.gob.pa/programa/programa-ampliado-de-inmunizacion"));
        aPaises.add(new Pais(this,"Paraguay", "https://pai.mspbs.gov.py/esquema-regular-de-vacunacion/"));
        aPaises.add(new Pais(this,"Peru", "https://www.gob.pe/22037-esquema-regular-de-vacunacion-por-etapas-de-vida-en-el-peru"));
        aPaises.add(new Pais(this,"Portugal", "https://www.dgs.pt/paginas-de-sistema/saude-de-a-a-z/programa-nacional-de-vacinacao/programa-nacional-de-vacinacao.aspx"));
        //aPaises.add(new Pais(this,"PuertoRico","https://www.salud.gov.pr/CMS/107"));
        aPaises.add(new Pais(this,"RepublicaDominicana","https://repositorio.msp.gob.do/handle/123456789/1126"));
        aPaises.add(new Pais(this,"Uruguay", "https://www.gub.uy/ministerio-salud-publica/comunicacion/publicaciones/vacunas"));
        //aPaises.add(new Pais(this,"Venezuela", "https://drive.google.com/file/d/0By6RZhEqt4ajc3djTXhBVnlLTFk/view?usp=sharing&resourcekey=0-WiodkfQAUxIOaXefJXPfRw",
          //                                       "https://drive.google.com/file/d/0By6RZhEqt4ajTmNRTlFuOHZDaXc/view?usp=sharing&resourcekey=0-nbl05AuK6bCCtbDWFa71nw"));



        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);



       //Colocar las banderas de cada pais en la pantalla

        int botonX= 77;
        int botonY= 400;

        for(int i=0; i<aPaises.size;i++){

        // TextButton tb = new TextButton("", aEstilo.get(i));
            m.addBoton(aPaises.get(i).getB_boton(),0.60f,0.60f,140,70,botonX,botonY);

            botonX += 145;
            if(i==6){botonX=77; botonY= 320;}
            if(i==13){botonX=77; botonY= 240;}


            stage.addActor(aPaises.get(i).getB_boton());

        }


        TextButtonStyle estilo = new TextButtonStyle();
        estilo.up = sk_skin.getDrawable("boton_up");
        estilo.down = sk_skin.getDrawable("boton_down");
        estilo.font = fuente_botones;

        //instancia los botones normales


        b_volver = new TextButton(game.getTx().getMenu(), estilo);
        b_otros = new TextButton(game.getTx().getOtrosPaise(), estilo);
        b_correo = new TextButton("e-mail", estilo);

        m.addBoton(b_correo,0.60f,1f,140,50,700,5);
        m.addBoton(b_volver,0.8f,1f,100,60,50,540);
        m.addBoton(b_otros,0.50f,1f,140,70,950,240);



        stage.addActor(b_volver);
        stage.addActor(b_otros);
        //stage.addActor(b_correo);



        //se agregan los listener para los botones normales

        b_volver.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
              //  m.stopMysic();
                game.setScreen(new Root(game));
            }});

        b_otros.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
                game.getOpenURL().openURL(game.getURLVacunasMundo());
            }});

        b_correo.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                m.playClick();
                game.getOpenURL().openURL("mailto:tesishelper@gmail.com");
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

       // batch.begin();
     //   batch.draw(img,0,0,ancho,alto);
      //  batch.end();

        fuente.getData().setScale(0.75f,1f);

        batch.begin();

        fuente.draw(batch,game.getTx().getCalendarioPorPaises(), 100, 580,900,1,true);

        fuente.getData().setScale(0.6f,0.7f);

        fuente.draw(batch,game.getTx().getSeleccionesPais(), 5, 520,1000,-1,true);

        fuente.getData().setScale(0.6f,0.7f);
        //fuente.draw(batch,game.getTx().getSiDeseaAgregarPais(), 5, 30,1000,-1,true);

        //declaracion

        fuente.getData().setScale(0.55f,0.65f);
        fuente.setColor(255f/255f, 228f/255f, 112f/255f, 1);
        fuente.draw(batch, game.getTx().getDeclaracion(), 5, 230);
        fuente.draw(batch, game.getTx().getDeclaracion2(), 15, 200);



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
        fuente_botones.dispose();
        fuente.dispose();
        sk_skin.dispose();
        sk_skin2.dispose();
        ta_atlas.dispose();
        ta_banderas.dispose();
        stage.dispose();
       // img.dispose();
        // batch.dispose();

    }


    public VaczineGame getGame() {
        return game;
    }

    public TextureAtlas getTa_banderas() {
        return ta_banderas;
    }

    public Skin getSk_skin2() {
        return sk_skin2;
    }
}
