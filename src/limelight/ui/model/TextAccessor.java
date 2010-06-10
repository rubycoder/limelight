//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model;

public interface TextAccessor
{
  void setText(PropablePanel panel, String text);
  String getText();
  void markAsDirty();
  void markAsNeedingLayout();
  boolean hasFocus();
}
