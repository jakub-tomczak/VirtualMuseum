package Utils;

import Interfaces.IApplicationEvents;
import Models.Model;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class VBOManager implements IApplicationEvents {
    List<Integer> vboList = new ArrayList<>();
    List<Integer> vaoList = new ArrayList<>();

    private static VBOManager vaoManager;

    public static VBOManager getInstance()
    {
        if(vaoManager == null)
            vaoManager = new VBOManager();

        return vaoManager;
    }

    int vaoID;

    public int getVaoID()
    {
        return vaoID;
    }

    public VBOManager()
    {

        vaoID = 0;
        ApplicationEventsManager.getInstance().subscribeToApplicationEvents(this);
    }

    public void loadToVAO(Model model)
    {
        model.setVaoID(createVAO());
        bindIndicesBuffer(model.getData().getIndices());
        putDataToVBO(0, model.getData().getVertices());
        destroyVAO();
    }



    private void bindIndicesBuffer(int [] indices)
    {
        int vboID = GL15.glGenBuffers();
        vboList.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buff = putDataToIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buff, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer putDataToIntBuffer(int [] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private int createVAO()
    {
        int vaoID  = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        vaoList.add(vaoID);
        return vaoID;
    }

    private void putDataToVBO(int attributeSize, float [] data)
    {
        int vboID = GL15.glGenBuffers();
        vboList.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

        FloatBuffer buff = dataToFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buff, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeSize, 3, GL11.GL_FLOAT, false, 0, 0);

        //unbind current vbo
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void destroyVAO()
    {
        GL30.glBindVertexArray(0);
    }

    private FloatBuffer dataToFloatBuffer(float [] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data).
                flip();
        return buffer;
    }


    @Override
    public void onApplicationStarted() {
        return;
    }

    @Override
    public void onApplicationEnded() {
        cleanUPBuffers();
    }

    private void cleanUPBuffers() {
        for(int vao: vaoList)
        {
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo: vboList)
        {
            GL15.glDeleteBuffers(vbo);
        }

        System.out.println("bufory VAO i VBO zostały wyczyszczone pomyślnie.");
    }
}
