import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

public class Dictionary {
  private final int keyLength;
  private final char lowerKeyBound;
  private final char upperKeyBound;
  private final String name;
  private Map<String, String> dictionary;
  private Result result;

  public Dictionary(Path file, int keyLength, char lowerKeyBound, char upperKeyBound, String name) throws IOException {
    if (keyLength <= 0) this.keyLength = 1;
    else this.keyLength = keyLength;

    if (lowerKeyBound > upperKeyBound) {
      this.lowerKeyBound = upperKeyBound;
      this.upperKeyBound = lowerKeyBound;
    } else {
      this.lowerKeyBound = lowerKeyBound;
      this.upperKeyBound = upperKeyBound;
    }

    dictionary = new LinkedHashMap<>();
    result = new Result();
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

  private class Result {
    private final String keyNotContainsMsg = "Key not contains!";
    private final String keyIsTooShortMsg = "Key is too short!";
    private final String keyIsTooLongMsg = "Key is too long!";
    private final String keyNotMatchMsg = "Key does not match the restrictions!";
    private String result;

    public String getResult() {
      return result;
    }

    public void resultForRemove(String key, String value) {
      if (isValidKey(key)) {
        if (!dictionary.containsKey(key)) result = keyNotContainsMsg;
        else result = key + "=" + value + " removed";
      }
    }

    public void resultForGetValue(String key, String value) {
      if (isValidKey(key)) {
        if (!dictionary.containsKey(key)) result = keyNotContainsMsg;
        else result = key + "=" + value;
      }
    }

    public void resultForPut(String key, String value) {
      if (isValidKey(key)) result = key + "=" + value + " put";
    }

    private boolean isValidKey(String key) {
      if (key.length() < keyLength) {
        result = keyIsTooShortMsg;

        return false;
      }

      if (key.length() > keyLength) {
        result = keyIsTooLongMsg;

        return false;
      }

      if (!key.toLowerCase().matches(String.format("[%c-%c]{%d}", lowerKeyBound, upperKeyBound, keyLength))) {
        result = keyNotMatchMsg;

        return false;
      }

      return true;
    }
  }
}
