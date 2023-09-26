using System;
using System.IO;
namespace CsvToSql
{
    class Program
    {
        static async Task Main(string[] args)
        {

            string inputFile="/home/pablo/Escritorio/Division territorial OTEA/paises.csv";
            string outputFile="/home/pablo/StudioProjects/tfg2223/scripts/paises.sql";

            Translator translator=new Translator();

            string trans=await translator.translate("Hola","es","en");

            Console.WriteLine(trans);
            /*try{
                string[] content=File.ReadAllText(inputFile).Split("\n");
                for(int i=1;i<content.Length;i++){
                    string[] fila=content[i].Split(",");
                    string nombrePaisEspanol=fila[0];
                    List<string> traducciones=await translator.multiLangTranslate(nombrePaisEspanol,"es");
                    string line="INSERT INTO countries VALUES("+nombrePaisEspanol+","+traducciones[1]+","+traducciones[2]+","+traducciones[3]+","+traducciones[4]+","+traducciones[5]+","+traducciones[6]+","+traducciones[7]+","+traducciones[8]+","+traducciones[9]+","+fila[4]+");";
                    Console.WriteLine(line);
                }
                //Console.WriteLine(content);

            }catch (FileNotFoundException)
            {
                Console.WriteLine("El archivo no se encontró en la ruta especificada.");
            }
            catch (IOException ex)
            {
                Console.WriteLine($"Error de lectura del archivo: {ex.Message}");
            }catch (HttpRequestException ex) when (ex.Message.Contains("429"))
            {
                // Espera un tiempo antes de volver a intentar la solicitud
                await Task.Delay(TimeSpan.FromSeconds(5)); // Puedes ajustar el tiempo de espera
                // Luego, puedes intentar nuevamente la traducción
            }*/
            
        }
    }
}