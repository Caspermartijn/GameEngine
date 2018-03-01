package selector;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class SelectorManager {

	private static int currentRed = 0;
	private static int currentGreen = 0;
	private static int currentBlue = 0;

	public static Vector3f getNewColor() {
		if (currentBlue >= 255) {
			currentGreen++;
			currentBlue = 0;
			if (currentGreen >= 255) {
				currentRed++;
				currentGreen = 0;
			}
		}
		currentBlue++;
		return new Vector3f(currentRed, currentGreen, currentBlue);
	}

    public static Vector3f getColorFromFBOPixel(Vector2f coords, int width, int height) {
        int[] pixels = new int[width * height];
        int bindex;
        ByteBuffer fb = ByteBuffer.allocateDirect(width * height * 3);

        GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, fb);

        BufferedImage imageIn = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < pixels.length; i++) {
            bindex = i * 3;
            pixels[i] = ((fb.get(bindex) << 16)) + ((fb.get(bindex + 1) << 8)) + ((fb.get(bindex + 2) << 0));
        }
        imageIn.setRGB(0, 0, width, height, pixels, 0, width);

        AffineTransform at = AffineTransform.getScaleInstance(1, -1);
        at.translate(0, -imageIn.getHeight(null));

        AffineTransformOp opRotated = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage imageOut = opRotated.filter(imageIn, null);
        int color = imageOut.getRGB((int) (coords.x * width), (int) (coords.y * height));
        Color c = new Color(color);
        return new Vector3f(c.getRed(), c.getGreen(), c.getBlue());
    }

}
