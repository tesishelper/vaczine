package com.vaczine.Game;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.vaczine.Pantallas.CalendarioIntro;
import com.vaczine.Pantallas.PantallaPaises;

public class Pais {


private PantallaPaises pp;
 private String nombre;
 private String url="";
 private String url2="";
 private TextButton b_boton; //crea botones con texto similares a los de swing



    public Pais(PantallaPaises pp, String nombre, String url){
     this.pp = pp;
     this.nombre = nombre;
     this.url=url;

     AgregarBoton();


 }

    public Pais(PantallaPaises pp, String nombre, String url, String url2){

        this.pp=pp;
        this.nombre = nombre;
        this.url=url;
        this.url2=url2;

        AgregarBoton();

    }


    public void AgregarBoton(){

        TextButton.TextButtonStyle estilo2 = new TextButton.TextButtonStyle();
        estilo2.up = pp.getSk_skin2().getDrawable(nombre);
        estilo2.down = pp.getSk_skin2().getDrawable(nombre);
        estilo2.font = new BitmapFont();

        b_boton = new TextButton("", estilo2);


        b_boton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                pp.getGame().getM().playClick();


            //Apartado especial para Argentina
                if(nombre.equals("Argentina")){

                    pp.getGame().setScreen(new CalendarioIntro(pp.getGame()));   }

             //Apartado especial para Estados unidos

                if(nombre.equals("EstadosUnidos")){

                    if(pp.getGame().getIdioma().equals("eng")){pp.getGame().getOpenURL().openURL(url); }
                    if(pp.getGame().getIdioma().equals("spa")||pp.getGame().getIdioma().equals("por") ){pp.getGame().getOpenURL().openURL(url2); }

                }


             //Resto de los paises
                if(!nombre.equals("Argentina") && !nombre.equals("EstadosUnidos")){

                    if(!url.equals("")){pp.getGame().getOpenURL().openURL(url);}
                    if(!url2.equals("")){pp.getGame().getOpenURL().openURL(url2);}
                }


            }});


    }




    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public String getUrl2() {
        return url2;
    }

    public TextButton getB_boton() {
        return b_boton;
    }

    public void setB_boton(TextButton b_boton) {
        this.b_boton = b_boton;
    }
}
