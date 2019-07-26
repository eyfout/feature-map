package ht.eyfout.struct.data;

import java.util.Arrays;

public class Elements {

  static <T> T[] expandArray(T[] arr, Integer index) {
    if (arr.length <= index) {
      int newLength = arr.length * 2;
      newLength = (index >= newLength) ? index + 1 : newLength;
      if (newLength == 0) {
        newLength = index + 1;
      }
      arr = Arrays.copyOf(arr, newLength);
    }
    return arr;
  }
}
