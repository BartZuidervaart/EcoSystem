package com.GameEngine.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import com.GameEngine.engine.gfx.Font;
import com.GameEngine.engine.gfx.Image;
import com.GameEngine.engine.gfx.ImageRequest;
import com.GameEngine.engine.gfx.ImageTile;
import com.GameEngine.engine.gfx.ScreenImage;
import com.GameEngine.engine.math.MathUtilities;

public class Renderer {
	MathUtilities m = new MathUtilities();
	private Font font = Font.STANDARD;
	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();

	private int pW, pH;
	private int[] p;
	private int[] zb;
	private int zDepth = 0;
	private boolean processing;

	boolean noStroke = true;
	boolean noFill = false;
	int lineWeight = 1;
	int color = 0xffffffff;
	int strokeColor = 0xffffffff;

	public Renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();

		p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zb = new int[p.length];
	}

	public void clear() {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
			zb[i] = 0;
		}
	}

	public void process() {
		processing = true;
		for (int i = 0; i < imageRequest.size(); i++) {
			ImageRequest ir = imageRequest.get(i);
			zDepth = ir.zDepth;
			drawImage(ir.image, ir.offX, ir.offY);
		}
		imageRequest.clear();
		processing = false;
	}

	public void setPixel(int x, int y, int value) {

		float alpha = ((value >> 24) & 0xff);
		if ((x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0) {
			return;
		}

		if (zb[x + y * pW] > zDepth) {
			return;
		}
		if (alpha == 255) {
			p[x + y * pW] = value;
		} else {
			int pixelColor = p[x + y * pW];
			int newRed = ((pixelColor >> 16) & 0xff)
					- (int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
			int newGreen = ((pixelColor >> 8) & 0xff)
					- (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
			int newBlue = (pixelColor & 0xff) - (int) (((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f));
			p[x + y * pW] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
	}

	public void drawText(String text, int offX, int offY) {

		int newOffY;

		if (!noFill) {
			String[] lines = text.split("\n");
			for (int j = 0; j < lines.length; j++) {
				int offset = 0;
				newOffY = offY + (12 * j);
				for (int i = 0; i < lines[j].length(); i++) {
					int unicode = lines[j].codePointAt(i) - 32;

					// don't try to use unknown unicodes
					if (unicode >= 127 - 32) {
						unicode = 0;
					}

					for (int y = 0; y < font.getFontImage().getH(); y++) {
						for (int x = 0; x < font.getWidths()[unicode]; x++) {
							int pixelColor = font.getFontImage().getP()[(x + font.getOffsets()[unicode])
									+ y * font.getFontImage().getW()];
							setPixel(x + offX + offset, y + newOffY, setAlpha(color, pixelColor));
						}
					}

					offset += font.getWidths()[unicode] + 1;
				}
			}
		}
	}
	
	public int getTextWidth(String text) {
		int offSet = 0;
		for (int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;

			// don't try to use unknown unicodes
			if (unicode >= 127 - 32) {
				unicode = 0;
			}
			offSet += font.getWidths()[unicode] + 1;
		}
		return offSet;
	}

	public void drawImage(Image image, int offX, int offY) {
		if (image.isAlpha() && !processing) {
			imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
			return;
		}

		// Dont render off screen
		if (offX < -image.getW())
			return;
		if (offY < -image.getH())
			return;
		if (offX > pW)
			return;
		if (offY > pH)
			return;

		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();

		// Clipping
		if (offX < 0)
			newX -= offX;
		if (offY < 0)
			newY -= offY;
		if (newWidth + offX > pW)
			newWidth -= newWidth + offX - pW;
		if (newHeight + offY > pH)
			newHeight -= newHeight + offY - pH;
		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
			}
		}
	}

	public void drawScreenResized(ScreenImage image, int offX, int offY, double scaleX, double scaleY) {
		if (scaleX <= 0) {
			scaleX = 1.0;
		}
		if (scaleY <= 0) {
			scaleY = 1.0;
		}

		// Dont render off screen
		if (offX < -image.getW())
			return;
		if (offY < -image.getH())
			return;
		if (offX > pW)
			return;
		if (offY > pH)
			return;
		
		int newX = 0;
		int newY = 0;
		int imageWidth = image.getW();
		int imageHeight = image.getH();
		int newWidth = (int) (image.getW()*scaleX);
		int newHeight = (int) (image.getH()*scaleY);

		// Clipping
		if (offX < 0)
			newX -= offX;
		if (offY < 0)
			newY -= offY;
		if (newWidth + offX > pW)
			newWidth -= newWidth + offX - pW;
		if (newHeight + offY > pH)
			newHeight -= newHeight + offY - pH;

		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[(int) ((x / scaleX) + (y / scaleY) * image.getW())]);
			}
		}
	}

	public void drawMirrorImage(Image image, int offX, int offY) {
		if (image.isAlpha() && !processing) {
			imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
			return;
		}

		// Dont render off screen
		if (offX < -image.getW())
			return;
		if (offY < -image.getH())
			return;
		if (offX > pW)
			return;
		if (offY > pH)
			return;

		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();

		// Clipping
		if (offX < 0)
			newX -= offX;
		if (offY < 0)
			newY -= offY;
		if (newWidth + offX > pW)
			newWidth -= newWidth + offX - pW;
		if (newHeight + offY > pH)
			newHeight -= newHeight + offY - pH;
		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[(image.getW() - x - 1) + y * image.getW()]);
			}
		}
	}

	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();

		// Clipping
		if (offX < 0)
			newX -= offX;
		if (offY < 0)
			newY -= offY;
		if (newWidth + offX > pW)
			newWidth -= newWidth + offX - pW;
		if (newHeight + offY > pH)
			newHeight -= newHeight + offY - pH;
		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY,
						image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
			}
		}
	}

	public void drawRect(int offX, int offY, int width, int height) {
		if (!noStroke) {
			for (int i = 1; i < lineWeight + 1; i++) {
				for (int x = 0; x <= width + (i * 2); x++) {
					setPixel(x + offX - i, offY - i, strokeColor);
					setPixel(x + offX - i, offY + height + (i * 2) - i, strokeColor);
				}
				for (int y = 0; y <= height + (i * 2); y++) {
					setPixel(offX - i, y + offY - i, strokeColor);
					setPixel(offX - i + width + (i * 2), y + offY - i, strokeColor);
				}
			}
		}
		if (!noFill) {
			for (int y = 0; y <= height; y++) {
				for (int x = 0; x <= width; x++) {
					setPixel(x + offX, y + offY, color);
				}
			}
		}
	}

	public void drawLine(int x1, int y1, int x2, int y2, int color) {
		if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
			for (int x = x1; x < Math.abs(x1 - x2); x++) {
				setPixel(x, (int) m.map(x, x1, Math.abs(x1 - x2), y1, y2), color);
			}
		} else {
			for (int y = y1; y < Math.abs(y1 - y2); y++) {
				setPixel((int) m.map(y, y1, Math.abs(y1 - y2), x1, x2), y, color);
			}
		}
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

	int setRed(int color, int red) {
		return (color & 0xff00ffff | (red << 16));
	}

	int setBlue(int color, int blue) {
		return (color & 0xffffff00 | blue);
	}

	int setGreen(int color, int green) {
		return (color & 0xffff00ff | (green << 8));
	}

	int setAlpha(int color, int alpha) {
		return (color & 0x00ffffff) | (getAlpha(alpha) << 24);
	}

	int changeColor(int color, int prevColor) {
		if (color == 0xffffffff) {
			return prevColor;
		} else {
			return setBlue(color, setGreen(color, setRed(color, setAlpha(color, prevColor))));
		}
	}

	public void noFill() {
		noFill = true;
	}

	public void noStroke() {
		noStroke = true;
	}

	public void fill(int color) {
		this.color = color;
		noFill = false;
	}

	public void fill(int r, int g, int b, int a) {
		fill(a << 24 | r << 16 | g << 8 | b);
	}

	public void fill(int r, int g, int b) {
		fill(r, g, b, 255);
	}

	public void stroke(int color) {
		this.strokeColor = color;
		noStroke = false;
	}

	public void stroke(int r, int g, int b, int a) {
		stroke(a << 24 | r << 16 | g << 8 | b);
		noStroke = false;
	}

	public void stroke(int r, int g, int b) {
		stroke(r, g, b, 255);
	}

	public void lineWeight(int size) {
		lineWeight = size;
	}

	public Font getFont() {
		return font;
	}
}