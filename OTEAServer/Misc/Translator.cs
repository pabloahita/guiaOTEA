using GTranslate.Translators;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
namespace OTEAServer.Misc
{
    /// <summary>
    /// Class controller for translation operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Translator")]
    public class Translator{


        private List<string> _languages;
        public Translator() { 
            _languages = new List<string> {"es","en","fr","eu","ca","nl","gl","de","it","pt"};
        }

        /// <summary>
        /// Method that translates a text from an origin language to a target language
        /// </summary>
        /// <param name="text">Text to translate</param>
        /// <param name="origin">Origin language</param>
        /// <returns>Translated text</returns>
        [HttpGet("translate")]
        public async Task<List<string>> translate([FromQuery] string text,[FromQuery] string origin){
            try
            {
                List<string> languages = _languages;
                languages.Remove(origin);
                List<string> translations = new List<string>();
                var translator = new GoogleTranslator();
                for (int i = 0; i < languages.Count; i++) {
                    var result = await translator.TranslateAsync(text, languages[i], origin);
                    translations.Add(result.Translation);
                }
                return translations;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

    }
}