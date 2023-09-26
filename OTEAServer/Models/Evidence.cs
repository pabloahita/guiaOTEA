using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class Evidence
    {
        public Evidence(int idEvidence, int idIndicator, string indicatorType, int idAmbit, string descriptionEnglish, string descriptionSpanish, string descriptionFrench, string descriptionBasque, string descriptionCatalan, string descriptionDutch, string descriptionGalician, string descriptionGerman, string descriptionItalian, string descriptionPortuguese, int evidenceValue, int indicatorVersion) {
            this.idEvidence = idEvidence;
            this.idIndicator = idIndicator;
            this.indicatorType = indicatorType;
            this.idAmbit = idAmbit;
            this.descriptionEnglish = descriptionEnglish;
            this.descriptionSpanish = descriptionSpanish;
            this.descriptionFrench = descriptionFrench;
            this.descriptionBasque = descriptionBasque;
            this.descriptionCatalan = descriptionCatalan;
            this.descriptionDutch = descriptionDutch;
            this.descriptionGalician = descriptionGalician;
            this.descriptionGerman = descriptionGerman;
            this.descriptionItalian = descriptionItalian;
            this.descriptionPortuguese = descriptionPortuguese;
            this.evidenceValue = evidenceValue;
            this.indicatorVersion = indicatorVersion;
        }


        [JsonPropertyName("idEvidence")]
        public int idEvidence { get; set; }

        [JsonPropertyName("idIndicator")]
        public int idIndicator { get; set; }

        [JsonPropertyName("indicatorType")]
        public string indicatorType { get; set; }

        [JsonPropertyName("idAmbit")]
        public int idAmbit { get; set; }

        [JsonPropertyName("descriptionEnglish")]
        public string descriptionEnglish { get; set; }

        [JsonPropertyName("descriptionSpanish")]
        public string descriptionSpanish { get; set; }

        [JsonPropertyName("descriptionFrench")]
        public string descriptionFrench { get; set; }

        [JsonPropertyName("descriptionBasque")]
        public string descriptionBasque { get; set; }

        [JsonPropertyName("descriptionCatalan")]
        public string descriptionCatalan { get; set; }

        [JsonPropertyName("descriptionDutch")]
        public string descriptionDutch { get; set; }

        [JsonPropertyName("descriptionGalician")]
        public string descriptionGalician { get; set; }

        [JsonPropertyName("descriptionGerman")]
        public string descriptionGerman { get; set; }

        [JsonPropertyName("descriptionItalian")]
        public string descriptionItalian { get; set; }

        [JsonPropertyName("descriptionPortuguese")]
        public string descriptionPortuguese { get; set; }

        [JsonPropertyName("evidenceValue")]
        public int evidenceValue{ get; set; }

        [JsonPropertyName("indicatorVersion")]
        public int indicatorVersion { get; set; }
    }
}
