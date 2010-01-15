package limelight.ui.model.inputs.keyProcessors;

import limelight.ui.model.inputs.KeyProcessor;
import limelight.ui.model.inputs.TextModel;

import java.awt.event.KeyEvent;

public class KPCMD extends KeyProcessor
{
  public KPCMD(TextModel boxInfo)
  {
    super(boxInfo);
  }

  public void processKey(int keyCode)
  {
    switch (keyCode)
    {
      case KeyEvent.VK_A:
        selectAll();
        break;

      case KeyEvent.VK_V:
        boxInfo.pasteClipboard();
        break;
      case KeyEvent.VK_RIGHT:
        boxInfo.cursorIndex = boxInfo.text.length();
        break;
      case KeyEvent.VK_LEFT:
        boxInfo.cursorIndex = 0;
        break;


    }
  }
}