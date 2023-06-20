using Newtonsoft.Json;

namespace OTEAServer.Models
{
    public class Indicator
    {
        public Indicator(int idIndicator, string indicatorType, string indicatorDescription, int indicatorPriority) {
            this.IdIndicator = idIndicator;
            this.IndicatorType = indicatorType;
            this.IndicatorDescription = indicatorDescription;
            this.IndicatorPriority = indicatorPriority;
        }


        [JsonProperty("indicatorId")]
        public int IdIndicator { get; set; }

        [JsonProperty("indicatorType")]
        public string IndicatorType { get; set; }

        [JsonProperty("indicatorDescription")]
        public string IndicatorDescription { get; set; }

        [JsonProperty("indicatorPriority")]
        public int IndicatorPriority { get; set; }
    }
}
