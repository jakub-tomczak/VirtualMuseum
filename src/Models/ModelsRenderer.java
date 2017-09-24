package Models;

import Camera.Camera;
import Utils.MathUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class ModelsRenderer {
    Camera mainCamera = new Camera();
    public ModelsRenderer()
    {
        projectionMatrix = MathUtils.createProjectionMatrix();
    }

    private Matrix4f projectionMatrix;

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

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
        //sprawdza jaki klawisz został wciśnięty
        //oblicza macierz widoku
        mainCamera.update();

        for (Model model : modelsToRender) {

            //wlacza shader danego modelu
            model.startUsingShader();


            GL30.glBindVertexArray(model.getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);//włacza tablice z teksturami
            model.loadTexture();

            //ustawianie transformacji obiektu
            model.loadTransformationMatrix();

            model.loadViewMatrix(mainCamera.getViewMatrix());

            //model.modelTransformation.increasePosition();
            model.modelTransformation.rotate();

            //GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
            GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);


            model.stopUsingShader();
        }
    }

    public void useCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }
}
