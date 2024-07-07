package session;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
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
                String observationsSpanish=reg.getAsJsonPrimitive("observationsSpanish").getAsString();
                String observationsEnglish=reg.getAsJsonPrimitive("observationsEnglish").getAsString();
                String observationsFrench=reg.getAsJsonPrimitive("observationsFrench").getAsString();
                String observationsBasque=reg.getAsJsonPrimitive("observationsBasque").getAsString();
                String observationsCatalan=reg.getAsJsonPrimitive("observationsCatalan").getAsString();
                String observationsDutch=reg.getAsJsonPrimitive("observationsDutch").getAsString();
                String observationsGalician=reg.getAsJsonPrimitive("observationsGalician").getAsString();
                String observationsGerman=reg.getAsJsonPrimitive("observationsGerman").getAsString();
                String observationsItalian=reg.getAsJsonPrimitive("observationsItalian").getAsString();
                String observationsPortuguese=reg.getAsJsonPrimitive("observationsPortuguese").getAsString();
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
                String descriptionSpanish=reg.getAsJsonPrimitive("descriptionSpanish").getAsString();
                String descriptionEnglish=reg.getAsJsonPrimitive("descriptionEnglish").getAsString();
                String descriptionFrench=reg.getAsJsonPrimitive("descriptionFrench").getAsString();
                String descriptionBasque=reg.getAsJsonPrimitive("descriptionBasque").getAsString();
                String descriptionCatalan=reg.getAsJsonPrimitive("descriptionCatalan").getAsString();
                String descriptionDutch=reg.getAsJsonPrimitive("descriptionDutch").getAsString();
                String descriptionGalician=reg.getAsJsonPrimitive("descriptionGalician").getAsString();
                String descriptionGerman=reg.getAsJsonPrimitive("descriptionGerman").getAsString();
                String descriptionItalian=reg.getAsJsonPrimitive("descriptionItalian").getAsString();
                String descriptionPortuguese=reg.getAsJsonPrimitive("descriptionPortuguese").getAsString();
                int indicatorVersion=reg.getAsJsonPrimitive("indicatorVersion").getAsInt();
                String observationsSpanish=reg.getAsJsonPrimitive("observationsSpanish").getAsString();
                String observationsEnglish=reg.getAsJsonPrimitive("observationsEnglish").getAsString();
                String observationsFrench=reg.getAsJsonPrimitive("observationsFrench").getAsString();
                String observationsBasque=reg.getAsJsonPrimitive("observationsBasque").getAsString();
                String observationsCatalan=reg.getAsJsonPrimitive("observationsCatalan").getAsString();
                String observationsDutch=reg.getAsJsonPrimitive("observationsDutch").getAsString();
                String observationsGalician=reg.getAsJsonPrimitive("observationsGalician").getAsString();
                String observationsGerman=reg.getAsJsonPrimitive("observationsGerman").getAsString();
                String observationsItalian=reg.getAsJsonPrimitive("observationsItalian").getAsString();
                String observationsPortuguese=reg.getAsJsonPrimitive("observationsPortuguese").getAsString();
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
            String observationsSpanish=reg.getAsJsonPrimitive("observationsSpanish").getAsString();
            String observationsEnglish=reg.getAsJsonPrimitive("observationsEnglish").getAsString();
            String observationsFrench=reg.getAsJsonPrimitive("observationsFrench").getAsString();
            String observationsBasque=reg.getAsJsonPrimitive("observationsBasque").getAsString();
            String observationsCatalan=reg.getAsJsonPrimitive("observationsCatalan").getAsString();
            String observationsDutch=reg.getAsJsonPrimitive("observationsDutch").getAsString();
            String observationsGalician=reg.getAsJsonPrimitive("observationsGalician").getAsString();
            String observationsGerman=reg.getAsJsonPrimitive("observationsGerman").getAsString();
            String observationsItalian=reg.getAsJsonPrimitive("observationsItalian").getAsString();
            String observationsPortuguese=reg.getAsJsonPrimitive("observationsPortuguese").getAsString();
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
