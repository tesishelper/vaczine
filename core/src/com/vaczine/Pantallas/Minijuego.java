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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Controles;
import com.vaczine.Game.VaczineGame;
import com.vaczine.Game.Mundo;

public class Minijuego implements Screen{

    private VaczineGame game;

    private Controles c;
    private InputMultiplexer inputMultiplexer;
    private ShapeRenderer caja;
    private OrthographicCamera camera;//
    private Viewport viewport;
    private SpriteBatch batch;//se usa para dibujar en la pantalla
    private Stage stage;
    private BitmapFont fu_fuente, fuente, fuenteAmarilla;
    private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
    private Texture imgcontrol,screenshot,imgvictoria,share,share2;
    private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private TextButton b_repetir,b_siguiente, b_salir, b_capturar, b_terminar,b_pausar,b_vacunar; //crea botones con texto similares a los de swing
    private Mundo m;
    private  boolean vercamara = false;
    /*
    private int verDato =1; //0 ocultar 1 ver
    private int tiempoFuera =1; //se usa para que se toque una sol vez el sonido de fin de juego
    private int mandeEnfermos = 1; //se usa para que el metodo agregarenfermo() se ejecute una sola vez dentro de render()
*/
    private boolean gameOver = false;
    private Vector2 posHeroe, fire;

    protected Sound GameOver,GameWin;
    private boolean isGameOverPlayed = false;
    //tamaño del mundo que quiero ver
    int ancho = 1024;
    int alto = 600;
    private boolean botonVisifle=false;

    public Minijuego(VaczineGame game) {
        this.game = game;

        m = game.getM2();

        Adshandler handler = game.getHandler();
        handler.showAds(false);

        //setear el tiempo


        m.setDelta3();
        m.setDelta5();


    }

    @Override
    public void show() {
        //Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc
        ;


        imgcontrol = new Texture("control.png");//imagen
        screenshot = new Texture("screenshot.png");
        imgvictoria = new Texture("victoria.png");
        //share= new Texture("share.png");//imagen
       // share2 = new Texture("share2.png");//imagen
        GameOver = Gdx.audio.newSound(Gdx.files.internal("game-over.mp3"));
        GameWin = Gdx.audio.newSound(Gdx.files.internal("jingle-win.mp3"));

        caja = new ShapeRenderer();


        fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuente.setColor(Color.WHITE);

        fu_fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuenteAmarilla = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
        fuenteAmarilla.setColor(255f/255f, 228f/255f, 112f/255f, 1);

        batch = game.getBatch();//new SpriteBatch(); //se usa para dibujar en la pantalla
        //crear la camara
        camera = new OrthographicCamera(ancho, alto);
        //crear el viewport
        viewport = new StretchViewport(ancho, alto); //no lo estoy usando ahora
        // viewport.setCamera(camara);

        posHeroe = new Vector2();
        fire = new Vector2();


        ta_atlas = new TextureAtlas("pack31.pack");//carga el atlas de texturas donde estan los botones
        sk_skin = new Skin();
        sk_skin.addRegions(ta_atlas);

        //instansia los elemrntos de un boton
        //la posocion up y down usando imagenes y el texto que tiene cada uno

        TextButtonStyle estilo = new TextButtonStyle();
        estilo.up = sk_skin.getDrawable("boton_up");
        estilo.down = sk_skin.getDrawable("boton_down");
        estilo.font = fu_fuente;




        b_pausar = new TextButton(">/=", estilo);
        b_terminar = new TextButton(game.getTx().getSalir(), estilo);
        b_repetir = new TextButton(game.getTx().getRepetirNivel(), estilo);
        b_siguiente = new TextButton(game.getTx().getSiguienteNivel(), estilo);
     //   b_salir = new TextButton(game.getTx().getSalir(), estilo);
        b_capturar = new TextButton("", estilo);





        TextButtonStyle estilo2 = new TextButtonStyle();
        estilo2.up = sk_skin.getDrawable("botonFireUP");
        estilo2.down = sk_skin.getDrawable("botonFireDown");
        estilo2.font = fu_fuente;

        b_vacunar = new TextButton(game.getTx().getVacunar(), estilo2);
        b_vacunar.setSize(90,100);
        b_vacunar.setPosition(800,60);

            //stage maneja elementos que reciben entradas como botones o eventos
            //en este caso se us apara los botones

            stage = new Stage();
            stage.clear();




            //instansia los elemrntos de un boton


        //instancia los botones


        m.addBoton(b_pausar,0.8f,1f,200,60,717,540);
        m.addBoton(b_terminar,0.8f,1f,200,60,924,540);
        m.addBoton(b_capturar,0.55f,1f,200,60,308,430);
        m.addBoton(b_repetir,0.55f,1f,200,60,512,430);
        m.addBoton(b_siguiente,0.55f,1f,200,60,714,430);
       // m.addBoton(b_salir,0.8f,1f,200,60,814,120);


        stage.addActor(b_terminar);
        stage.addActor(b_pausar);
        stage.addActor(b_repetir);
        stage.addActor(b_siguiente);
       // stage.addActor(b_salir);
        stage.addActor(b_capturar);

        if (game.isAndroid()==true){
            stage.addActor(b_vacunar);

        }
        b_capturar.setVisible(false);
        b_siguiente.setVisible(false);
        b_repetir.setVisible(false);
       // b_salir.setVisible(false);

            //se agregan los listener para los botones

            b_vacunar.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    m.getHeroe().vacunar();
                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                    // m.getHeroe().vacunar();
                }});



            b_terminar.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    m.playClick();
                //    if(game.getMusicaOn()==1){ m.playMusic();}//musica de ambiente

                    game.setScreen(new MinijuegoIntro(game));
                }});

            b_pausar.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    m.playClick();
                    m.setDelta3();
                    m.setDelta5();
                    for (int i=0;i<m.getAct().size;i++) {

                        m.getAct().get(i).setTimeEnfermo();//
                    }
                    m.setPlay(m.getPlay()*(-1)) ;
                }});


        b_repetir.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

               play_nivel(m.getNivel());


            }});

        b_siguiente.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
              m.setNivel(m.getNivel()+1);
              if(m.getNivel()>4){m.setNivel(4);}

              play_nivel(m.getNivel());


            }});



        b_capturar.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.getM().playClick();
                b_pausar.setVisible(false);
                b_terminar.setVisible(false);
                b_repetir.setVisible(false);
                b_siguiente.setVisible(false);
                //b_salir.setVisible(false);
                b_capturar.setVisible(false);
                vercamara= false;

                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {


                game.getScreenShot().screenShot("Game_");

                b_pausar.setVisible(true);
                b_terminar.setVisible(true);
                b_repetir.setVisible(true);
                b_siguiente.setVisible(true);
               // b_salir.setVisible(true);
                b_capturar.setVisible(true);
                vercamara= true;
            }});


