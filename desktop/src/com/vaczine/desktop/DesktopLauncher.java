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

package com.vaczine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vaczine.Game.VaczineGame;

public class DesktopLauncher  {
	public static void main (String[] arg) {



		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//config.useGL20 = true;
		config.width = 1024;
		config.height =600;
		config.title= "VacZine  v= 3.17v";

		config.resizable = true;

		new LwjglApplication(new VaczineGame(), config);
	}


}
