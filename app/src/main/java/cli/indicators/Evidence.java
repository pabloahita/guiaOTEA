package cli.indicators;

/**
 * <p>EN: Class that represents an evidence that belongs to an indicator. Every evidence is composed by a description and an float value.</p>
 * <p>ES: Clase que representa una evidencia que pertenece a un indicador. Cada evidencia se compone de una descripción y de un valor en coma flotante.</p>
 *
 * @author Pablo Ahita del Barrio
 * @version 1.0
 */
public class Evidence {

    /**<p>EN: Evidence's description</p>
     * <p>ES: Descripcion de la evidencia</p>*/
    private String description;

    /**<p>EN: Evidence's float value</p>
     * <p>ES: Valor en coma flotante de la evidencia</p>*/
    private float value;

    /**
     * <p>EN: Class Evidence's constructor</p>
     * <p>ES: Constructor de la clase Evidence</p>
     *
     * @param description <p>EN: Evidence's description</p> <p>ES: Descripcion de la evidencia</p>
     * @param value       <p>EN: Evidence's value</p> <p>ES: Valor de la evidencia</p>
     */
    public Evidence(String description, float value){
        setDescription(description);
        setValue(value);
    }

    /**
     * <p>EN: Method that changes the evidence's value to the one passed by parameter.</p>
     * <p>ES: Metodo que cambia el valor de la evidencia por otro el cual es pasado por parametro</p>
     *
     * @param value <p>EN: The new evidence's value</p> <p>ES: El nuevo valor de la evidencia</p>
     */
    public void setValue(float value) {
        this.value=value;
    }

    /**
     * <p>EN: Method that changes the evidence's description to the one passed by parameter.</p>
     * <p>ES: Metodo que cambia la descripcion de la evidencia por otra la cual es pasada por parametro</p>
     *
     * @param description <p>EN: The new evidence's description</p> <p>ES: La nueva descripcion de la evidencia</p>
     */
    public void setDescription(String description) {
        this.description=description;
    }

    /**
     * <p>EN: Method that obtains the evidence's description</p>
     * <p>ES: Metodo que obtiene la descripcion de la evidencia</p>
     *
     * @return <p>EN: Evidence's description</p><p>ES: Descripcion de la evidencia</p>
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>EN: Method that obtains the evidence's value</p>
     * <p>ES: Metodo que obtiene el valor de la evidencia</p>
     *
     * @return <p>EN: Evidence's description</p><p>ES: Descripcion de la evidencia</p>
     */
    public float getValue(){
        return value;
    }
}
