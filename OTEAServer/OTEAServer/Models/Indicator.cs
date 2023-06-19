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

        public int IdIndicator { get; set; }
        public string IndicatorType { get; set; }
        public string IndicatorDescription { get; set; }
        public int IndicatorPriority { get; set; }
    }
}
