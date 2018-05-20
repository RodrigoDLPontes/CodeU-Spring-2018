package codeu.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BBCodeParserTest {

  @Test
  public void testParse_Empty() {
    String input = "";
    String expected = "";
    String actual = BBCodeParser.parse(input);

    assertEquals(expected, actual);
  }

  @Test
  public void testParse_NoTags() {
    String input = "text";
    String expected = "text";
    String actual = BBCodeParser.parse(input);

    assertEquals(expected, actual);
  }

  @Test
  public void testParse_OneTagPair() {
    String input = "[b]text[/b]";
    String expected = "<b>text</b>";
    String actual = BBCodeParser.parse(input);

    assertEquals(expected, actual);
  }

  @Test
  public void testParse_MultipleTagPairsParallel() {
    String input = "[b]text[/b][b]othertext[/b][b]yetanothertext[/b]";
    String expected = "<b>text</b><b>othertext</b><b>yetanothertext</b>";
    String actual = BBCodeParser.parse(input);

    assertEquals(expected, actual);
  }

  @Test
  public void testParse_MultipleTagPairsNested() {
    String input = "[b]leftleft[b]left[b]center[/b]right[/b]rightright[/b]";
    String expected = "<b>leftleft<b>left<b>center</b>right</b>rightright</b>";
    String actual = BBCodeParser.parse(input);

    assertEquals(expected, actual);
  }

  @Test
  public void testParse_MultipleTagPairsParallelAndNested() {
    String input = "[b]leftleft1[b]left1[b]center1[/b]right1[/b]rightright1[/b][b]leftleft2[b]left2"
        + "[b]center2[/b]right2[/b]rightright2[/b]";
    String expected = "<b>leftleft1<b>left1<b>center1</b>right1</b>rightright1</b><b>leftleft2<b>le"
        + "ft2<b>center2</b>right2</b>rightright2</b>";
    String actual = BBCodeParser.parse(input);

    assertEquals(expected, actual);
  }
}
