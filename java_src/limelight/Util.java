package limelight;

public class Util
{
  public static boolean equal(Object o1, Object o2)
  {
    if(o1 != null)
      return o1.equals(o2);
    else if(o2 != null)
      return o2.equals(o1);
    else
      return true;
  }
}
