package Texturing;


import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
///http://wiki.lwjgl.org/wiki/The_Quad_textured.html
public class Texture {
    private int width;
    private int height;
    private int texID;
    private int textureBank;
    private ByteBuffer imageBuffer;


    public static Texture loadTexture(String fileName, int textureBank)
    {
        return decode(fileName, textureBank);
    }

    private static Texture decode(String fileName, int textureBank)
    {
        Texture texture = new Texture();
        try {
            InputStream input = new FileInputStream(fileName);
            PNGDecoder decoder = new PNGDecoder(input);

            texture.width = decoder.getWidth();
            texture.height = decoder.getHeight();
            texture.imageBuffer = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());

            decoder.decode(texture.imageBuffer , decoder.getWidth() * 4, PNGDecoder.Format.RGBA);

            //set buffer to read
            texture.imageBuffer.flip();
            input.close();

            texture.texID = createTextureObject(textureBank);
        } catch (FileNotFoundException e) {
            texture = null;
            e.printStackTrace();
        } catch (IOException e) {
            texture = null;
            e.printStackTrace();
        }

        return texture;
    }

    private int getTextureHandle()
    {
        return texID;
    }

    //pobiera id wygenerowanej tekstury, binduje teksture do opengl
    private static int createTextureObject(int textureBank) {

        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(textureBank);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        return texId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getImageBuffer() {
        return imageBuffer;
    }
}
