package org.wololo.snakeclassic.core;

import org.wololo.snakeclassic.core.gfx.Screen;
import org.wololo.snakeclassic.vmlayer.VMContext;

public class Game implements Runnable{
	public VMContext vmContext;

	boolean running = false;

	Screen screen;

	public Game(VMContext vmContext) {
		this.vmContext = vmContext;
		screen = new Screen(this);
	}

	@Override
	public void run() {
		running = true;

		long lastTime = System.nanoTime();
		float unprocessed = 0.0f;
		float nsPerTick = 1000000000.0f / 60.0f;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1.0f) {
				ticks += 1;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			// Thread.sleep(2)

			if (shouldRender) {
				frames += 1;
				screen.render();
				vmContext.render(screen.bitmap);
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick() {

	};
}
