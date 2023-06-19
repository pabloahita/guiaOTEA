namespace OTEAServer.Models
{
    public class Evidence
    {
        public Evidence(int idEvidence, int idIndicator, string indicatorType, string evidenceDescription, int evidenceValue) {
            this.IdEvidence = idEvidence;
            this.IdIndicator = idIndicator;
            this.IndicatorType = indicatorType;
            this.EvidenceDescription= evidenceDescription;
            this.EvidenceValue = evidenceValue;
        }

        public int IdEvidence { get; set; }
        public int IdIndicator { get; set; }
        public string IndicatorType { get; set; }
        public string EvidenceDescription { get; set; }
        public int EvidenceValue{ get; set; }
    }
}
