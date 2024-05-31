package modelo;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Esta clase sirve para que gson pueda convertir los valores de un objeto LocalDateTime a Json en las clases Dao.
 * @author ChatGPT, Alejandro Moreno
 * @version 1.1
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    /**
     * Este método convierte los valores de un obleto LocalDateTime a una cadena de texto, 
     * para ello recoge los valores de que se compone un objeto LocalDateTime y los recompone como una cadena de texto que pueda ser convertida a Json.
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        jsonWriter.value(localDateTime.format(formatter));
    }
    /**
     * Este método convierte una cadena de texto a un LocalDateTime,
     * para ello recoge la cadena de texto y la recompone como un objeto LocalDateTime.
     */
    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        return LocalDateTime.parse(jsonReader.nextString(), formatter);
    }
}
