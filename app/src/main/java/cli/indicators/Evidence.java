package cli.indicators;

public class Evidence {
    private String description;
    private float value;

    public Evidence(String description, float value){
        setDescription(description);
        setValue(value);
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
}
