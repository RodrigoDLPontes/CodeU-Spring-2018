package codeu.service;

public class BBCodeParser {

  /**
   * Parses the input, substituting BBCode tags with their equivalent HTML tags
   * @param input The text to be parsed
   * @return The parsed text
   */
  public static String parse(String input) {
    // replace [b] with <b>
    input = input.replaceAll("\\[b\\]", "<b>"); // \\[b\\] is regex for [b]
    // replace [/b] with </b>
    input = input.replaceAll("\\[/b\\]", "</b>"); // \\[/b]\\ is regex for [/b]
    return input;
  }
}
