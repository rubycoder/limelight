package limelight.ui.model.inputs;

import limelight.ui.EventAction;
import limelight.ui.events.CharTypedEvent;
import limelight.ui.events.FocusGainedEvent;
import limelight.ui.events.FocusLostEvent;
import limelight.ui.events.KeyPressedEvent;
import limelight.ui.model.InputTabbingAction;
import limelight.ui.model.MockRootPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class InputPanelTest
{
  private MockRootPanel root;
  private InputPanel panel;

  @Before
  public void setUp() throws Exception
  {
    panel = new TestableInputPanel();
    root = new MockRootPanel();
    root.add(panel);
  }
  
  @Test
  public void canBeFocused() throws Exception
  {
    assertEquals(0, root.dirtyRegions.size());

    panel.getEventHandler().dispatch(new FocusGainedEvent(panel));

    assertEquals(1, root.dirtyRegions.size());
    assertEquals(panel.getBoundingBox(), root.dirtyRegions.get(0));
  }
  
  @Test
  public void canBeUnfocused() throws Exception
  {
    panel.getEventHandler().dispatch(new FocusGainedEvent(panel));
    root.dirtyRegions.clear();
    panel.getEventHandler().dispatch(new FocusLostEvent(panel));

    assertEquals(1, root.dirtyRegions.size());
    assertEquals(panel.getBoundingBox(), root.dirtyRegions.get(0));
  }

  @Test
  public void hasTabbingActionWired() throws Exception
  {
    final List<EventAction> actions = panel.getEventHandler().getActions(KeyPressedEvent.class);

    assertEquals(1, actions.size());
    assertEquals(InputTabbingAction.instance, actions.get(0));
  }
}
