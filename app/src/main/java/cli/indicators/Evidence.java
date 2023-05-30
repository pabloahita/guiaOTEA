package cli.indicators;

public class Evidence {

    private int idEvidence;
    private int idIndicator;
    private String indicatorType;

    private String description;

    private float value;

    private boolean isMarked;
    public Evidence(int idEvidence, int idIndicator, String indicatorType, String description, float value){
        setIdEvidence(idEvidence);
        setIdIndicator(idIndicator);
        setIndicatorType(indicatorType);
        setDescription(description);
        setValue(value);
        setMarked(false);
    }

    public int getIdEvidence() {
        return idEvidence;
    }

    public void setIdEvidence(int idEvidence) {
        this.idEvidence = idEvidence;
    }

    public int getIdIndicator() {
        return idIndicator;
    }

    public void setIdIndicator(int idIndicator) {
        this.idIndicator = idIndicator;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public void setValue(float value) {
        this.value=value;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public float getValue(){
        return value;
    }

    public void setMarked(boolean isMarked){this.isMarked=isMarked;}

    public boolean getStatus(){return isMarked;}
}
