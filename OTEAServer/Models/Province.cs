using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class Province
    {
        public Province(int idProvince, int idRegion, string idCountry, string nameSpanish, string nameEnglish, string nameFrench,string nameBasque, string nameCatalan, string nameDutch, string nameGalician, string nameGerman, string nameItalian, string namePortuguese)
        {
            this.idProvince = idProvince;
            this.idRegion = idRegion;
            this.idCountry = idCountry;
            this.nameSpanish = nameSpanish;
            this.nameEnglish = nameEnglish;
            this.nameFrench = nameFrench;
            this.nameBasque = nameBasque;
            this.nameCatalan = nameCatalan;
            this.nameDutch = nameDutch;
            this.nameGalician = nameGalician;
            this.nameGerman = nameGerman;
            this.nameItalian = nameItalian;
            this.namePortuguese = namePortuguese;
            
        }

        [JsonPropertyName("idProvince")]
        public int idProvince { get; set; }

        [JsonPropertyName("idRegion")]
        public int idRegion { get; set; }

        [JsonPropertyName("idCountry")]
        public string idCountry { get; set; }

        [JsonPropertyName("nameSpanish")]
        public string nameSpanish { get; set; }

        [JsonPropertyName("nameEnglish")]
        public string nameEnglish { get; set; }

        [JsonPropertyName("nameFrench")]
        public string nameFrench { get; set; }

        [JsonPropertyName("nameBasque")]
        public string nameBasque { get; set; }

        [JsonPropertyName("nameCatalan")]
        public string nameCatalan { get; set; }

        [JsonPropertyName("nameDutch")]
        public string nameDutch { get; set; }

        [JsonPropertyName("nameGalician")]
        public string nameGalician { get; set; }

        [JsonPropertyName("nameGerman")]
        public string nameGerman { get; set; }

        [JsonPropertyName("nameItalian")]
        public string nameItalian { get; set; }

        [JsonPropertyName("namePortuguese")]
        public string namePortuguese { get; set; }
    }
}
