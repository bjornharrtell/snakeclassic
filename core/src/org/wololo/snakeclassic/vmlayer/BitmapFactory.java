package org.wololo.snakeclassic.vmlayer;

public interface BitmapFactory {
	public static final int OPAGUE = 0;
	public static final int BITMASK = 1;
	public static final int TRANSLUCENT = 2;
	
	Object create(int width, int height, int bitmapType);
}
