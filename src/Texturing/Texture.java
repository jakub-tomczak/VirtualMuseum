package Texturing;


import Utils.Constants;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glGenTextures;

///http://wiki.lwjgl.org/wiki/The_Quad_textured.html
public class Texture{
    private int width;
    private int height;
    private int texID;
    private int textureBank;
    private ByteBuffer imageBuffer;

    private static List<Integer> textures = new ArrayList<>();
    public static Texture loadTexture(String filename, int textureBank, String path)
    {
        return decode(filename, textureBank, path);
    }

    public static Texture loadTexture(String filename, int textureBank)
    {
        return decode(filename, textureBank, Constants.TEXTURES_PATH);
    }

    private static Texture decode(String filename, int textureBank, String path)
    {
        Texture texture = new Texture();
        try {
            InputStream input = new FileInputStream(path + filename + ".png");
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

            //bez tworzenia mipmapy nie działa
            // Upload the texture data and generate mip maps (for scaling)
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, texture.width, texture.height, 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texture.imageBuffer);
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);




        } catch (FileNotFoundException e) {
            texture = null;
            e.printStackTrace();
        } catch (IOException e) {
            texture = null;
            e.printStackTrace();
        }

        return texture;
    }

    private int getTextureHandler()
    {
        return texID;
    }

    //pobiera id wygenerowanej tekstury, binduje teksture do opengl
    private static int createTextureObject(int textureBank) {

        // tworzy nową teksturę w pamięci
        int texId = glGenTextures();

        //trzeba bindowac bo inaczej wywala blad
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        return texId;
    }

    public void setTextureActive()
    {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        //bindowanie nowej tekstury
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);

        // All RGB bytes are aligned to each other and each component is 1 byte
        //GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

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

    public static void clearTextures()
    {
        for(Integer index : textures)
        {
            GL11.glDeleteTextures(index);
        }
        System.out.println("Usunięto tekstury");

    }
}
