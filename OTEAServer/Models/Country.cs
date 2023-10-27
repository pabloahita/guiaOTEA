using System.Text.Json.Serialization;

namespace OTEAServer.Models
{
    public class Country
    {
        public Country(string idCountry, string nameSpanish, string nameEnglish, string nameFrench,string nameBasque, string nameCatalan, string nameDutch, string nameGalician, string nameGerman, string nameItalian, string namePortuguese, string? phone_code, string? flag) {
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
            this.idCountry = idCountry;
            this.phone_code=phone_code;
            this.flag=flag;
        }
        
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

        [JsonPropertyName("phone_code")]
        public string? phone_code { get; set; }

        [JsonPropertyName("flag")]
        public string? flag { get; set; }

    }
}
