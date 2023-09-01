# -*- coding: utf-8 -*-
"""
Traductor simultáneo del español a varios idiomas (Para texto simple)

Autor: Pablo Ahíta del Barrio

Version: 1.0
"""
try:
    from googletrans import Translator, LANGUAGES
except ImportError:
    print("La biblioteca 'googletrans' no está instalada. Para instalarla introduzca en la consola: pip install googletrans==4.0.0-rc1")
    raise   



def translate_text(text, src_lang, target_lang):
    translator = Translator()
    translated = translator.translate(text, src=src_lang, dest=target_lang)
    return translated.text

if __name__ == "__main__":
    
    input_text = input("Ingrese el texto que desea traducir: ")

    
        
    languages=["en","eu","ca","nl","fr","gl","de","it","pt"]
    for lang in languages:
        translated_text=translate_text(input_text,"es",lang)
        print(LANGUAGES[lang],": ",translated_text)
    print("spanish : ",input_text)