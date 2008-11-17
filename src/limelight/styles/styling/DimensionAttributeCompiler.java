package limelight.styles.styling;

import limelight.styles.abstrstyling.StyleAttributeCompiler;
import limelight.styles.abstrstyling.StyleAttribute;
import limelight.styles.abstrstyling.DimensionAttribute;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DimensionAttributeCompiler extends StyleAttributeCompiler
{
  private static final Pattern percentagePattern = Pattern.compile("(\\d+)%");

  public StyleAttribute compile(Object objValue)
  {
    String value = objValue.toString();
    try
    {
      DimensionAttribute attribute;
      attribute = attemptAutoAttribute(value);
      if(attribute == null)
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

  private DimensionAttribute attemptStaticAttribute(String value)
  {
    int intValue;
    if(value.indexOf(".") != -1)
      intValue = (int)(Double.parseDouble(value) + 0.5);
    else
      intValue = Integer.parseInt(value);
    
    if(intValue >= 0)
      return new StaticDimensionAttribute(intValue);
    else
      return null;
  }

  private DimensionAttribute attemptAutoAttribute(String value)
  {
    if("auto".equals(value.toLowerCase()))
      return new AutoDimensionAttribute();
    else
      return null;
  }

  private DimensionAttribute attemptPercentageAttribute(String value)
  {
    Matcher matcher = percentagePattern.matcher(value);
    if(matcher.matches())
    {
      String percentStringValue = matcher.group(1);
      int percentValue = Integer.parseInt(percentStringValue);
      if(percentValue >= 0 && percentValue <= 100)
        return new PercentageDimensionAttribute(percentValue);
    }
    return null;
  }
}
