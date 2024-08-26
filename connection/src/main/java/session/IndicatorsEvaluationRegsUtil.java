package session;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cli.indicators.Evidence;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationEvidenceReg;
import cli.indicators.IndicatorsEvaluationIndicatorReg;
import cli.indicators.IndicatorsEvaluationSimpleEvidenceReg;
import otea.connection.controller.IndicatorsEvaluationsController;

public class IndicatorsEvaluationRegsUtil {


    private static IndicatorsEvaluationRegsUtil instance;

    private List<IndicatorsEvaluationIndicatorReg> indicatorRegs;

    private List<IndicatorsEvaluationEvidenceReg> evidenceRegs;

    private List<IndicatorsEvaluationSimpleEvidenceReg> evidenceSimpleRegs;


    private IndicatorsEvaluationRegsUtil(IndicatorsEvaluation indicatorsEvaluation){
        getRegsByIndicatorsEvaluation(indicatorsEvaluation);
    }

    public static synchronized IndicatorsEvaluationRegsUtil getInstance(){
        return instance;
    }

    public static void createInstance(IndicatorsEvaluation indicatorsEvaluation){
        instance=new IndicatorsEvaluationRegsUtil(indicatorsEvaluation);
    }

    public static void removeInstance(){
        instance=null;
    }

    public List<IndicatorsEvaluationIndicatorReg> getIndicatorRegs() {
        return indicatorRegs;
    }

    public void setIndicatorRegs(List<IndicatorsEvaluationIndicatorReg> indicatorRegs) {
        this.indicatorRegs = indicatorRegs;
    }

    public List<IndicatorsEvaluationEvidenceReg> getEvidenceRegs() {
        return evidenceRegs;
    }

    public void setEvidenceRegs(List<IndicatorsEvaluationEvidenceReg> evidenceRegs) {
        this.evidenceRegs = evidenceRegs;
    }

    public List<IndicatorsEvaluationSimpleEvidenceReg> getEvidenceSimpleRegs() {
        return evidenceSimpleRegs;
    }

    public void setEvidenceSimpleRegs(List<IndicatorsEvaluationSimpleEvidenceReg> evidenceSimpleRegs) {
        this.evidenceSimpleRegs = evidenceSimpleRegs;
    }

