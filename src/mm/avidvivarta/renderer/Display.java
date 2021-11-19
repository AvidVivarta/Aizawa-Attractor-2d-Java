package mm.avidvivarta.renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import mm.avidvivarta.aizawa.AizawaAttractor;
import mm.avidvivarta.renderer.graphics.Screen;
import mm.avidvivarta.renderer.point.Point3D;
import mm.avidvivarta.renderer.point.PointConverter;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static double aspectRatio = 5 / 4;
	public static int width = 200;
	public static int height = (int) (width / aspectRatio);
	public static final String TITLE = "Aizawa Attractor";
	public static int scale = 2;

	private boolean running = false;

	private int xUpdate = 0, yUpdate = 0;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private Thread thread;
	private JFrame frame;
	private AizawaAttractor aa;
	private Screen screen;

	public Display() {

		Dimension size = new Dimension(Display.width * Display.scale, Display.height * Display.scale);
		this.setPreferredSize(size);
		this.init();

	}

	private void init() {

		this.frame = new JFrame();
		this.screen = new Screen(Display.width, Display.height);
		Point3D new3dPoint = new Point3D(0.1, 0.0, 0);
		this.aa = new AizawaAttractor(new3dPoint, 0.01);

	}

	public synchronized void start() {

		this.running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();

	}

	public synchronized void stop() {

		this.running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		double updatesPerSecondsNeeded = 60;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000_000_000.0 / updatesPerSecondsNeeded;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		while (this.running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				this.update();
				updates++;
				delta--;
			}
			this.render();
			frames++;
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				this.frame.setTitle(TITLE + " | " + frames + " fps | " + updates + " ups");
				frames = 0;
				updates = 0;
			}
		}
		this.stop();



	}

	public JFrame getFrame() {

		return this.frame;

	}

	private void update() {

		Point3D point3d = this.aa.iterate();
		Point p = PointConverter.convertPointZLeft(point3d);
		this.xUpdate = (int) p.getX();
		this.yUpdate = (int) p.getY();
		System.out.println("x update: " + this.xUpdate + ", y update: " + this.yUpdate + ", time: "
				+ this.aa.getLapsedTime() + ", point3d: " + this.aa.getCurrentLocation().toString());

	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) { this.createBufferStrategy(3); return; }
		Graphics g = bs.getDrawGraphics();

		int[] pixelValues = screen.renderAizawaPoint(xUpdate, yUpdate);
		for (int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = pixelValues[i];
		}

		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), null);

		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {

		Display display = new Display();
		display.getFrame().setTitle(Display.TITLE);
		display.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.getFrame().add(display);
		display.getFrame().pack();
		display.getFrame().setLocationRelativeTo(null);
		display.getFrame().setResizable(false);
		display.getFrame().setVisible(true);

		display.start();

	}


}
