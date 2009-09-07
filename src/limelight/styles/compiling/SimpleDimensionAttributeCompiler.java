package limelight.styles.compiling;

import limelight.styles.abstrstyling.StyleAttribute;
import limelight.styles.abstrstyling.DimensionAttribute;

public class SimpleDimensionAttributeCompiler extends DimensionAttributeCompiler
{
  public StyleAttribute compile(Object objValue)
  {
    String value = objValue.toString();
    try
    {
      DimensionAttribute attribute;
      attribute = attemptPercentageAttribute(value);
      if(attribute == null)
        attribute = attemptStaticAttribute(value);

      if(attribute != null)
        return attribute;
      else
        throw makeError(value);
    }
    catch(Exception e)
    {
      throw makeError(value);
    }
  }
}
