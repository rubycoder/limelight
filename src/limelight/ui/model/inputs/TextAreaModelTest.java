package limelight.ui.model.inputs;

import limelight.ui.TextLayoutImpl;
import limelight.ui.TypedLayout;
import limelight.ui.model.TextPanel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextAreaModelTest
{
  TextModel modelInfo;
  TextAreaModel areaInfo;
  TextInputPanel panel;

  @Before
  public void setUp()
  {
    panel = new TextArea2Panel();
    modelInfo = panel.getModelInfo();
    areaInfo = new TextAreaModel(panel);
    modelInfo.setText("I took the one less traveled by. And that has made all the difference");
  }

  @Test
  public void canCalculateTheXPositionForTheCursorFromAString()
  {
    int width = modelInfo.getWidthDimension(new TextLayoutImpl("ABC", modelInfo.font, TextPanel.getRenderContext()));
    int expectedX = width + modelInfo.SIDE_TEXT_MARGIN;

    int x = modelInfo.getXPosFromText("ABC");

    assertEquals(expectedX, x);
  }

  @Test
  public void canCalculateTheXPositionFromTheCursorIndex()
  {
    int width = modelInfo.getWidthDimension(new TextLayoutImpl("I took the one less", modelInfo.font, TextPanel.getRenderContext()));
    int width2 = modelInfo.getWidthDimension(new TextLayoutImpl("by. And that has made", modelInfo.font, TextPanel.getRenderContext()));

    int x = modelInfo.getXPosFromIndex(0);
    int x2 = modelInfo.getXPosFromIndex(19);
    int x3 = modelInfo.getXPosFromIndex(50);

    assertEquals(modelInfo.SIDE_TEXT_MARGIN, x);
    assertEquals(width + modelInfo.SIDE_TEXT_MARGIN, x2);
    assertEquals(width2 + modelInfo.SIDE_TEXT_MARGIN, x3);
  }

    @Test
  public void canCalculateTheYPositionForTheCursorViaAnIndex()
  {
    int expectedYForOneLine = TextModel.TOP_MARGIN;
    int expectedYForTwoLines = 18;
    int expectedYForThreeLines = expectedYForTwoLines * 2 - TextModel.TOP_MARGIN;

    int y1 = modelInfo.getYPosFromIndex(0);
    int y2 = modelInfo.getYPosFromIndex(50);
    int y3 = modelInfo.getYPosFromIndex(65);

    assertEquals(expectedYForOneLine, y1);
    assertEquals(expectedYForTwoLines, y2);
    assertEquals(expectedYForThreeLines, y3);
  }

  @Test
  public void canGetTheHeightOfTheCurrentLine()
  {
    int expectedHeight = (int) (modelInfo.getHeightDimension(new TextLayoutImpl("A", modelInfo.font, TextPanel.getRenderContext())) + .5);
    modelInfo.setCursorIndex(50);

    int height = modelInfo.getHeightOfCurrentLine();

    assertEquals(expectedHeight, height);
  }

  @Test
  public void shouldBeAbleToFindNewLineIndices()
  {
    ArrayList<Integer> indices = areaInfo.findNewLineIndices("this\nIs\nA new \rline");

    assertEquals(3, indices.size());
    assertEquals(4, (int)indices.get(0));
    assertEquals(7, (int)indices.get(1));
    assertEquals(14, (int)indices.get(2));
  }

  @Test
  public void canSpiltTextIntoMultipleLinesBasedOffReturnKeys()
  {
    ArrayList<TypedLayout> textLayouts = areaInfo.parseTextForMultipleLayouts("this is the first line\nthis is the second line");

    assertEquals(2, textLayouts.size());
    assertEquals("this is the first line\n", textLayouts.get(0).getText());
    assertEquals("this is the second line", textLayouts.get(1).getText());
  }

  @Test
  public void canSplitTextIntoMultipleLinesFromThePanelWidth()
  {
   ArrayList<TypedLayout> textLayouts = areaInfo.parseTextForMultipleLayouts("This here is the first full line. This is the second line");

    assertEquals(3, textLayouts.size());
    assertEquals("This here is the first full ", textLayouts.get(0).getText());
    assertEquals("line. This is the second ", textLayouts.get(1).getText());
  }

  @Test
  public void willStoreATextLayoutForEachLine()
  {
    areaInfo.setText("This is more than 1 line\rand should be 2 lines");

    ArrayList<TypedLayout> textLayouts = areaInfo.getTextLayouts();
    assertEquals(2, textLayouts.size());
    assertEquals("This is more than 1 line\r", textLayouts.get(0).getText());
    assertEquals("and should be 2 lines", textLayouts.get(1).getText());
  }

  @Test
  public void canCalculateTheTextModelsDimensions()
  {
    modelInfo.setText("line 1\nline 2");
    Dimension dim = modelInfo.calculateTextDimensions();
    assertEquals(29, dim.width);
    assertEquals(28, dim.height);
  }

  @Test
  public void canTellIfTheTextPanelIsFull()
  {
    modelInfo.setText("line\nline\nline\nline\nline\nline\nline\nline\n");
    assertTrue(modelInfo.isBoxFull());
  }


}
