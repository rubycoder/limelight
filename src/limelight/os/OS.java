//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.os;

import limelight.Context;
import limelight.io.TempDirectory;

import java.io.IOException;

public abstract class OS
{
  protected boolean inKioskMode;
  protected RuntimeExecution runtime = new RuntimeExecution();

  protected abstract void turnOnKioskMode();
  protected abstract void turnOffKioskMode();
  protected abstract void launch(String URL) throws IOException;
  public abstract void configureSystemProperties();

  public String dataRoot()
  {
    return Context.fs().absolutePath(new TempDirectory().getRoot());
  }

  public void enterKioskMode()
  {
    if(inKioskMode)
      return;
    turnOnKioskMode();
    inKioskMode = true;
  }

  public void exitKioskMode()
  {
    inKioskMode = false;
    turnOffKioskMode();
  }

  public boolean isInKioskMode()
  {
    return inKioskMode;
  }

  public void appIsStarting()
  {
  }

  public void openProduction(String productionPath)
  {
    Context.instance().studio.open(productionPath);
  }

  public void setRuntime(RuntimeExecution runtime)
  {
    this.runtime = runtime;
  }
}
