package cli.indicators;

import com.google.gson.annotations.SerializedName;

public class Evidence {

    @SerializedName("idEvidence")
    private int idEvidence;
    @SerializedName("idIndicator")
    private int idIndicator;
    @SerializedName("indicatorType")
    private String indicatorType;
    @SerializedName("evidenceDescription")
    private String description;
    @SerializedName("evidenceValue")
    private int value;
    private boolean isMarked;
    public Evidence(int idEvidence, int idIndicator, String indicatorType, String description, int value){
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

    public void setValue(int value) {
        this.value=value;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public int getValue(){
        return value;
    }

    public void setMarked(boolean isMarked){this.isMarked=isMarked;}

    public boolean getStatus(){return isMarked;}
}
