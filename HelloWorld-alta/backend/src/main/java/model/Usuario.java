package model;

/**
 * Clase para representar un usuario con atributos básicos
 */
public class Usuario {
    private String nombre;
    private String username;
    private String email;
    private String password;



    /**
     * Constructor de clase Usuario
     * @param nombre
     * @param usuario
     * @param email
     * @param password
     */
    public Usuario(String nombre, String username, String email, String password) {
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor vacío
     */
    public Usuario() {
    }

    /**
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     */
    public void setPassword (String password) {
        this.password = password;
    }

}
