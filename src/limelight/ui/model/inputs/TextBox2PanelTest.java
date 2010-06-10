//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model.inputs;

import limelight.ui.MockGraphics;
import limelight.ui.api.MockProp;
import limelight.ui.model.PropPanel;
import limelight.ui.model.inputs.keyProcessors.*;
import limelight.ui.model.inputs.painters.TextPanelPropPainter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextBox2PanelTest extends Assert
{
  TextBox2Panel panel;
  PropPanel parent;
  MockGraphics graphics;
  TextModel boxInfo;

  @Before
  public void setUp()
  {
    panel = new TextBox2Panel();
    parent = new PropPanel(new MockProp());
    parent.add(panel);
    graphics = new MockGraphics();
    boxInfo = panel.getModelInfo();
    boxInfo.setText("Some Text");
  }

  @Test
  public void shouldDefaultStyles() throws Exception
  {
    assertEquals("150", panel.getStyle().getWidth());
    assertEquals("28", panel.getStyle().getHeight());
    assertEquals("4", panel.getStyle().getTopBorderWidth());
    assertEquals("4", panel.getStyle().getRightBorderWidth());
    assertEquals("4", panel.getStyle().getBottomBorderWidth());
    assertEquals("4", panel.getStyle().getLeftBorderWidth());
  }

  @Test
  public void shouldSetPainterOnParent() throws Exception
  {
    PropPanel newParent = new PropPanel(new MockProp());
    
    panel.setParent(newParent);

    assertEquals(TextPanelPropPainter.instance, newParent.getPainter());
  }

  @Test
  public void shouldKeyProcessorsWithNoSelection() throws Exception
  {
    assertEquals(NormalKeyProcessor.instance, panel.getKeyProcessorFor(0));
    assertEquals(ShiftKeyProcessor.instance, panel.getKeyProcessorFor(1));
    assertEquals(CmdKeyProcessor.instance, panel.getKeyProcessorFor(2));
    assertEquals(ShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(3));
    assertEquals(CmdKeyProcessor.instance, panel.getKeyProcessorFor(4));
    assertEquals(ShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(5));
    assertEquals(CmdKeyProcessor.instance, panel.getKeyProcessorFor(6));
    assertEquals(ShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(7));
    assertEquals(AltKeyProcessor.instance, panel.getKeyProcessorFor(8));
    assertEquals(AltShiftKeyProcessor.instance, panel.getKeyProcessorFor(9));
    assertEquals(AltCmdKeyProcessor.instance, panel.getKeyProcessorFor(10));
    assertEquals(AltShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(11));
    assertEquals(AltCmdKeyProcessor.instance, panel.getKeyProcessorFor(12));
    assertEquals(AltShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(13));
    assertEquals(AltCmdKeyProcessor.instance, panel.getKeyProcessorFor(14));
    assertEquals(AltShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(15));
  }                                     

  @Test
  public void shouldKeyProcessorsWithSelection() throws Exception
  {
    boxInfo.setSelectionOn(true);
    assertEquals(SelectionOnKeyProcessor.instance, panel.getKeyProcessorFor(0));
    assertEquals(SelectionOnShiftKeyProcessor.instance, panel.getKeyProcessorFor(1));
    assertEquals(SelectionOnCmdKeyProcessor.instance, panel.getKeyProcessorFor(2));
    assertEquals(SelectionOnShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(3));
    assertEquals(SelectionOnCmdKeyProcessor.instance, panel.getKeyProcessorFor(4));
    assertEquals(SelectionOnShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(5));
    assertEquals(SelectionOnCmdKeyProcessor.instance, panel.getKeyProcessorFor(6));
    assertEquals(SelectionOnShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(7));
    assertEquals(SelectionOnAltKeyProcessor.instance, panel.getKeyProcessorFor(8));
    assertEquals(SelectionOnAltShiftKeyProcessor.instance, panel.getKeyProcessorFor(9));
    assertEquals(SelectionOnAltCmdKeyProcessor.instance, panel.getKeyProcessorFor(10));
    assertEquals(SelectionOnAltShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(11));
    assertEquals(SelectionOnAltCmdKeyProcessor.instance, panel.getKeyProcessorFor(12));
    assertEquals(SelectionOnAltShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(13));
    assertEquals(SelectionOnAltCmdKeyProcessor.instance, panel.getKeyProcessorFor(14));
    assertEquals(SelectionOnAltShiftCmdKeyProcessor.instance, panel.getKeyProcessorFor(15));
  }
}
