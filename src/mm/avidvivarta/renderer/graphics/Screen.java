package mm.avidvivarta.renderer.graphics;

import java.util.Random;

public class Screen {

	private static final int MAP_SIZE = 8;
	private static final int MAP_SIZE_MASK = MAP_SIZE - 1;
	private static int tileIndex = 0;

	private int width, height;
	private long initialTime = System.currentTimeMillis();

	private int[] pixels;
	private int[] tiles = new int[MAP_SIZE * MAP_SIZE];

	private Random random = new Random();

	public Screen(int width, int height) {

		this.width = width;
		this.height = height;

		this.pixels = new int[this.height * this.width];

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}

	}

	public int[] render(int xOffset, int yOffset) {

		for (int y = 0; y < this.height; y++) {
			int yy = y + yOffset;
			for (int x = 0; x < this.width; x++) {
				int xx = x + xOffset;
				int ti = ((xx >> 4) & MAP_SIZE_MASK) + ((yy >> 4) & MAP_SIZE_MASK) * this.width;
				this.pixels[x + y * this.height] = this.tiles[ti];
			}
		}
		return this.pixels;

	}

	public void clear() {

		for (int i : this.pixels)
			i = 0x000000;

	}

	public int[] renderAizawaPoint(int xUpdate, int yUpdate) {


		long timeNow = System.currentTimeMillis();
		if (timeNow - initialTime >= 1000) { initialTime = timeNow; tileIndex = ++tileIndex % this.tiles.length; }
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (x == xUpdate && y == yUpdate) { this.pixels[x + y * this.width] = this.tiles[tileIndex]; }
			}
		}
		return this.pixels;

	}

}