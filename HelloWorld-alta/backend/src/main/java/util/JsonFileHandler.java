package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Usuario;

@Service
public class JsonFileHandler {

    private final String FILE_PATH = "usuarios.json";
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper(); 
    private final String DATA_FOLDER_PATH = "data";
    private final String FULL_PATH = DATA_FOLDER_PATH + File.separator + FILE_PATH;


    /**
     * Crear JSON si no existe
     * @throws IOException
     */
    public void crearFicheroJson() throws IOException {
        // Crear carpeta si no existe
        File carpeta = new File(DATA_FOLDER_PATH);

        if(!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Crear fichero (dentro de la carpeta) si no existe
        File fichero = new File(carpeta, FILE_PATH);

        if(!fichero.exists()) {
            fichero.createNewFile();
        }
    }

    /**
     * Guardar usuario en JSON
     * @param nuevoUsuario
     * @throws StreamWriteException
     * @throws DatabindException
     * @throws IOException
     */
    public void guardarUsuario(Usuario nuevoUsuario) throws StreamWriteException, DatabindException, IOException {
        File fichero = new File(FULL_PATH);
        ArrayList<Usuario> listaUsuarios;

        //Leer contenido del fichero JSON si existe
        if (fichero.exists() && fichero.length() > 0){
            listaUsuarios = OBJECT_MAPPER.readValue(fichero, new TypeReference<ArrayList<Usuario>>(){});
        } else {
            listaUsuarios = new ArrayList<>();
        }

        //Añadir nuevo usuario a ArrayList
        listaUsuarios.add(nuevoUsuario);

        //Escribir lista actualizada en JSON
        OBJECT_MAPPER.writeValue(fichero, listaUsuarios);
    }

    /**
     * Mostrar usuarios del JSON
     * @return
     * @throws IOException
     */
    public ArrayList<Usuario> mostrarUsuarios() throws IOException{
        File fichero = new File(FULL_PATH);
        ArrayList<Usuario> listaUsuarios;
        
        if (fichero.exists() && fichero.length() > 0){
            listaUsuarios = OBJECT_MAPPER.readValue(fichero, new TypeReference<ArrayList<Usuario>>(){});
        } else {
            listaUsuarios = new ArrayList<>();
        }
        return listaUsuarios;
    }
}
