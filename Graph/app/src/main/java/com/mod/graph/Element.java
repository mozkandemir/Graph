package com.mod.graph;

import android.graphics.Canvas;
import java.util.ArrayList;

public abstract class Element {

	public static ArrayList<Element> elements = new ArrayList<Element>();
	
	public Element() {
		elements.add(this);
	}

	public abstract void tick();
	public abstract void render(Canvas canvas);

}
