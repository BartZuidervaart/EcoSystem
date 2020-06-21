package com.GameEngine.engine.gfx;

public class ImageRequest {
	public Image image;
	public int zDepth;
	public int offX, offY;
	public ImageRequest(Image image, int zDepth, int offX, int offY) {
		super();
		this.image = image;
		this.zDepth = zDepth;
		this.offX = offX;
		this.offY = offY;
	} 
}
