import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Dictionary {
  protected Map<String, String> dictionary;

  public Dictionary(Path file) throws IOException {
    dictionary = new LinkedHashMap<>();
    List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

    for (String line : lines) {
      String[] keyValue = line.split("=");
      dictionary.put(keyValue[0], keyValue[1]);
    }
  }

  public void writeToFile(Path file) throws IOException {
    try (BufferedWriter fileWriter = Files.newBufferedWriter(file)) {
      for (Map.Entry<String, String> entry : dictionary.entrySet()) {
        fileWriter.write(entry.getKey() + "=" + entry.getValue());
        fileWriter.newLine();
      }
    }
  }

  @Override
  public String toString() {
    String result = "";

    for (Map.Entry<String, String> entry : dictionary.entrySet()) {
      result += entry.getKey() + "=" + entry.getValue() + "\n";
    }

    return result;
  }

  public abstract String remove(String key) throws DictionaryException;
  public abstract String getValue(String key) throws DictionaryException;
  public abstract String put(String key, String value) throws DictionaryException;
}
