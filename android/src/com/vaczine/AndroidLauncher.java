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


package com.vaczine;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.StreamUtils;
import com.vaczine.Game.Adshandler;
import com.vaczine.Game.OpenURL;
import com.vaczine.Game.ScreenShot;
import com.vaczine.Game.Share;
import com.vaczine.Game.VaczineGame;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;



public class AndroidLauncher extends AndroidApplication implements Share, Adshandler,OpenURL, ScreenShot {

	static String codigoAdmod = "ca-app-pub-6161795650203878/3080936248";// verdadero
	//static String codigoAdmod = "ca-app-pub-3940256099942544/6300978111";// de prueba

	private static final int STORAGE_PERMISSION_CODE = 101;
	private VaczineGame game;

	AdView adView;

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	public static final String PLAYSTORE_LINK= "https://play.google.com/store/apps/details?id=com.vaczine";
	//public static final String PAGINAOFICIAL= "https://vaczine.jimdosite.com/";
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pedirPermiso(); //pedir los permisos para versiones entre API 22 y 29
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		game = new VaczineGame(this,this,this,this);

		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		View gameView = initializeForView(game, config);


		// Add the libgdx view
		layout.addView(gameView);

		// Create and setup the AdMob view

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int height = displayMetrics.heightPixels;
		int width = displayMetrics.widthPixels;


		//System.out.println("Ancho " + height + " alto "+ width);


		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what) {
					case SHOW_ADS:{
						adView.setVisibility(View.VISIBLE);
						break;
					}
					case HIDE_ADS:{
						adView.setVisibility(View.GONE);
						break;
					}
				}
			}
		};


		adView = new AdView(this);

		//adView.setAdSize(AdSize.LARGE_BANNER);

		adView.setAdSize(getAdSize());

		adView.setAdUnitId(codigoAdmod); // programa


		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		// Add the AdMob view
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(adView, adParams);


		// Hook it all up
		setContentView(layout);



	}

	private AdSize getAdSize() {
		// Step 2 - Determine the screen width (less decorations) to use for the ad width.
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float widthPixels = outMetrics.widthPixels;
		float density = outMetrics.density;

		int adWidth = (int) (widthPixels / density);

		// Step 3 - Get adaptive ad size and return for setting on the ad view.
		//return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
		return new AdSize(adWidth,90);
	}

	// metodo para manejar los anuncios de Admod
	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
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


	@Override
	public void screenShot(String str) {

		String now = fechaAhora();
		//android.text.format.DateFormat.format("yyyyMMddhhmmss", now);
		//copiamos todos los pixeles de la pantalla en una lista
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
        // This loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
		for (int i = 4; i < pixels.length; i += 4) {
			pixels[i - 1] = (byte) 255;
		}
		//crear el pixmap con la libreria libgdx
		Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);

		//convertir el pixmap a bitmap para usar con android Api 29 y superior
			PixmapIO.PNG writer = new PixmapIO.PNG((int)(pixmap.getWidth() * pixmap.getHeight() * 1.5f));
			writer.setFlipY(false);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				writer.write(output, pixmap);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				StreamUtils.closeQuietly(output);
				writer.dispose();
				pixmap.dispose();
			}
			byte[] bytes = output.toByteArray();
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

		    if(Build.VERSION.SDK_INT >= 29){	saveImage(bitmap, str+now);}

			if (Build.VERSION.SDK_INT <= 28) {

				//El codigo se aplica si estan los permisos de escritura aprobados
				if (ContextCompat.checkSelfPermission(AndroidLauncher.this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE)==
						PackageManager.PERMISSION_GRANTED) {

					saveImage22(bitmap, str+now);

				}

				else {
					//pedir permiso de nuevo
					pedirPermiso();
				}

				}
		}


		//codigo para guarad una imagen utilizando el modo MadiaStore
	private void saveImage(Bitmap bitmap, @NonNull String name)  {

		boolean ok = false;

		try {
			OutputStream fos;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				ContentResolver resolver = getContentResolver();
				ContentValues contentValues = new ContentValues();
				contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name + ".jpg");
				contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
				contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + "vaczine");
				Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
				fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
			} else {
				String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
				File image = new File(imagesDir, name + ".jpg");
				fos = new FileOutputStream(image);
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			//Objects.requireNonNull(fos).close();
			fos.close();

			ok = true;

		} catch (IOException e) {
			e.printStackTrace();
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(), game.getTx().getCapturarPantallaError() , Toast.LENGTH_SHORT).show();
				}
			});

		}


		if(ok){

			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(), game.getTx().getCapturarPantallaMensaje1() + Environment.DIRECTORY_DCIM + File.separator + "vaczine" , Toast.LENGTH_SHORT).show();
				}
			});}



	}

	// codigo para guardar una imagen para apis 22 a 28

	//Metodo para guardar la imagen

	private void saveImage22(Bitmap bin, String str) {

		boolean ok = false;
		try {
			String root = Environment.getExternalStorageDirectory().getAbsolutePath();
			File myDir = new File(root+"/vaczine");
			myDir.mkdirs();

			String fname = str + ".jpg";
			File file = new File (myDir, fname);
			if (file.exists ()) file.delete ();


			FileOutputStream out = new FileOutputStream(file);

			float alto = bin.getHeight();
			float ancho = bin.getWidth();
			float proporcion = 720*(ancho/alto);

			//System.out.println("alto: "+ alto+ " ancho "+ ancho + " ancho imagen nueva "+  (int)(720*(proporcion)) );

			Bitmap bout = Bitmap.createScaledBitmap(bin, (int)proporcion, 720, false);

			bout.compress(Bitmap.CompressFormat.JPEG, 50, out); //guarda la imagen comprimida

			out.flush();
			out.close();


			ok = true;
			//System.out.println("imagen correctamente guardada en " + myDir.getAbsolutePath());

		}
		catch (Exception e) {
			e.printStackTrace();

			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(), game.getTx().getCapturarPantallaError() , Toast.LENGTH_SHORT).show();
				}
			});

		}

		if(ok){

		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), game.getTx().getCapturarPantallaMensaje1() + Environment.getExternalStorageDirectory().getAbsolutePath() +  "/vaczine" , Toast.LENGTH_SHORT).show();
			}
		});}

	}


	@Override
	public void share(String sAux) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);


		sAux = sAux + PLAYSTORE_LINK +" \n\n";

		sharingIntent.putExtra(Intent.EXTRA_TEXT, sAux);
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	@Override
	public void openURL(String str) {

		try {

			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

		}
		catch (Exception e) {


		}

	}

	//metodos para pedir permisos en versiones de andrid superiores a api 22 y menor que 29

	public void pedirPermiso(){

		if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= Build.VERSION_CODES.P ){ //cheque la version de android

			if (ContextCompat.checkSelfPermission(AndroidLauncher.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {

				checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

			}}
	}

	// Function to check and request permission
	public void checkPermission(String permission, int requestCode){

		// Checking if permission is not granted
		if (ContextCompat.checkSelfPermission(AndroidLauncher.this,	permission)== PackageManager.PERMISSION_DENIED) {

			ActivityCompat.requestPermissions(
					AndroidLauncher.this,
					new String[] { permission },
					requestCode);
		}
		else {	//Toast.makeText(AndroidLauncher.this,"Permission already granted",Toast.LENGTH_SHORT).show();

		}
	}



}
