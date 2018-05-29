package codeu.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BBCodeParserTest {

  @Test
  public void testParse_EmptyString_ReturnsEmptyString() {
    assertEquals("", BBCodeParser.parse(""));
  }

  @Test
  public void testParse_NoTags_ReturnsNoTags() {
    assertEquals("text", BBCodeParser.parse("text"));
  }

  @Test
  public void testParse_OneBBCodeTagPair_ReturnsOneHTMLTagPair() {
    assertEquals("<b>text</b>", BBCodeParser.parse("[b]text[/b]"));
  }

  @Test
  public void testParse_MultipleBBCodeTagPairsInParallel_ReturnsMultipleHTMLTagPairsInParallel() {
    assertEquals("<b>text</b><b>othertext</b><b>yetanothertext</b>",
        BBCodeParser.parse("[b]text[/b][b]othertext[/b][b]yetanothertext[/b]"));
  }

  @Test
  public void testParse_MultipleBBCodeTagPairsNested_MultipleHTMLTagPairsNested() {
    assertEquals("<b>leftleft<b>left<b>center</b>right</b>rightright</b>",
        BBCodeParser.parse("[b]leftleft[b]left[b]center[/b]right[/b]rightright[/b]"));
  }

  @Test
  public void testParse_MultipleBBCodeTagPairsInParallelAndNested_ReturnsMultipleHTMLTagPairsInParallelAndNested() {
    assertEquals("<b>leftleft1<b>left1<b>center1</b>right1</b>rightright1</b><b>leftleft2"
            + "<b>left2<b>center2</b>right2</b>rightright2</b>",
        BBCodeParser.parse("[b]leftleft1[b]left1[b]center1[/b]right1[/b]rightright1[/b][b]lef"
            + "tleft2[b]left2[b]center2[/b]right2[/b]rightright2[/b]"));
  }
}
