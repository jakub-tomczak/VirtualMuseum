package Shaders;

import Utils.Constants;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    private String vertexShaderFileName;
    private String fragmentShaderFileName;

    public ShaderProgram(String vertexShaderFileName, String fragmentShaderFileName) {

        this.vertexShaderFileName = vertexShaderFileName;
        this.fragmentShaderFileName = fragmentShaderFileName;

        vertexShaderID = loadShader(vertexShaderFileName, GL20.GL_VERTEX_SHADER);

        fragmentShaderID = loadShader(fragmentShaderFileName, GL20.GL_FRAGMENT_SHADER);


        programID = GL20.glCreateProgram();
        //attaching vertex
        GL20.glAttachShader(programID, vertexShaderID);
        //and fragment shaders
        GL20.glAttachShader(programID, fragmentShaderID);

        //binding vao attributes
        bindAttributes();

        //linking
        GL20.glLinkProgram(programID);

        GL20.glValidateProgram(programID);


        getAlUniformLocations();
    }

    protected abstract void getAlUniformLocations();

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    public void startUsingShader() {
        GL20.glUseProgram(programID);
    }

    public void stopUsingShader() {
        GL20.glUseProgram(0);
    }

    public void cleanUP() {
        GL20.glUseProgram(0);

        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);

        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);

        System.out.println("Shadery " + vertexShaderFileName + " " + fragmentShaderFileName + " zostały wyczyszczone.");
    }


    //bind attributes to vao
    protected abstract void bindAttributes();


    //binduje jeden atrybut do vao
    protected void bindAttribute(int attribute, String attributeName) {
        GL20.glBindAttribLocation(programID, attribute, attributeName);
    }

    private static int loadShader(String fileName, int type) {
        StringBuilder shaderData = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.format("%s/%s.txt", Constants.SHADERS_PATH, fileName)));


            //put data from shaders files to a string
            String line;
            while ((line = reader.readLine()) != null) {
                shaderData.append(line).append("\n");
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //kompilacja shadera
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderData);
        GL20.glCompileShader(shaderID);

        //niepowodzenie kompilacji shadera
        int shaderCompilationStatus = GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS);
        if (shaderCompilationStatus == GL11.GL_FALSE) {
            System.err.println("Błąd przy kompilacji shadera");
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 300));
            System.exit(1);
        } else {
            System.out.println("Kompilacja " + fileName + " się powiodła.");
        }

        return shaderID;


    }



    //metody do ustawiania uniform variables

    /*
    Uniform variables are used to communicate with your vertex or fragment shader from "outside".
    Uniform variables are read-only and have the same value among all processed vertices. You can only change them within your program.
    */
    //ustawia zmienna typu float
    protected void loadFloat(int location, float value)
    {
        GL20.glUniform1f(location, value);
    }

    protected void loadVec3f(int location, Vector3f vector)
    {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    //ładowanie bool polega na załadowaniu float
    protected void loadBool(int location, boolean value)
    {
        GL20.glUniform1f(location, value ? 1f : 0f);
    }


    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    //ładuje macierz 4x4 złożoną z floatów
    //potrzeba float buffera do tego
    protected void loadMat4f(int location, Matrix4f matrix)
    {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();    //zmiana bufora na odczyt
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }
}
