package com.GameEngine.engine.gfx;

public class Font {
	public static final Font STANDARD = new Font("/pixelfont.png");

	private Image fontImage;
	private int[] offsets;
	private int[] widths;
	
	int colorFilter = 10;

	public Font(String path) {
		fontImage = new Image(path);

		offsets = new int[127-32];
		widths = new int[127-32];

		int unicode = 0;

		for (int i = 0; i < fontImage.getW(); i++) {
			if (unicode < offsets.length) {
				int pixel = fontImage.getP()[i];
				if (getRed(pixel) < colorFilter && getGreen(pixel)< colorFilter && getBlue(pixel) > 255-colorFilter && getAlpha(pixel) > 255-colorFilter) {
					offsets[unicode] = i+1;
				}

				if (getRed(pixel)> 255-colorFilter && getGreen(pixel)> 255-colorFilter && getBlue(pixel) < colorFilter && getAlpha(pixel) > 255-colorFilter) {
					widths[unicode] = i - offsets[unicode];
					unicode++;
				}
			}

		}
	}

	public Image getFontImage() {
		return fontImage;
	}

	public void setFontImage(Image fontImage) {
		this.fontImage = fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}
	
	int getRed(int color) {
		return ((color >> 16) & 0xff);
	}
	
	int getBlue(int color) {
		return ((color) & 0xff);
	}
	
	int getGreen(int color) {
		return ((color >> 8) & 0xff);
	}
	
	int getAlpha(int color) {
		return ((color >> 24) & 0xff);
	}

}