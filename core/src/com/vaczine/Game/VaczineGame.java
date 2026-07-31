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


package com.vaczine.Game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.vaczine.Pantallas.MenuInicio;
import com.vaczine.Pantallas.Pantalla_loading;
import com.vaczine.Pantallas.Root;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class VaczineGame extends Game {

	Share share;
	OpenURL openURL;
	ScreenShot screenShot;
	Mundo m, m2;
	Texto tx;

	private SpriteBatch batch;//se usa para dibujar en la pantalla

	float version = (float) 3.19;
	boolean android = true;
	public boolean debug = false;
	int musicaOn = -1;
    static String URLMisAps= "https://sites.google.com/view/tesishelper/inicio";// dirección de mi pagina web
	static String URLMisApseng= "https://sites.google.com/view/tesishelper/inicio/home-eng";// dirección de mi pagina web
	static String URLMisApspor= "https://sites.google.com/view/tesishelper/inicio/inicio-por";// dirección de mi pagina web
	static String URLVacunasMundo= "https://apps.who.int/immunization_monitoring/globalsummary/schedules";//calendario de vacunacion global
    static String URLMinisterioSalud= "https://www.argentina.gob.ar/salud/vacunas";//calendario de vacunacion global
	static String URLVaczinePlay= "https://play.google.com/store/apps/details?id=com.vaczine";//sitio en google play
	static String idiomaRuta = "vaczineSetup/idioma.txt";
	static String eleccionRuta = "vaczineSetup/eleccion.txt";
    static String tictacRuta = "vaczineSetup/tictac.txt";
    static String verPedidoRuta ="vaczineSetup/pedido.txt";
	static String primerArranqueRuta ="vaczineSetup/primerArranque.txt";

	private int simulacionNum =0; //cuenta cuantas simulaciones se estan haciend0, se usa para guardar datos

	String idioma = "eng";

	boolean sirRapido = false;
	boolean mostrarPedido = true;

	Adshandler handler;


//constructor para Android
	public VaczineGame(Share share, OpenURL openURL, ScreenShot screenShot, Adshandler handler){

	this.share= share;
	this.openURL = openURL;
	this.screenShot = screenShot;
	this.handler = handler;


}
// constructor para desktop
public VaczineGame(){

	handler = new Adshandler() {
		@Override
		public void showAds(boolean show) {
			System.out.println("AdMod No implementado en descktop");
		}
	};

	share = new Share() {
		@Override
		public void share(String str) {
			System.out.println(str + URLVaczinePlay);
		}
	};

	openURL = new OpenURL() {
		@Override
		public void openURL(String str) {

			Gdx.net.openURI(str);
		}
	};

	screenShot = new ScreenShot() {
		@Override
		public void screenShot(String str) {



			byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

// This loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
			for (int i = 4; i < pixels.length; i += 4) {
				pixels[i - 1] = (byte) 255;
			}

			Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
			BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
			PixmapIO.writePNG(Gdx.files.external("vaczine/"+str+"_"+fechaAhora()+".png"), pixmap);
			pixmap.dispose();



		}
	};


}




	@Override
	public void create () {

		tx = new Texto();

		//chequear idioma



		if (Gdx.files.local(idiomaRuta).exists()==true){

			FileHandle file = Gdx.files.local(idiomaRuta); //leemos el archivo
			String filetext = file.readString();

			if(filetext.equals("spa")){tx.setEspanol();setIdioma(filetext);}
			if(filetext.equals("eng")){tx.setIngles();setIdioma(filetext);;}
			if(filetext.equals("por")){tx.setPortugues();setIdioma(filetext);;}

			}

		if (Gdx.files.local(idiomaRuta).exists()==false){

			tx.setEspanol();setIdioma("spa");

		}









		batch = new SpriteBatch();

		m = new Mundo(this);
		m2 = new Mundo(this);

		this.setScreen(new Pantalla_loading(this));

	}


	public String fechaAhora(){

		Date date = new Date();
		Calendar calendarG = new GregorianCalendar();
		calendarG.setTime(date);

		String year = String.valueOf(calendarG.get(Calendar.YEAR));
		int month = calendarG.get(Calendar.MONTH)+1;
		String strMonth = String.valueOf(month);
		if(month<10){strMonth = "0"+month;}
		int day =  calendarG.get(Calendar.DAY_OF_MONTH);
		String strDay =  String.valueOf(day);
		if(day<10){strDay =  "0"+day;}
		int hours = calendarG.get(Calendar.HOUR_OF_DAY);
		String strHours = String.valueOf(hours);
		if (hours<10) { strHours = "0"+hours;}
		int minutes = calendarG.get(Calendar.MINUTE);
		String strMinutes = String.valueOf(minutes);
		if(minutes<10){strMinutes = "0"+ minutes;}
		int second = calendarG.get(Calendar.SECOND);
		String strSecond = String.valueOf(second);
		if(second<10){strSecond = "0"+second;}

		return year+strMonth+strDay+strHours+strMinutes+strSecond;
	}



	public Mundo getM() {
		return m;
	}


	public void setM(Mundo m) {
		this.m = m;
	}


	public float getVersion() {
		return version;
	}



	public void dispose () {

		super.dispose();
		batch.dispose();
		m.getS().dispose();
		m.getT_heroeCaido().dispose();
		m.getT_heroe().dispose();
		m.getT_vacuna().dispose();
		m.getTa_actores().dispose();

	}

	public boolean isAndroid() {
		return android;
	}

	public void setAndroid(boolean android) {
		this.android = android;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public int getMusicaOn() {
		return musicaOn;
	}

	public void setMusicaOn(int musicaOn) {
		this.musicaOn = musicaOn;
	}

	public Share getShare() {
		return share;
	}

    public static String getURLMisAps() {
        return URLMisAps;
    }

	public static String getURLVacunasMundo() {
		return URLVacunasMundo;
	}

    public static String getURLMinisterioSalud() {
        return URLMinisterioSalud;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

	public static String getURLVaczinePlay() {
		return URLVaczinePlay;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public static String getIdiomaRuta() {
		return idiomaRuta;
	}

	public Texto getTx() {
		return tx;
	}

	public static String getEleccionRuta() {
		return eleccionRuta;
	}

	public int getSimulacionNum() {
		return simulacionNum;
	}

	public void setSimulacionNum(int simulacionNum) {
		this.simulacionNum = simulacionNum;
	}

    public static String getTictacRuta() {
        return tictacRuta;
    }

	public OpenURL getOpenURL() {
		return openURL;
	}

	public static String getURLMisApseng() {
		return URLMisApseng;
	}

	public static String getURLMisApspor() {
		return URLMisApspor;
	}

	public boolean isSirRapido() {
		return sirRapido;
	}

	public void setSirRapido(boolean sirRapido) {
		this.sirRapido = sirRapido;
	}

	public Mundo getM2() {
		return m2;
	}

	public void setM2(Mundo m2) {
		this.m2 = m2;
	}

	public ScreenShot getScreenShot() {
		return screenShot;
	}

	public boolean isMostrarPedido() {
		return mostrarPedido;
	}

	public void setMostrarPedido(boolean mostrarPedido) {
		this.mostrarPedido = mostrarPedido;
	}

	public static String getVerPedidoRuta() {
		return verPedidoRuta;
	}

	public static String getPrimerArranqueRuta() {
		return primerArranqueRuta;
	}

    public Adshandler getHandler() {
        return handler;
    }

    public void setHandler(Adshandler handler) {
        this.handler = handler;
    }
}
