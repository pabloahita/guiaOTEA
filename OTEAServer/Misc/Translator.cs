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

        /// <summary>
        /// Method that translates a text from an origin language to a target language
        /// </summary>
        /// <param name="text">Text to translate</param>
        /// <param name="origin">Origin language</param>
        /// <param name="target">Target language</param>
        /// <returns>Translated text</returns>
        [HttpGet("translate")]
        public async Task<string> translate([FromQuery] string text,[FromQuery] string origin, [FromQuery] string target){
            try
            {
                var translator = new GoogleTranslator();
                var result = await translator.TranslateAsync(text, target, origin);
                return result.Translation;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

    }
}