package dictionary;

import java.util.Map;

public class Result {
        private final String keyNotContainsMsg = "Key not contains!";
        private final String  keyIsTooShortMsg = "Key is too short!";
        private final String keyIsTooLongMsg = "Key is too long!";
        private final String keyNotMatchMsg = "Key does not match the restrictions!";
        private String result;
        private String keyRegex;
        private int keyLength;
        private Map<String, String> dictionary;

        public Result(int keyLength, String keyRegex, Map<String, String> dictionary) {
            this.keyLength = keyLength;
            this.keyRegex = keyRegex;
            this.dictionary = dictionary;
        }

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

            if (!key.matches(keyRegex)) {
                result = keyNotMatchMsg;

                return false;
            }

            return true;
        }
    }