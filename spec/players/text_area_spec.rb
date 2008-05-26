#- Copyright 2008 8th Light, Inc.
#- Limelight and all included source files are distributed under terms of the GNU LGPL.

require File.expand_path(File.dirname(__FILE__) + "/../spec_helper")
require 'limelight/prop'
require 'limelight/players/text_area'

describe Limelight::Players::TextArea do

  before(:each) do
    @prop = Limelight::Prop.new
    @prop.add_controller(Limelight::Players::TextArea)
  end
  
  it "should get rid of the all painters and add a TextAreaPainter" do
    @prop.panel.painters.size.should == 1
    @prop.panel.painters.last.class.should == Java::limelight.ui.painting.TextAreaPainter
  end
  
  it "should clear event listeners on the panel" do
    @prop.panel.mouse_listeners.length.should == 0
    @prop.panel.key_listeners.length.should == 0
  end
  
  it "should have a JTextArea" do
    @prop.panel.components[0].class.should == javax.swing.JTextArea
  end
  
  it "should use the TextArea for the text accessor" do
    @prop.text = "blah"
    @prop.panel.components[0].text.should == "blah"
    
    @prop.panel.components[0].text = "harumph"
    @prop.text.should == "harumph"
  end

end