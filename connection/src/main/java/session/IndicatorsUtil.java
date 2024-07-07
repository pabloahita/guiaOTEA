package session;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cli.indicators.Ambit;
import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.SubAmbit;
import cli.indicators.SubSubAmbit;
import otea.connection.controller.IndicatorsController;

public class IndicatorsUtil {

    private static IndicatorsUtil instance;

    private List<Indicator> indicators;

    private List<Ambit> ambits;

    private List<Evidence> evidences;

    private List<SubAmbit> subAmbits;

    private List<SubSubAmbit> subSubAmbits;

    private IndicatorsUtil(String evaluationType){
        obtainIndicatorsFromDataBase(evaluationType);
    }

    public static synchronized IndicatorsUtil getInstance(){
        return instance;
    }

    public static void createInstance(String evaluationType){
        instance=new IndicatorsUtil(evaluationType);
    }

    public static void removeInstance(){
        instance=null;
    }

    public void obtainIndicatorsFromDataBase(String evaluationType){
        if(indicators==null || !indicators.get(0).getEvaluationType().equals(evaluationType)) {
            ambits=new ArrayList<>();
            subAmbits=new ArrayList<>();
            subSubAmbits=new ArrayList<>();
            indicators=new ArrayList<>();
            evidences=new ArrayList<>();
            List<JsonObject> allData= IndicatorsController.GetAll(evaluationType);
            JsonObject tams=allData.get(allData.size()-1);
            int numIndicators=tams.getAsJsonPrimitive("numIndicators").getAsInt();
            int numAmbits=tams.getAsJsonPrimitive("numAmbits").getAsInt();
            int numSubAmbits=tams.getAsJsonPrimitive("numSubAmbits").getAsInt();
            int numSubSubAmbits=tams.getAsJsonPrimitive("numSubSubAmbits").getAsInt();
            List<JsonObject> indicatorsJson=allData.subList(0,numIndicators);
            for(JsonObject ind:indicatorsJson){
                indicators.add(new Indicator(ind.getAsJsonPrimitive("idIndicator").getAsInt(), ind.getAsJsonPrimitive("indicatorType").getAsString()
                        , ind.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), ind.getAsJsonPrimitive("idSubAmbit").getAsInt(), ind.getAsJsonPrimitive("idAmbit").getAsInt(),
                        ind.getAsJsonPrimitive("descriptionEnglish").getAsString(), ind.getAsJsonPrimitive("descriptionSpanish").getAsString(), ind.getAsJsonPrimitive("descriptionFrench").getAsString(),
                        ind.getAsJsonPrimitive("descriptionBasque").getAsString(), ind.getAsJsonPrimitive("descriptionCatalan").getAsString(), ind.getAsJsonPrimitive("descriptionDutch").getAsString(),
                        ind.getAsJsonPrimitive("descriptionGalician").getAsString(), ind.getAsJsonPrimitive("descriptionGerman").getAsString(),
                        ind.getAsJsonPrimitive("descriptionItalian").getAsString(), ind.getAsJsonPrimitive("descriptionPortuguese").getAsString(), ind.getAsJsonPrimitive("indicatorPriority").getAsString(),
                        ind.getAsJsonPrimitive("indicatorVersion").getAsInt(), ind.getAsJsonPrimitive("isActive").getAsInt(), evaluationType));
            }
            List<JsonObject> evidencesJson;
            List<JsonObject> ambitsJson;
            List<JsonObject> subAmbitsJson;
            List<JsonObject> subSubAmbitsJson;
            if(indicators.get(0).getEvaluationType().equals("COMPLETE")){
                int numEvidences=tams.getAsJsonPrimitive("numEvidences").getAsInt();
                evidencesJson=allData.subList(numIndicators,numIndicators+numEvidences);
                for(JsonObject ev:evidencesJson){
                    evidences.add(new Evidence(ev.getAsJsonPrimitive("idEvidence").getAsInt(),ev.getAsJsonPrimitive("idIndicator").getAsInt(), ev.getAsJsonPrimitive("indicatorType").getAsString(),
                            ev.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), ev.getAsJsonPrimitive("idSubAmbit").getAsInt(), ev.getAsJsonPrimitive("idAmbit").getAsInt(),
                            ev.getAsJsonPrimitive("descriptionEnglish").getAsString(), ev.getAsJsonPrimitive("descriptionSpanish").getAsString(), ev.getAsJsonPrimitive("descriptionFrench").getAsString(),
                            ev.getAsJsonPrimitive("descriptionBasque").getAsString(), ev.getAsJsonPrimitive("descriptionCatalan").getAsString(), ev.getAsJsonPrimitive("descriptionDutch").getAsString(),
                            ev.getAsJsonPrimitive("descriptionGalician").getAsString(), ev.getAsJsonPrimitive("descriptionGerman").getAsString(),
                            ev.getAsJsonPrimitive("descriptionItalian").getAsString(), ev.getAsJsonPrimitive("descriptionPortuguese").getAsString(),
                            ev.getAsJsonPrimitive("evidenceValue").getAsInt(), ev.getAsJsonPrimitive("indicatorVersion").getAsInt(), evaluationType));
                }
                ambitsJson=allData.subList(numIndicators+numEvidences,numIndicators+numEvidences+numAmbits);
                subAmbitsJson=allData.subList(numIndicators+numEvidences+numAmbits,numIndicators+numEvidences+numAmbits+numSubAmbits);
                subSubAmbitsJson=allData.subList(numIndicators+numEvidences+numAmbits+numSubAmbits,numIndicators+numEvidences+numAmbits+numSubAmbits+numSubSubAmbits);

            }
            else{
                ambitsJson=allData.subList(numIndicators,numIndicators+numAmbits);
                subAmbitsJson=allData.subList(numIndicators+numAmbits,numIndicators+numAmbits+numSubAmbits);
                subSubAmbitsJson=allData.subList(numIndicators+numAmbits+numSubAmbits,numIndicators+numAmbits+numSubAmbits+numSubSubAmbits);
            }




