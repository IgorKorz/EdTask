package dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

@Component
public class Dictionary {
  private final int keyLength;
  private final String keyRegex;
  private final String name;
  private Map<String, String> dictionary;
  private Result result;

  public Dictionary(Path file, int keyLength, String keyRegex, String name) throws IOException {
    if (keyLength <= 0) this.keyLength = 1;
    else this.keyLength = keyLength;

    dictionary = new LinkedHashMap<>();
    result = new Result(keyLength, keyRegex, dictionary);
    this.keyRegex = keyRegex;
    this.name = name;
    List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

    for (String line : lines) {
      String[] keyValue = line.split("=");
      dictionary.put(keyValue[0], keyValue[1]);
    }
  }

  public String getName() {
    return name;
  }

  public void writeToFile(Path file) throws IOException {
    try (BufferedWriter fileWriter = Files.newBufferedWriter(file)) {
      for (Map.Entry<String, String> entry : dictionary.entrySet()) {
        fileWriter.write(entry.getKey() + "=" + entry.getValue());
        fileWriter.newLine();
      }
    }
  }

  public Map<String, String> getDictionary() {
    return new LinkedHashMap<>(dictionary);
  }

  public String remove(String key) {
    String value = dictionary.remove(key);
    result.resultForRemove(key, value);

    return result.getResult();
  }

  public String getValue(String key) {
    String value = dictionary.get(key);
    result.resultForGetValue(key, value);

    return result.getResult();
  }

  public String put(String key, String value) {
    result.resultForPut(key, value);

    return result.getResult();
  }
}
