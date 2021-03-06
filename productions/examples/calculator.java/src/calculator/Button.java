package calculator;

import limelight.java.JavaProp;
import limelight.ui.events.panel.PanelEvent;
import limelight.util.Util;

public class Button
{
  public void press(PanelEvent event)
  {
    JavaProp prop = (JavaProp)event.getProp();
    JavaProp screen = prop.getScene().findProp("lcd");
    String text = prop.getText();
    if("c".equals(text))
      screen.setText("");
    else if("=".equals(text))
      screen.setText("Screw this!");
    else
      screen.setText(screen.getText() + text);
  }

  public void createButtons(PanelEvent event)
  {
    JavaProp buttons = (JavaProp)event.getProp();

    String labels = "1 2 3 + 4 5 6 - 7 8 9 * c 0 = /";
    for(String label : labels.split(" "))
      buttons.add(new JavaProp(Util.toMap("name", "calcButton", "text", label)));
  }
}
