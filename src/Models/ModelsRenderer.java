package Models;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class ModelsRenderer {

    private List<Model> modelsToRender = new ArrayList<>();

    public List<Model> getModelsToRender() {
        return modelsToRender;
    }

    public void addModelsToRender(Model modelToRender) {
        if(!modelsToRender.contains(modelToRender))
        {
            modelsToRender.add(modelToRender);
        }
    }


    public void renderModels() {
        for (Model model : modelsToRender) {


            GL30.glBindVertexArray(model.getVaoID());
            GL20.glEnableVertexAttribArray(0);
            //GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
            GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        }
    }

}
