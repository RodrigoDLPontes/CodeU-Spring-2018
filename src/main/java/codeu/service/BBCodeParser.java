package codeu.service;

public class BBCodeParser {

  public static String parse(String input) {
    StringBuilder builder = new StringBuilder(input);

    // replace [b] with <b>
    int i = builder.indexOf("[b]");
    while (i != -1) {
      builder.replace(i, i + "[b]".length(), "<b>");
      i = builder.indexOf("[b]", i);
    }

    // replace [/b] with </b>
    i = builder.indexOf("[/b]");
    while (i != -1) {
      builder.replace(i, i + "[/b]".length(), "</b>");
      i = builder.indexOf("[/b]", i);
    }

    return builder.toString();
  }
}
