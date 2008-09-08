package limelight.ui.model;

import limelight.LimelightError;
import limelight.ui.Panel;
import limelight.ui.model.updates.Updates;
import limelight.util.Box;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public abstract class BasePanel implements Panel
{
  protected int height;
  protected int width;
  private int x;
  private int y;
  private Panel parent;
  protected Point absoluteLocation;
  protected Box absoluteBounds;
  protected LinkedList<Panel> children;
  private boolean sterilized;
  protected Box boundingBox;
  protected Update neededUpdate;

  protected BasePanel()
  {
    width = 50;
    height = 50;
    children = new LinkedList<Panel>();
  }

  public int getHeight()
  {
    return height;
  }

  public int getWidth()
  {
    return width;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public void setSize(int w, int h)
  {
    w = h == 0 ? 0 : w;
    h = w == 0 ? 0 : h;
    if(w != getWidth() || h != getHeight())
    {
      clearCache();
      width = w;
      height = h;
    }
  }

  public void clearCache()
  {
    absoluteLocation = null;
    boundingBox = null;
    absoluteBounds = null;
    for(Panel child : children)
      child.clearCache();
  }

  public void setLocation(int x, int y)
  {
    if(x != this.x || y != this.y)
      clearCache();
    this.x = x;
    this.y = y;
  }

  public Point getLocation()
  {
    return new Point(x, y);
  }

  public Point getAbsoluteLocation()
  {
    if(absoluteLocation == null)
    {
      int x = this.x;
      int y = this.y;

      Panel p = parent;
      while(p != null)
      {
        x += p.getX();
        y += p.getY();
        p = p.getParent();
      }
      absoluteLocation = new Point(x, y);
    }
    return absoluteLocation;
  }

  public Box getAbsoluteBounds()
  {
    if(absoluteBounds == null)
    {
      Point absoluteLocation = getAbsoluteLocation();
      absoluteBounds = new Box(absoluteLocation.x, absoluteLocation.y, getWidth(), getHeight());
    }
    return absoluteBounds;
  }

  public Panel getParent()
  {
    return parent;
  }

  public void setParent(Panel panel)
  {
    parent = panel;
  }

  public boolean containsRelativePoint(Point point)
  {
    return point.x >= x &&
        point.x < x + width &&
        point.y >= y &&
        point.y < y + height;
  }

  public boolean containsAbsolutePoint(Point point)
  {
    Point absoluteLocation = getAbsoluteLocation();
    return point.x >= absoluteLocation.x &&
        point.x < absoluteLocation.x + width &&
        point.y >= absoluteLocation.y &&
        point.y < absoluteLocation.y + height;
  }

  //TODO  MDM Change my return type to RootPanel
  public RootPanel getRoot()
  {
    if(parent == null)
      return null;
    return parent.getRoot();
  }

  public boolean isAncestor(Panel panel)
  {
    if(parent == null)
      return false;
    else if(parent == panel)
      return true;
    else
      return parent.isAncestor(panel);
  }

  public Panel getClosestCommonAncestor(Panel panel)
  {
    Panel ancestor = getParent();
    while(ancestor != null && !panel.isAncestor(ancestor))
      ancestor = ancestor.getParent();

    if(ancestor == null)
      throw new LimelightError("No common ancestor found! Do the panels belong to the same tree?");

    return ancestor;
  }

  public Graphics2D getGraphics()
  {
    Box bounds = getAbsoluteBounds();
    return (Graphics2D) getRoot().getGraphics().create(bounds.x, bounds.y, bounds.width, bounds.height);
  }

  public void paintOn(Graphics2D graphics)
  {
  }

  public void doLayout()
  {
  }

  public void mousePressed(MouseEvent e)
  {
    parent.mousePressed(e);
  }

  public void mouseReleased(MouseEvent e)
  {
    parent.mouseReleased(e);
  }

  public void mouseClicked(MouseEvent e)
  {
    parent.mouseClicked(e);
  }

  public void mouseDragged(MouseEvent e)
  {
    parent.mouseDragged(e);
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }

  public void mouseMoved(MouseEvent e)
  {
    parent.mouseMoved(e);
  }

  public void mouseWheelMoved(MouseWheelEvent e)
  {
    parent.mouseWheelMoved(e);
  }

  public void focusLost(FocusEvent e)
  {
    parent.focusLost(e);
  }

  public void focusGained(FocusEvent e)
  {
    parent.focusGained(e);
  }

  public void keyTyped(KeyEvent e)
  {
    parent.keyTyped(e);
  }

  public void keyPressed(KeyEvent e)
  {
    parent.keyPressed(e);
  }

  public void keyReleased(KeyEvent e)
  {
    parent.keyReleased(e);
  }

  public void buttonPressed(ActionEvent e)
  {
    parent.buttonPressed(e);
  }

  public void valueChanged(Object e)
  {
    parent.valueChanged(e);
  }

  public void add(Panel panel)
  {
    add(-1, panel);
  }

  public void add(int index, Panel child)
  {
    if(sterilized)
      throw new SterilePanelException("Propless Panel");
    if(index == -1)
      children.add(child);
    else
      children.add(index, child);
    child.setParent(this);
    setNeededUpdate(Updates.layoutAndPaintUpdate);
  }

  public boolean hasChildren()
  {
    return children.size() > 0;
  }

  public LinkedList<Panel> getChildren()
  {
    return children;
  }

  public void sterilize()
  {
    sterilized = true;
  }

  public boolean isSterilized()
  {
    return sterilized;
  }

  public void repaint()
  {
    getParent().repaint();
  }

  public Panel getOwnerOfPoint(Point point)
  {
    point = new Point(point.x - getX(), point.y - getY());
    if(children.size() > 0)
    {
      for(ListIterator<Panel> iterator = children.listIterator(children.size()); iterator.hasPrevious();)
      {
        Panel panel = iterator.previous();
if(this instanceof PropPanel && "surface".equals(((PropPanel)this).getProp().getName()))
{
  System.err.println("panel = " + panel + " panel.isFloater() = " + panel.isFloater() + " panel.containsRelativePoint(point) = " + panel.containsRelativePoint(point));
}
        if(panel.isFloater() && panel.containsRelativePoint(point))
          return panel.getOwnerOfPoint(point);
      }
      for(Panel panel : children)
      {
        if(!panel.isFloater() && panel.containsRelativePoint(point))
          return panel.getOwnerOfPoint(point);
      }
    }
    return this;
  }

  public boolean isChild(Panel child)
  {
    return children.contains(child);
  }

  public boolean remove(Panel child)
  {
    if(children.remove(child))
    {
      setNeededUpdate(Updates.layoutAndPaintUpdate);
      return true;
    }
    return false;
  }

  public void removeAll()
  {
    if(children.size() > 0)
    {
      children.clear();
      sterilized = false;
      setNeededUpdate(Updates.layoutAndPaintUpdate);
    }
  }

  public Box getBoundingBox()
  {
    if(boundingBox == null)
      boundingBox = new Box(0, 0, getWidth(), getHeight());
    return boundingBox;
  }

  public boolean isFloater()
  {
    return false;
  }

  public boolean canBeBuffered()
  {
    return true;
  }

  public synchronized void setNeededUpdate(Update update)
  {
    if(neededUpdate == null)
    {
      Panel root = getRoot();
      if(root != null)
      {
        ((RootPanel) root).addChangedPanel(this);
        neededUpdate = update;
      }
    }
    else
      neededUpdate = neededUpdate.prioritize(update);
  }

  public synchronized Update getAndClearNeededUpdate()
  {
    Update update = neededUpdate;
    neededUpdate = null;
    return update;
  }


  public synchronized boolean needsUpdating()
  {
    return neededUpdate != null;
  }

  public synchronized void resetNeededUpdate()
  {
    neededUpdate = null;
  }

  public synchronized Update getNeededUpdate()
  {
    return neededUpdate;
  }

  //TODO This is little inefficient.  Reconsider what get's passed to props.
  protected MouseEvent translatedEvent(MouseEvent e)
  {
    e = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), false);
    Point absoluteLocation = getAbsoluteLocation();
    e.translatePoint(absoluteLocation.x * -1, absoluteLocation.y * -1);
    return e;
  }

  public Iterator<Panel> iterator()
  {
    return new PanelIterator(this);
  }
}