    public void getRegsByIndicatorsEvaluation(IndicatorsEvaluation indicatorsEvaluation) {
        List<JsonObject> regs= IndicatorsEvaluationsController.GetRegsByIndicatorsEvaluation(indicatorsEvaluation);
        List<IndicatorsEvaluationIndicatorReg> indicatorsRegsList=new ArrayList<>();
        List<IndicatorsEvaluationEvidenceReg> evidencesRegsList=new ArrayList<>();
        List<IndicatorsEvaluationSimpleEvidenceReg> evidenceSimpleRegsList=new ArrayList<>();
        List<JsonObject> indicatorsRegs;
        JsonObject tams=regs.get(regs.size()-1);
        int numIndicatorsRegs=tams.getAsJsonPrimitive("numIndicatorsRegs").getAsInt();
        int numEvidencesRegs=tams.getAsJsonPrimitive("numEvidencesRegs").getAsInt();
        indicatorsRegs=regs.subList(0,numIndicatorsRegs);
        List<JsonObject> evidencesRegs=regs.subList(numIndicatorsRegs,numEvidencesRegs+numIndicatorsRegs);
        if(indicatorsEvaluation.getEvaluationType().equals("COMPLETE")){
            for(JsonObject reg: evidencesRegs){
                int idSubSubAmbit=reg.getAsJsonPrimitive("idSubSubAmbit").getAsInt();
                int idSubAmbit=reg.getAsJsonPrimitive("idSubAmbit").getAsInt();
                int idAmbit=reg.getAsJsonPrimitive("idAmbit").getAsInt();
                int idIndicator=reg.getAsJsonPrimitive("idIndicator").getAsInt();
                String indicatorType=reg.getAsJsonPrimitive("indicatorType").getAsString();
                int idEvidence=reg.getAsJsonPrimitive("idEvidence").getAsInt();
                int indicatorVersion=reg.getAsJsonPrimitive("indicatorVersion").getAsInt();
                int isMarked=reg.getAsJsonPrimitive("isMarked").getAsInt();
                String observations=reg.getAsJsonPrimitive("observations").getAsString();
                String observationsSpanish="";
                String observationsEnglish="";
                String observationsFrench="";
                String observationsBasque="";
                String observationsCatalan="";
                String observationsDutch="";
                String observationsGalician="";
                String observationsGerman="";
                String observationsItalian="";
                String observationsPortuguese="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    observationsSpanish=observations;
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    observationsFrench=observations;
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    observationsBasque=observations;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    observationsCatalan=observations;
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    observationsDutch=observations;
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    observationsGalician=observations;
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    observationsGerman=observations;
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    observationsItalian=observations;
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    observationsPortuguese=observations;
                }else{
                    observationsEnglish=observations;
                }
                
                evidencesRegsList.add(new IndicatorsEvaluationEvidenceReg(indicatorsEvaluation.getEvaluationDate(), indicatorsEvaluation.getIdEvaluatedOrganization(), indicatorsEvaluation.getOrgTypeEvaluated(), indicatorsEvaluation.getIdEvaluatorTeam(), indicatorsEvaluation.getIdEvaluatorOrganization(), indicatorsEvaluation.getOrgTypeEvaluator(), indicatorsEvaluation.getIllness(), indicatorsEvaluation.getIdCenter(), idEvidence, idIndicator, indicatorType, idSubSubAmbit, idSubAmbit, idAmbit, indicatorVersion, isMarked, indicatorsEvaluation.getEvaluationType(),observationsSpanish, observationsEnglish, observationsFrench, observationsBasque, observationsCatalan,
                        observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese));
            }

            setEvidenceRegs(evidencesRegsList);
        }
        else{
            for(JsonObject reg: evidencesRegs){
                int idSubSubAmbit=reg.getAsJsonPrimitive("idSubSubAmbit").getAsInt();
                int idSubAmbit=reg.getAsJsonPrimitive("idSubAmbit").getAsInt();
                int idAmbit=reg.getAsJsonPrimitive("idAmbit").getAsInt();
                int idIndicator=reg.getAsJsonPrimitive("idIndicator").getAsInt();
                int idEvidence=reg.getAsJsonPrimitive("idEvidence").getAsInt();
                String description=reg.getAsJsonPrimitive("description").getAsString();
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
                int indicatorVersion=reg.getAsJsonPrimitive("indicatorVersion").getAsInt();
                String observations=reg.getAsJsonPrimitive("observations").getAsString();
                String observationsSpanish="";
                String observationsEnglish="";
                String observationsFrench="";
                String observationsBasque="";
                String observationsCatalan="";
                String observationsDutch="";
                String observationsGalician="";
                String observationsGerman="";
                String observationsItalian="";
                String observationsPortuguese="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    descriptionSpanish=description;
                    observationsSpanish=observations;
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    descriptionFrench=description;
                    observationsFrench=observations;
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    descriptionBasque=description;
                    observationsBasque=observations;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    descriptionCatalan=description;
                    observationsCatalan=observations;
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    descriptionDutch=description;
                    observationsDutch=observations;
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    descriptionGalician=description;
                    observationsGalician=observations;
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    descriptionGerman=description;
                    observationsGerman=observations;
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    descriptionItalian=description;
                    observationsItalian=observations;
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    descriptionPortuguese=description;
                    observationsPortuguese=observations;
                }else{
                    descriptionEnglish=description;
                    observationsEnglish=description;
                }
                evidenceSimpleRegsList.add(new IndicatorsEvaluationSimpleEvidenceReg(indicatorsEvaluation.getEvaluationDate(), indicatorsEvaluation.getIdEvaluatedOrganization(), indicatorsEvaluation.getOrgTypeEvaluated(), indicatorsEvaluation.getIdEvaluatorTeam(), indicatorsEvaluation.getIdEvaluatorOrganization(), indicatorsEvaluation.getOrgTypeEvaluator(), indicatorsEvaluation.getIllness(), indicatorsEvaluation.getIdCenter(), idIndicator, idEvidence,
                descriptionSpanish, descriptionEnglish, descriptionFrench, descriptionBasque, descriptionCatalan,
                        descriptionDutch, descriptionGalician, descriptionGerman, descriptionItalian, descriptionPortuguese,
                idSubSubAmbit, idSubAmbit, idAmbit, indicatorVersion, indicatorsEvaluation.getEvaluationType(), observationsSpanish, observationsEnglish, observationsFrench, observationsBasque, observationsCatalan,
                        observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese));
            }
            setEvidenceSimpleRegs(evidenceSimpleRegsList);
        }

        for(JsonObject reg: indicatorsRegs){
            int idSubSubAmbit=reg.getAsJsonPrimitive("idSubSubAmbit").getAsInt();
            int idSubAmbit=reg.getAsJsonPrimitive("idSubAmbit").getAsInt();
            int idAmbit=reg.getAsJsonPrimitive("idAmbit").getAsInt();
            int idIndicator=reg.getAsJsonPrimitive("idIndicator").getAsInt();
            int indicatorVersion=reg.getAsJsonPrimitive("indicatorVersion").getAsInt();
            String observations=reg.getAsJsonPrimitive("observations").getAsString();
            String observationsSpanish="";
            String observationsEnglish="";
            String observationsFrench="";
            String observationsBasque="";
            String observationsCatalan="";
            String observationsDutch="";
            String observationsGalician="";
            String observationsGerman="";
            String observationsItalian="";
            String observationsPortuguese="";
            if(Locale.getDefault().getLanguage().equals("es")){
                observationsSpanish=observations;
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                observationsFrench=observations;
            }else if(Locale.getDefault().getLanguage().equals("eu")){
                observationsBasque=observations;
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                observationsCatalan=observations;
            }else if(Locale.getDefault().getLanguage().equals("nl")){
                observationsDutch=observations;
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                observationsGalician=observations;
            }else if(Locale.getDefault().getLanguage().equals("de")){
                observationsGerman=observations;
            }else if(Locale.getDefault().getLanguage().equals("it")){
                observationsItalian=observations;
            }else if(Locale.getDefault().getLanguage().equals("pt")){
                observationsPortuguese=observations;
            }else{
                observationsEnglish=observations;
            }
            int numEvidencesMarked=reg.getAsJsonPrimitive("numEvidencesMarked").getAsInt();
            String status=reg.getAsJsonPrimitive("status").getAsString();
            int requiresImprovementPlan=reg.getAsJsonPrimitive("requiresImprovementPlan").getAsInt();
            indicatorsRegsList.add(new IndicatorsEvaluationIndicatorReg(indicatorsEvaluation.getEvaluationDate(), indicatorsEvaluation.getIdEvaluatedOrganization(), indicatorsEvaluation.getOrgTypeEvaluated(), indicatorsEvaluation.getIdEvaluatorTeam(), indicatorsEvaluation.getIdEvaluatorOrganization(), indicatorsEvaluation.getOrgTypeEvaluator(), indicatorsEvaluation.getIllness(), indicatorsEvaluation.getIdCenter(), idIndicator, idSubSubAmbit, idSubAmbit, idAmbit, indicatorVersion, indicatorsEvaluation.getEvaluationType(), observationsSpanish, observationsEnglish, observationsFrench, observationsBasque, observationsCatalan,
                    observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese, numEvidencesMarked, status, requiresImprovementPlan));
        }

        setIndicatorRegs(indicatorsRegsList);

    }

    public List<IndicatorsEvaluationEvidenceReg> getEvidencesRegsByIndicator(int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, String indicatorType, int indicatorVersion, String evaluationType){
        Predicate<IndicatorsEvaluationEvidenceReg> evidenceOfThatIndicatorExist=new Predicate<IndicatorsEvaluationEvidenceReg>() {
            @Override
            public boolean test(IndicatorsEvaluationEvidenceReg reg) {
                return reg.getIdSubSubAmbit()==idSubSubAmbit && reg.getIdSubAmbit()==idSubAmbit &&
                        reg.getIdAmbit()==idAmbit && reg.getIdIndicator()==idIndicator &&
                        reg.getIndicatorType().equals(indicatorType) &&
                        reg.getIndicatorVersion()==indicatorVersion && reg.getEvaluationType().equals(evaluationType);
            }
        };
        return evidenceRegs.stream().filter(evidenceOfThatIndicatorExist).collect(Collectors.toList());
    }

    public List<IndicatorsEvaluationSimpleEvidenceReg> getSimpleEvidencesRegsByIndicator(int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int indicatorVersion, String evaluationType){
        Predicate<IndicatorsEvaluationSimpleEvidenceReg> evidenceOfThatIndicatorExist=new Predicate<IndicatorsEvaluationSimpleEvidenceReg>() {
            @Override
            public boolean test(IndicatorsEvaluationSimpleEvidenceReg reg) {
                return reg.getIdSubSubAmbit()==idSubSubAmbit && reg.getIdSubAmbit()==idSubAmbit &&
                        reg.getIdAmbit()==idAmbit && reg.getIdIndicator()==idIndicator &&
                        reg.getIndicatorVersion()==indicatorVersion && reg.getEvaluationType().equals(evaluationType);
            }
        };
        return evidenceSimpleRegs.stream().filter(evidenceOfThatIndicatorExist).collect(Collectors.toList());
    }


}
