package limelight.styles.attributes;

import limelight.styles.StyleAttribute;
import limelight.styles.abstrstyling.StyleValue;
import limelight.ui.model.ChangeablePanel;

public class BottomLeftBorderWidthAttribute extends StyleAttribute
{
  public BottomLeftBorderWidthAttribute()
  {
    super("Bottom Left Border Width", "pixels", "0");
  }

  @Override
  public void applyChange(ChangeablePanel panel, StyleValue value)
  {
    handleBorderChange(panel);
  }
}
