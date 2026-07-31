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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;


public class AcercaDe implements Screen{
	private VaczineGame game;
	private Mundo m;
	private OrthographicCamera camera;// 
	private Viewport viewport; 
	private SpriteBatch batch;//se usa para dibujar en la pantalla
	private Stage stage; 

	private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
	private Texture img;
	private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
	private TextButton b_volver, b_correo; //crea botones con texto similares a los de swing

	private BitmapFont fuente, fuente_botones;
	private float rX;
	private float rY;

	//tamaño del mundo que quiero ver
	int ancho = 1024;
	int alto = 600;


	public AcercaDe(VaczineGame game) {
		this.game = game;

		Adshandler handler = game.getHandler();
		handler.showAds(false);
		
		
	}
		
		@Override
		public void show() {
		//Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc
			
			m = game.getM();




			img = new Texture("pantalla_AcercaDe_"+game.getIdioma()+".png");//imagen
		//	img.set
			fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
			fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
			batch = game.getBatch(); //se usa para dibujar en la pantalla

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
			estilo.font = fuente;


			//instancia los botones


			b_volver = new TextButton(game.getTx().getMenu(), estilo);
			b_correo = new TextButton("e-mail", estilo);

				//stage maneja elementos que reciben entradas como botones o eventos
				//en este caso se us apara los botones

				stage = new Stage();
				stage.clear();
				Gdx.input.setInputProcessor(stage);



				m.addBoton(b_volver,0.8f,1f,100,60,50,540);
			    m.addBoton(b_correo,0.6f,1f,150,60,250,30);



				stage.addActor(b_volver);
				stage.addActor(b_correo);


				//se agregan los listener para los botones

				b_volver.addListener(new InputListener() {
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

						return true;
					}

					public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
						m.playClick();
						//m.stopMysic();
						game.setScreen(new Root(game));
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


			batch.begin();
			batch.draw(img, 0, 0, ancho,alto);
			batch.end();



			fuente.getData().setScale(0.5f,0.5f);
			fuente.setColor(Color.WHITE);
			
			batch.begin();

			fuente.draw(batch, ""+game.getVersion(), 570, 430);
			
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
			img.dispose();
			sk_skin.dispose();
			ta_atlas.dispose();
			stage.dispose();
			fuente.dispose();
			fuente_botones.dispose();
			//batch.dispose();

		}
		
		
		
		
		
		

}
