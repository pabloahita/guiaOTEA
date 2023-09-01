using GTranslate.Translators;
using Microsoft.AspNetCore.Mvc;
namespace OTEAServer.Misc
{
    [ApiController]
    [Route("Translator")]
    public class Translator{

        [HttpGet("translate")]
        public async Task<string> translate([FromQuery] string text,[FromQuery] string origin, [FromQuery] string target){
            var translator=new GoogleTranslator();
            var result = await translator.TranslateAsync(text, target, origin);
            return result.Translation;
        }

        [HttpGet("multiLangTranslate")]
        public async Task<List<string>> multiLangTranslate([FromQuery] string text, [FromQuery] string origin){
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