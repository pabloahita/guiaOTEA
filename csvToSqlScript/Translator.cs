using GTranslate.Translators;
namespace CsvToSql
{
    public class Translator{

        public async Task<string> translate(string text, string origin, string target){
            var translator=new GoogleTranslator();
            var result = await translator.TranslateAsync(text, target, origin);
            return result.Translation;
        }

        public async Task<List<string>> multiLangTranslate(string text, string origin){
            List<string> languages=new List<string>{"es","en","fr","eu","ca","nl","gl","de","it","pt"};
            List<string> result=new List<string>();
            for(int i=0;i<languages.Count;i++){
                Task<string> translationTask=translate(text,origin,languages[i]);
                string translationResult=await translationTask;
                result.Add(translationResult);
            }
            return result;
        }

    }
}