            for(JsonObject amb:ambitsJson){
                ambits.add(new Ambit(amb.getAsJsonPrimitive("idAmbit").getAsInt(), amb.getAsJsonPrimitive("descriptionEnglish").getAsString(), amb.getAsJsonPrimitive("descriptionSpanish").getAsString(), amb.getAsJsonPrimitive("descriptionFrench").getAsString(),
                        amb.getAsJsonPrimitive("descriptionBasque").getAsString(), amb.getAsJsonPrimitive("descriptionCatalan").getAsString(), amb.getAsJsonPrimitive("descriptionDutch").getAsString(),
                        amb.getAsJsonPrimitive("descriptionGalician").getAsString(), amb.getAsJsonPrimitive("descriptionGerman").getAsString(),
                        amb.getAsJsonPrimitive("descriptionItalian").getAsString(), amb.getAsJsonPrimitive("descriptionPortuguese").getAsString()));
            }

            for(JsonObject subAmb:subAmbitsJson){
                subAmbits.add(new SubAmbit(subAmb.getAsJsonPrimitive("idSubAmbit").getAsInt(), subAmb.getAsJsonPrimitive("idAmbit").getAsInt(), subAmb.getAsJsonPrimitive("descriptionEnglish").getAsString(), subAmb.getAsJsonPrimitive("descriptionSpanish").getAsString(), subAmb.getAsJsonPrimitive("descriptionFrench").getAsString(),
                        subAmb.getAsJsonPrimitive("descriptionBasque").getAsString(), subAmb.getAsJsonPrimitive("descriptionCatalan").getAsString(), subAmb.getAsJsonPrimitive("descriptionDutch").getAsString(),
                        subAmb.getAsJsonPrimitive("descriptionGalician").getAsString(), subAmb.getAsJsonPrimitive("descriptionGerman").getAsString(),
                        subAmb.getAsJsonPrimitive("descriptionItalian").getAsString(), subAmb.getAsJsonPrimitive("descriptionPortuguese").getAsString()));
            }

