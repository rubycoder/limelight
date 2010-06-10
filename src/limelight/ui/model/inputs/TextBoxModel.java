//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model.inputs;

import limelight.ui.TextLayoutImpl;
import limelight.ui.TypedLayout;
import limelight.ui.model.TextPanel;
import limelight.util.Box;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;


public class TextBoxModel extends TextModel
{

  public TextBoxModel(TextInputPanel myBox)
  {
    super(myBox);
  }

  protected int getXPosFromText(String toIndexString)
  {
    TypedLayout layout = new TextLayoutImpl(toIndexString, getFont(), TextPanel.getRenderContext());
    return getWidthDimension(layout) - xOffset;
  }

  public void shiftOffset(int index)
  {
    int xPos = getXPosFromIndex(index);
    if (index == 0)
    {
      setCursorX(PIXEL_WIDTH);
      xOffset = 0;
    }
    else if (isCriticallyLeft(xPos))
    {
      calculateRightShiftingOffset();
    }
    else if (isCriticallyRight(xPos))
    {
      calculateLeftShiftingOffset();
    }
    setCursorX(getXPosFromIndex(index));
  }

  public boolean isCursorAtCriticalEdge(int xPos)
  {
    if (myPanel.getWidth() > calculateTextDimensions().width)
      return false;
    if (!isCriticallyRight(xPos) && !isCriticallyLeft(xPos))
      return false;
    return true;
  }

  private boolean isCriticallyLeft(int xPos)
  {
    return (xPos <= PIXEL_WIDTH && xOffset != 0);
  }

  private boolean isCriticallyRight(int xPos)
  {
    return (xPos >= myPanel.getWidth() - PIXEL_WIDTH);
  }

  private void calculateRightShiftingOffset()
  {
    String rightShiftingText = getText().substring(0, getCursorIndex());
    TypedLayout layout = new TextLayoutImpl(rightShiftingText, getFont(), TextPanel.getRenderContext());
    int textWidth = getWidthDimension(layout) + getTerminatingSpaceWidth(rightShiftingText);
    if (textWidth > getPanelWidth() / 2)
      xOffset -= getPanelWidth() / 2;
    else
      xOffset -= textWidth;
    if (xOffset < 0)
      xOffset = 0;
  }

  public void calculateLeftShiftingOffset()
  {
    int defaultOffset = PIXEL_WIDTH;
    if (getCursorIndex() == getText().length())
    {
      int textWidth = calculateTextDimensions().width;
      if (textWidth > getPanelWidth())
      {
        xOffset = textWidth - getPanelWidth() + defaultOffset;
      }
    }
    else
    {
      String leftShiftingText;
      if (getCursorIndex() == getText().length() - 1)
        leftShiftingText = Character.toString(getText().charAt(getCursorIndex()));
      else
        leftShiftingText = getText().substring(getCursorIndex(), getText().length() - 1);
      TypedLayout layout = new TextLayoutImpl(leftShiftingText, getFont(), TextPanel.getRenderContext());
      int textWidth = getWidthDimension(layout) + getTerminatingSpaceWidth(leftShiftingText);
      if (textWidth > getPanelWidth() / 2)
        xOffset += getPanelWidth() / 2;
      else
        xOffset += textWidth;
    }
  }

  public Dimension calculateTextDimensions()
  {
    if (getText() != null && getText().length() > 0)
    {
      int height = 0;
      int width = 0;
      for (TypedLayout layout : getTextLayouts())
      {
        height += (int) (getHeightDimension(layout) + layout.getLeading() + .5);
        width += getWidthDimension(layout);
      }
      return new Dimension(width, height);
    }
    return null;
  }

  public ArrayList<TypedLayout> getTextLayouts()
  {
    if (getText() == null){
      initNewTextLayouts("");
      return textLayouts;
    }
    else
    {
      if (textLayouts == null || isThereSomeDifferentText())
      {
        setLastLayedOutText(getText());
        initNewTextLayouts(getText());
      }
      return textLayouts;
    }
  }

  private void initNewTextLayouts(String text)
  {
    textLayouts = new ArrayList<TypedLayout>();
    textLayouts.add(new TextLayoutImpl(text, getFont(), TextPanel.getRenderContext()));
  }

  public ArrayList<Rectangle> getSelectionRegions()
  {
    if (getText().length() > 0)
      shiftOffset(getCursorIndex());
    int x1 = getXPosFromIndex(getCursorIndex());
    int x2 = getXPosFromIndex(getSelectionIndex());
    int edgeSelectionExtension = 0;

    if (x1 <= 0 || x2 <= 0)
      edgeSelectionExtension = 0;
    ArrayList<Rectangle> regions = new ArrayList<Rectangle>();
    if (x1 > x2)
      regions.add(new Box(x2 - edgeSelectionExtension, 0, x1 - x2 + edgeSelectionExtension, getPanelHeight() * 2));
    else
      regions.add(new Box(x1 - edgeSelectionExtension, 0, x2 - x1 + edgeSelectionExtension, getPanelHeight() * 2));
    return regions;
  }

  public int calculateYOffset()
  {
    return 0;
  }

  public boolean isBoxFull()
  {
    if(getText().length() > 0)
      return (myPanel.getWidth() - (TextModel.PIXEL_WIDTH * 2) <= calculateTextDimensions().width);
    return false;
  }

  public boolean isMoveUpEvent(int keyCode)
  {
    return false;
  }

  public boolean isMoveDownEvent(int keyCode)
  {
    return false;
  }

  public int getTopOfStartPositionForCursor()
  {
    int textHeight = getHeightOfCurrentLine();
    return getVerticalAlignment().getY(textHeight, myPanel.getBoundingBox());
  }

  public int getBottomPositionForCursor()
  {
    TypedLayout layout = getTextLayouts().get(0);
    return getTopOfStartPositionForCursor() + (int)(getHeightDimension(layout) + 0.5);
  }

  public int getIndexOfLastCharInLine(int line)
  {
    return getText().length();
  }

  public void lostOwnership(Clipboard clipboard, Transferable contents)
  {
    //this doesn't have to do anything...
  }
}
