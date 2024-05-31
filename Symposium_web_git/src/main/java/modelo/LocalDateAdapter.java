package modelo;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Esta clase sirve para que gson pueda convertir los valores LocalDate a Json en las clases Dao.
 * @author ChatGPT, Alejandro Moreno
 * @version 1.1
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    /**
     * Este método convierte los valores de un obleto LocalDate a una cadena de texto, 
     * para ello recoge los valores de que se compone un objeto LocalDate y los recompone como una cadena de texto que pueda ser convertida a Json.
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        jsonWriter.value(localDate.format(formatter));
    }
    /**
     * Este método convierte una cadena de texto a un LocalDate,
     * para ello recoge la cadena de texto y la recompone como un objeto LocalDate.
     */
    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        return LocalDate.parse(jsonReader.nextString(), formatter);
    }
}