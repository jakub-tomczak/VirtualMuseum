package Models;

import Camera.Camera;
import Light.*;
import Terrain.Terrain;
import Utils.MathUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ModelsRenderer {
    Camera mainCamera;
    List<Light>LightHandler = new ArrayList<Light>();


    public ModelsRenderer() {
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
        if (!modelsToRender.contains(modelToRender)) {
            modelsToRender.add(modelToRender);
        }
    }


    public void renderTerrain(Terrain terrain) {
        terrain.getTerrainModel().startUsingShader();
       // terrain.getTerrainModel().loadProjectionMatrix(getProjectionMatrix());
        bindAttribArrays(terrain.getTerrainModel().getVaoID());
        terrain.getTerrainModel().loadTexture();
        loadMatrices(terrain.getTerrainModel());
        terrain.getTerrainModel().loadLights(LightHandler);
        drawModel(terrain.getTerrainModel().getVertexCount());
        unbindAttribArrays();
        terrain.getTerrainModel().stopUsingShader();


    }

    public void renderModels() {
        //sprawdza jaki klawisz został wciśnięty
        //oblicza macierz widoku
        mainCamera.update();


        for (Model model : modelsToRender) {

            //wlacza shader danego modelu
            model.startUsingShader();
            bindAttribArrays(model.getVaoID());
            model.loadTexture();

            //ustawianie transformacji obiektu
            loadMatrices(model);

            model.loadLights(LightHandler);
            //model.modelTransformation.increasePosition();
            //model.modelTransformation.rotate();

            drawModel(model.getVertexCount());
            unbindAttribArrays();
            model.stopUsingShader();
        }
    }

    private void drawModel(int vertexCount) {
        GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);

    }


    private void loadMatrices(Model model) {
        model.loadTransformationMatrix();

        model.loadViewMatrix(mainCamera.getViewMatrix());
    }

    private void bindAttribArrays(int vaoID) {
        GL30.glBindVertexArray(vaoID);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);//włacza tablice z teksturami
        GL20.glEnableVertexAttribArray(2);//włacza tablice ze światłem
    }

    private void unbindAttribArrays() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void useCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    public void useLight(List<Light>lightArray) {
        this.LightHandler = lightArray;
    }
}

