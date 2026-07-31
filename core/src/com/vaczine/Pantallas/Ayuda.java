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

//import com.vaczine.Game.Mundo;
import com.vaczine.Game.Adshandler;
import com.vaczine.Game.Mundo;
import com.vaczine.Game.VaczineGame;

public class Ayuda implements Screen{
	private VaczineGame game;

	private OrthographicCamera camera;// 
	private Viewport viewport; 
	private SpriteBatch batch;//se usa para dibujar en la pantalla
	private Stage stage; 
	private BitmapFont fuente,fuente_botones;
	private TextureAtlas ta_atlas;//carga imagenes de atlas de texturas
	private Texture img_base, img;
	private Skin sk_skin;         //almacena recursos de atlas como imagenes y colores para ser usados mas facilmente
    private int pagina =0;

	private TextButton b_0,b_10, b_volver;//crea botones con texto similares a los de swing

	private TextButton b_back;
	private Mundo m;

	//tamaño del mundo que quiero ver
	int ancho = 1024;
	int alto = 600;
	
	public Ayuda(VaczineGame game) {
		this.game = game;


		Adshandler handler = game.getHandler();
		handler.showAds(false);
		
	}
		
		@Override
		public void show() {
		//Carga los elemento que se usaran en el programa texturas, fuentes, sonidos etc
			m = game.getM();

			img_base= new Texture("pantalla_Ayuda00_base.png");//imagen
			img = new Texture("pantalla_Ayuda00_"+game.getIdioma()+".png");//imagen
			//img.set
			fuente_botones = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);
			fuente = new BitmapFont(Gdx.files.internal("Arial_35.fnt"),false);

			
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
			b_volver = new TextButton(game.getTx().getMenu(), estilo);

			b_0 = new TextButton(" < ", estilo);

			b_10 = new TextButton(" > ", estilo);



				//stage maneja elementos que reciben entradas como botones o eventos
				//en este caso se us apara los botones

				stage = new Stage();
				stage.clear();
				Gdx.input.setInputProcessor(stage);




				m.addBoton(b_volver,0.8f,1f,100,60,50,540);

				m.addBoton(b_0,0.8f,1f,82,50,41,0);

				m.addBoton(b_10,0.8f,1f,82,50,983,0);



				//stage.addActor(b_back);
				stage.addActor(b_volver);
				stage.addActor(b_0);
				/**/
				stage.addActor(b_10);



				//se agregan los listener para los botones

				b_volver.addListener(new InputListener() {
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

						return true;
					}

					public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
						game.getM().playClick();
					//	game.getM().stopMysic();
						game.setScreen(new Root(game));
					}});


				b_0.addListener(new InputListener() {
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

						return true;
					}

					public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
						game.getM().playClick();

						pagina=pagina-1;
						if(pagina<0){pagina=11;}

                         verPagina(pagina);


						//game.setScreen(new Ayuda(game));
					}});


				b_10.addListener(new InputListener() {
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

						return true;
					}

					public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
						game.getM().playClick();

						pagina=pagina+1;
						if(pagina>11){pagina=0;}
						verPagina(pagina);
						//game.setScreen(new Ayuda10(game));
					}});




		}


		public void verPagina(int pag){

			switch (pag){

				case 0: img = new Texture("pantalla_Ayuda00_"+game.getIdioma()+".png");//imagen
						img_base= new Texture("pantalla_Ayuda00_base.png");//imagen
				break;
				case 1: img = new Texture("pantalla_Ayuda01_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Ayuda01_base.png");//imagen
					break;
				case 2: img = new Texture("pantalla_Ayuda02_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Ayuda02_base.png");//imagen
					break;
				case 3: img = new Texture("pantalla_Ayuda03_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Ayuda03_base.png");//imagen
					break;
				case 4: img = new Texture("pantalla_Ayuda04_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Ayuda04_base.png");//imagen
					break;
				case 5: img = new Texture("pantalla_Ayuda05_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Ayuda05_base.png");//imagen
					break;
				case 6: img = new Texture("pantalla_Ayuda06_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Ayuda06_base.png");//imagen
					break;
				case 7: img = new Texture("pantalla_Ayuda07_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Ayuda07_base.png");//imagen
					break;
				case 8: img = new Texture("pantalla_Ayuda08_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Ayuda08_base.png");//imagen
					break;
				case 9: img = new Texture("pantalla_Ayuda1SIR_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Nula.png");//imagen
					break;
				case 10: img = new Texture("pantalla_Ayuda2SIR_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Nula.png");//imagen
					break;
				case 11: img = new Texture("pantalla_Ayuda3SIR_"+game.getIdioma()+".png");//imagen
					img_base= new Texture("pantalla_Nula.png");//imagen
					break;

			}



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
			batch.draw(img_base,0,0,ancho,alto);
			batch.draw(img, 0, 0, ancho,alto);
            fuente.getData().setScale(0.8f,0.8f);
			fuente.draw(batch, ""+(pagina+1)+"/12", 450, 35);
			batch.end();


			//botones

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
			sk_skin.dispose();

			ta_atlas.dispose();
			img.dispose();
			img_base.dispose();
			stage.dispose();
			fuente.dispose();
			fuente_botones.dispose();
		//	batch.dispose();
		//	System.out.println("menu prinsipal cerrado");
		}
		
		
		
		
		
		

}