            for(JsonObject subSubAmb:subSubAmbitsJson){
                subSubAmbits.add(new SubSubAmbit(subSubAmb.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), subSubAmb.getAsJsonPrimitive("idSubAmbit").getAsInt(), subSubAmb.getAsJsonPrimitive("idAmbit").getAsInt(), subSubAmb.getAsJsonPrimitive("descriptionEnglish").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionSpanish").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionFrench").getAsString(),
                        subSubAmb.getAsJsonPrimitive("descriptionBasque").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionCatalan").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionDutch").getAsString(),
                        subSubAmb.getAsJsonPrimitive("descriptionGalician").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionGerman").getAsString(),
                        subSubAmb.getAsJsonPrimitive("descriptionItalian").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionPortuguese").getAsString()));
            }
        }
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public List<Ambit> getAmbits() {
        return ambits;
    }

    public void setAmbits(List<Ambit> ambits) {
        this.ambits = ambits;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public List<SubAmbit> getSubAmbits() {
        return subAmbits;
    }

    public void setSubAmbits(List<SubAmbit> subAmbits) {
        this.subAmbits = subAmbits;
    }

    public List<SubSubAmbit> getSubSubAmbits() {
        return subSubAmbits;
    }

    public void setSubSubAmbits(List<SubSubAmbit> subSubAmbits) {
        this.subSubAmbits = subSubAmbits;
    }

    public Ambit getAmbit(int idAmbit){
        Predicate<Ambit> ambitExist=new Predicate<Ambit>() {
            @Override
            public boolean test(Ambit ambit) {
                return ambit.getIdAmbit()==idAmbit;
            }
        };
        return ambits.stream().filter(ambitExist).collect(Collectors.toList()).get(0);
    }

    public SubAmbit getSubAmbit(int idSubAmbit, int idAmbit){
        Predicate<SubAmbit> subAmbitExist=new Predicate<SubAmbit>() {
            @Override
            public boolean test(SubAmbit subAmbit) {
                return subAmbit.getIdSubAmbit()==idSubAmbit && subAmbit.getIdAmbit()==idAmbit;
            }
        };
        return subAmbits.stream().filter(subAmbitExist).collect(Collectors.toList()).get(0);
    }

    public SubSubAmbit getSubSubAmbit(int idSubSubAmbit, int idSubAmbit, int idAmbit){
        Predicate<SubSubAmbit> subSubAmbitExist=new Predicate<SubSubAmbit>() {
            @Override
            public boolean test(SubSubAmbit subSubAmbit) {
                return subSubAmbit.getIdSubSubAmbit()==idSubSubAmbit &&
                        subSubAmbit.getIdSubAmbit()==idSubAmbit && subSubAmbit.getIdAmbit()==idAmbit;
            }
        };
        return subSubAmbits.stream().filter(subSubAmbitExist).collect(Collectors.toList()).get(0);
    }

    public List<Evidence> getEvidencesByIndicator(int idSubSubAmbit,int idSubAmbit, int idAmbit, int idIndicator, String indicatorType, int indicatorVersion, String evaluationType){
        Predicate<Evidence> evidenceOfThatIndicatorExist=new Predicate<Evidence>() {
            @Override
            public boolean test(Evidence evidence) {
                return evidence.getIdSubSubAmbit()==idSubSubAmbit && evidence.getIdSubAmbit()==idSubAmbit &&
                        evidence.getIdAmbit()==idAmbit && evidence.getIdIndicator()==idIndicator &&
                        evidence.getIndicatorType().equals(indicatorType) &&
                        evidence.getIndicatorVersion()==indicatorVersion && evidence.getEvaluationType().equals(evaluationType);
            }
        };
        return evidences.stream().filter(evidenceOfThatIndicatorExist).collect(Collectors.toList());
    }


}
