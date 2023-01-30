package cli.indicators;

import java.util.LinkedList;
import java.util.List;
public class Indicator {
    private String description;
    private List<Evidence> evidences;

    public Indicator(String description,List<Evidence> evidences) {
        setDescription(description);
        setEvidences(evidences);
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

}