//



//controles para mover la pieza comodin

        c = new Controles(m.getHeroe(),m.getRo(), camera); //instancia los controles
        inputMultiplexer = new InputMultiplexer();//esto es para manejar multiples controles

        //Agregar los contrloles del stage y de tocar la pantalla

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(c);

        Gdx.input.setInputProcessor(inputMultiplexer);



    }

    @Override
    public void render(float delta) {
        //Se encarga de dibujar la pantalla
        Gdx.gl.glClearColor(m.getColorFondo().r,m.getColorFondo().g,m.getColorFondo().b,m.getColorFondo().a);

//        Gdx.gl.glClearColor(0f/255f, 25f/255f, 100f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update(); //

        stage.getViewport().setCamera(camera);//el estage usará el vieport de la camara
        stage.act(delta);

        batch.setProjectionMatrix(camera.combined); //batch usa el punto de vista de la camara
        caja.setProjectionMatrix(camera.combined); // shape usa el punto de vista de la camara



        //actores
        m.update2();

        m.getHeroe().update(delta);


        if(m.getPlay()==1) {

            m.contadorReverso();
            m.agregarEnfermoTiempo(10000);

        }//cuenta el tiempo de juego



        for (int i=0; i< m.getAct().size; i++) {
            if(m.getAct().get(i).getEstado()!= 4) {//si el actor no esta muerto
                m.getAct().get(i).update(delta);
                m.getAct().get(i).verActor(batch);
                if(game.debug==true){ m.getAct().get(i).verBorde(caja,fuente,batch, delta);} //se ve el rectangulo de cada personaje
            }
        }

        //dibujar nuestro heroe


         //ver vacunas
         for (int i=0; i< m.getaVacunas().size; i++) {

         m.getaVacunas().get(i).verObjeto(batch);}
          m.getHeroe().verHeroe(batch);


        fuenteAmarilla.getData().setScale(1,1);


        batch.begin();
        fuenteAmarilla.draw(batch, "|"+ game.getTx().getDias()+": " + m.getSegundos(),5, 595);

        fuenteAmarilla.draw(batch, "|" + game.getTx().getPuntos()+": " + (m.contarVacunados(m.getAct(),9)-m.contarEnfermos(m.getAct(),9)),5, 545);

        if (m.getNivel()==1){fuenteAmarilla.draw(batch, "|Noob" ,300, 595);}
        if (m.getNivel()==2){fuenteAmarilla.draw(batch, "|Normal" ,300, 595);}
        if (m.getNivel()==3){fuenteAmarilla.draw(batch, "|Pro" ,300, 595);}
        if (m.getNivel()==4){fuenteAmarilla.draw(batch, "|Hacker" ,300, 595);}


        batch.end();


        //botones

        stage.draw();//dibuja los botones definidos en resize

        batch.begin();
        if(vercamara) {batch.draw(screenshot, 280, 440, 50,40);}//
        batch.end();


        //Terminamos sin problemas
        if(m.getSegundos()==0 || m.contarSanos(m.getAct(),9)==0) {
            verBotones();
            b_vacunar.setVisible(false);
            playGameWin();
            m.setPlay(0) ;

            //
           // batch.draw(share2, 135, 90, 140,35);//

            caja.setColor(Color.BLACK);
            caja.begin(ShapeType.Filled);
            caja.setColor(Color.BLACK);
            caja.rect(0, 120,  ancho, 300);
            caja.end();


            batch.begin();
            fuenteAmarilla.draw(batch, game.getTx().getFelicitaciones(), 280, 400);
            fuenteAmarilla.draw(batch, game.getTx().getPersonasVacunadas()+": +" +m.contarVacunados(m.getAct(),9)+ " "+game.getTx().getPuntosMinuscula() ,220, 320);
            fuenteAmarilla.draw(batch, game.getTx().getPersonasEnfermas()+": -" +m.contarEnfermos(m.getAct(),9)+ " "+game.getTx().getPuntosMinuscula(),220, 270);
            fuenteAmarilla.draw(batch, game.getTx().getBonoPorNoMorir()+" : +" + m.getBono() +" "+game.getTx().getPuntosMinuscula(),220, 220);
            fuenteAmarilla.draw(batch, game.getTx().getPuntageTotal()+" : " + (m.getBono()+m.contarVacunados(m.getAct(),9)-m.contarEnfermos(m.getAct(),9))+ " "+game.getTx().getPuntosMinuscula() ,220, 170);


            batch.draw(imgvictoria, 5, 10, 180,200);

            batch.end();



        }

        //perdimos la partida
        if(m.isGameOver()){
            verBotones();
            b_vacunar.setVisible(false);
            m.setPlay(0) ;
            playGameOver();

            caja.setColor(Color.BLACK);
            caja.begin(ShapeType.Filled);
            caja.setColor(Color.BLACK);
            caja.rect(0, 120,  ancho, 300);
            caja.end();

            batch.begin();


           // batch.draw(share2, 135, 90, 140,35);//

            fuenteAmarilla.draw(batch, game.getTx().getJuegoTerminado(), 280, 400);
            fuenteAmarilla.draw(batch, game.getTx().getPersonasVacunadas()+": +" +m.contarVacunados(m.getAct(),9)+ " "+game.getTx().getPuntosMinuscula() ,220, 320);
            fuenteAmarilla.draw(batch, game.getTx().getPersonasEnfermas()+": -" +m.contarEnfermos(m.getAct(),9)+ " "+game.getTx().getPuntosMinuscula()  ,220, 270);
            fuenteAmarilla.draw(batch, game.getTx().getBonoPorNoMorir()+" : +" + m.getBono() + " "+game.getTx().getPuntosMinuscula() ,220, 220);
            fuenteAmarilla.draw(batch, game.getTx().getPuntageTotal()+" : " + (m.getBono()+m.contarVacunados(m.getAct(),9)-m.contarEnfermos(m.getAct(),9))+ " "+game.getTx().getPuntosMinuscula()  ,220, 170);

            batch.end();



        }

        if(game.isAndroid() && b_vacunar.isVisible() ){

            batch.begin();
            batch.draw(imgcontrol,70,20,180,200);
            batch.end();


        }





    }


    public void play_nivel(int i){


      //  m.stopMysic();
        m.dispose();
        game.setM2(new Mundo(game));
        m = game.getM2();
        m.playClick();
       if(i==1){

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
       }

        if(i==2){

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
        }
        if(i==3){
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
        }
        if(i==4){
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
        }
        game.setScreen(new Minijuego(game));

    }

    // metodos

    public void playGameOver(){

        if(isGameOverPlayed==false){

            GameOver.play();
            isGameOverPlayed=true;

        }

    }

    public void verBotones(){

     if(!botonVisifle){
         b_capturar.setVisible(true);
         b_siguiente.setVisible(true);
         b_repetir.setVisible(true);

         vercamara=true;


         botonVisifle = true;
     }



    }


    public void playGameWin(){

        if(isGameOverPlayed==false){

            GameWin.play();
            isGameOverPlayed=true;
        }

    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height);// actualiza el stage para que no camve las proporciones de los elementos
        camera.setToOrtho(false,ancho, alto); // ajusta la camara para el ancho y alto predefinido


    }

    //comando para controlar al personaje

   /* public void controles(){

    if (Gdx.input.isTouched()) {


        Vector2 pos = new Vector2().set(Gdx.input.getX(), alto-Gdx.input.getY());

         if(pos.x>154 && pos.y< 540){
             m.getHeroe().setPosicion(pos); }

      //   System.out.println(pos);
     }


    if (Gdx.input.isKeyJustPressed(Input.Keys.A)){ m.getHeroe().vacunar();}


    if(Gdx.input.justTouched()){//solo un toque

        fire.set(Gdx.input.getX(), alto-Gdx.input.getY());
      if(fire.x<m.Xsize(15) && (alto-fire.y)< m.Ysize(90)){
        m.getHeroe().vacunar();

        }}

    }*/




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
        stage.dispose();
        GameOver.dispose();
        GameWin.dispose();
        imgcontrol.dispose();
        fu_fuente.dispose();
        fuente.dispose();
        fuenteAmarilla.dispose();
        screenshot.dispose();
        imgvictoria.dispose();
     //   share2.dispose();

    }



}
