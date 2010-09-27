package limelight.ui.events.panel;

import limelight.ui.MockPanel;
import limelight.ui.Panel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.Assert.assertEquals;

public class MouseEnteredEventTest
{
  private Panel panel;
  private MouseEnteredEvent event;

  @Before
  public void setUp() throws Exception
  {
    panel = new MockPanel();
    event = new MouseEnteredEvent(0, new Point(0, 0), 0);
  }
  
  @Test
  public void isNotInheritable() throws Exception
  {
    assertEquals(false, event.isInheritable());
  }
}