package cli.indicators;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>EN: Class that represents an indicator that is used to evaluate an organization. Every indicator is composed by a description, a list of four evidences and a priority represented by a float value.</p>
 * <p>ES: Clase que representa un indicador que es utilizado para evaluar una organizacion. Cada indicador se compone de una descripción, una lista de cuatro evidencias y una prioridad representada mediante un valor en coma flotante.</p>
 *
 * @author Pablo Ahita del Barrio
 * @version 1.0
 */
public class Indicator {
    /**<p>EN: Indicator's description</p>
     * <p>ES: Descripcion del indicador</p>*/
    private String description;

    /**<p>EN: Indicator's list of evidences</p>
     * <p>ES: Lista de evidencias del indicador</p>*/
    private List<Evidence> evidences;

    /**<p>EN: Indicator's priority</p>
     * <p>ES: Descripcion de la evidencia</p>*/
    private float priority;

    /**
     * <p>EN: Class Indicator's constructor</p>
     * <p>ES: Constructor de la clase Indicators</p>
     *
     * @param description <p>EN: Indicator's description</p> <p>ES: Descripcion del indicador</p>
     * @param evidences   <p>EN: Indicator's list of evidences</p> <p>ES: Lista de evidencias del indicador</p>
     * @param priority    <p>EN: Indicator's priority</p> <p>ES: Prioridad del indicador</p>
     */
    public Indicator(String description,List<Evidence> evidences, float priority) {
        setDescription(description);
        setEvidences(evidences);
        setPriority(priority);
    }

    /**
     * <p>EN: Method that changes the indicator's list of evidences to the one passed by parameter.</p>
     * <p>ES: Metodo que cambia la lista de evidencias del indicador por otro el cual es pasado por parametro</p>
     *
     * @param evidences <p>EN: The new list of evidences</p> <p>ES: La nueva lista de evidencias</p>
     */
    public void setEvidences(List<Evidence> evidences) {
        if(evidences==null){
            this.evidences=new LinkedList<Evidence>();
        }
        else{
            this.evidences=evidences;
        }
    }

    /***/
    public List<Evidence> getEvidences() {
        return evidences;
    }

    /**
     * <p>EN: Method that changes the indicator's description to the one passed by parameter.</p>
     * <p>ES: Metodo que cambia la descripcion del indicador por otro la cual es pasada por parametro</p>
     *
     * @param description <p>EN: The new indicator's description</p> <p>ES: La nueva descripcion del indicador</p>
     */
    public void setDescription(String description) {
        this.description=description;
    }

    /***/
    public String getDescription() {
        return description;
    }

    /**
     * <p>EN: Method that obtains the indicator's value using the sum of every evidence's value</p>
     * <p>ES: Metodo que obtiene el valor del indicador a partir de la suma de los valores de sus evidencias</p>
     *
     * @return <p>EN: Indicator's value</p><p>ES: Valor del indicador</p>
     */
    public int getIndicatorValue(){
        int value=0;
        for(Evidence e : evidences){
            value+=e.getValue();
        }
        return value;
    }

    /**
     * <p>EN: Method that adds an evidence to the list of evidences</p>
     * <p>ES: Metodo que anade una evidencia a la lista de evidencias</p>
     *
     * @param evidence <p>EN: Evidence that is added to the list of evidences</p><p>ES: Evidencia que es añadida a la lista de evidencias</p>
     */
    public void addEvidence(Evidence evidence){
        this.evidences.add(evidence);
    }

    /**
     * <p>EN: Method that obtains the indicator's priority</p>
     * <p>ES: Metodo que obtiene la prioridad del indicador</p>
     *
     * @return <p>EN: Indicator's priority</p><p>ES: Prioridad del indicador</p>
     */
    public float getPriority() {
        return priority;
    }

    /**
     * <p>EN: Method that changes the indicator's priority</p>
     * <p>ES: Metodo que obtiene la prioridad del indicador</p>
     *
     * @param priority the priority
     */
    public void setPriority(float priority) {
        this.priority = priority;
    }

    public int getMultiplicator(int num_evidences_filled){
        if(num_evidences_filled==0||num_evidences_filled==1){return 0;}
        else if (num_evidences_filled==2||num_evidences_filled==3){
            if (this.priority==1){return 1;}
            else if (this.priority==2){return 2;}
            else if (this.priority==3){return 3;}
            else{return 4;}
        }
        else{
            if (this.priority==1){return 2;}
            else if (this.priority==2){return 3;}
            else if (this.priority==3){return 4;}
            else{return 5;}
        }
    }

}
