package cli.indicators;

import java.util.LinkedList;
import java.util.List;
public class Indicator {
    private String description;
    private List<Evidence> evidences;
    private float priority;

    public Indicator(String description,List<Evidence> evidences, float priority) {
        setDescription(description);
        setEvidences(evidences);
        setPriority(priority);
    }

    public void setEvidences(List<Evidence> evidences) {
        if(evidences==null){
            this.evidences=new LinkedList<Evidence>();
        }
        else{
            this.evidences=evidences;
        }
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public int getIndicatorValue(){
        int value=0;
        for(Evidence e : evidences){
            value+=e.getValue();
        }
        return value;
    }

    public void addEvidence(Evidence evidence){
        this.evidences.add(evidence);
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }
}
