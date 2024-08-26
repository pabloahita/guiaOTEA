package session;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
                String description=ind.getAsJsonPrimitive("description").getAsString();
                String descriptionSpanish="";
                String descriptionEnglish="";
                String descriptionFrench="";
                String descriptionBasque="";
                String descriptionCatalan="";
                String descriptionDutch="";
                String descriptionGalician="";
                String descriptionGerman="";
                String descriptionItalian="";
                String descriptionPortuguese="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    descriptionSpanish=description;
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    descriptionFrench=description;
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    descriptionBasque=description;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    descriptionCatalan=description;
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    descriptionDutch=description;
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    descriptionGalician=description;
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    descriptionGerman=description;
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    descriptionItalian=description;
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    descriptionPortuguese=description;
                }else{
                    descriptionEnglish=description;
                }
                indicators.add(new Indicator(ind.getAsJsonPrimitive("idIndicator").getAsInt(), ind.getAsJsonPrimitive("indicatorType").getAsString()
                        , ind.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), ind.getAsJsonPrimitive("idSubAmbit").getAsInt(), ind.getAsJsonPrimitive("idAmbit").getAsInt(),
                        descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese, ind.getAsJsonPrimitive("indicatorPriority").getAsString(),
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
                    String description=ev.getAsJsonPrimitive("description").getAsString();
                    String descriptionSpanish="";
                    String descriptionEnglish="";
                    String descriptionFrench="";
                    String descriptionBasque="";
                    String descriptionCatalan="";
                    String descriptionDutch="";
                    String descriptionGalician="";
                    String descriptionGerman="";
                    String descriptionItalian="";
                    String descriptionPortuguese="";
                    if(Locale.getDefault().getLanguage().equals("es")){
                        descriptionSpanish=description;
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        descriptionFrench=description;
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        descriptionBasque=description;
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        descriptionCatalan=description;
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        descriptionDutch=description;
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        descriptionGalician=description;
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        descriptionGerman=description;
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        descriptionItalian=description;
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        descriptionPortuguese=description;
                    }else{
                        descriptionEnglish=description;
                    }
                    evidences.add(new Evidence(ev.getAsJsonPrimitive("idEvidence").getAsInt(),ev.getAsJsonPrimitive("idIndicator").getAsInt(), ev.getAsJsonPrimitive("indicatorType").getAsString(),
                            ev.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), ev.getAsJsonPrimitive("idSubAmbit").getAsInt(), ev.getAsJsonPrimitive("idAmbit").getAsInt(),
                            descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese,
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
                String description=amb.getAsJsonPrimitive("description").getAsString();
                String descriptionSpanish="";
                String descriptionEnglish="";
                String descriptionFrench="";
                String descriptionBasque="";
                String descriptionCatalan="";
                String descriptionDutch="";
                String descriptionGalician="";
                String descriptionGerman="";
                String descriptionItalian="";
                String descriptionPortuguese="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    descriptionSpanish=description;
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    descriptionFrench=description;
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    descriptionBasque=description;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    descriptionCatalan=description;
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    descriptionDutch=description;
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    descriptionGalician=description;
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    descriptionGerman=description;
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    descriptionItalian=description;
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    descriptionPortuguese=description;
                }else{
                    descriptionEnglish=description;
                }
                ambits.add(new Ambit(amb.getAsJsonPrimitive("idAmbit").getAsInt(), descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese));
            }

            for(JsonObject subAmb:subAmbitsJson){
                String description=subAmb.getAsJsonPrimitive("description").getAsString();
                String descriptionSpanish="";
                String descriptionEnglish="";
                String descriptionFrench="";
                String descriptionBasque="";
                String descriptionCatalan="";
                String descriptionDutch="";
                String descriptionGalician="";
                String descriptionGerman="";
                String descriptionItalian="";
                String descriptionPortuguese="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    descriptionSpanish=description;
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    descriptionFrench=description;
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    descriptionBasque=description;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    descriptionCatalan=description;
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    descriptionDutch=description;
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    descriptionGalician=description;
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    descriptionGerman=description;
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    descriptionItalian=description;
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    descriptionPortuguese=description;
                }else{
                    descriptionEnglish=description;
                }
                subAmbits.add(new SubAmbit(subAmb.getAsJsonPrimitive("idSubAmbit").getAsInt(), subAmb.getAsJsonPrimitive("idAmbit").getAsInt(), descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese));
            }

            for(JsonObject subSubAmb:subSubAmbitsJson){
                String description=subSubAmb.getAsJsonPrimitive("description").getAsString();
                String descriptionSpanish="";
                String descriptionEnglish="";
                String descriptionFrench="";
                String descriptionBasque="";
                String descriptionCatalan="";
                String descriptionDutch="";
                String descriptionGalician="";
                String descriptionGerman="";
                String descriptionItalian="";
                String descriptionPortuguese="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    descriptionSpanish=description;
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    descriptionFrench=description;
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    descriptionBasque=description;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    descriptionCatalan=description;
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    descriptionDutch=description;
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    descriptionGalician=description;
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    descriptionGerman=description;
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    descriptionItalian=description;
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    descriptionPortuguese=description;
                }else{
                    descriptionEnglish=description;
                }
                subSubAmbits.add(new SubSubAmbit(subSubAmb.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), subSubAmb.getAsJsonPrimitive("idSubAmbit").getAsInt(), subSubAmb.getAsJsonPrimitive("idAmbit").getAsInt(), descriptionEnglish,descriptionSpanish,descriptionFrench,descriptionBasque,descriptionCatalan,descriptionDutch,descriptionGalician,descriptionGerman,descriptionItalian,descriptionPortuguese));
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
