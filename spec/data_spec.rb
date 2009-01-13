require File.expand_path(File.dirname(__FILE__) + "/spec_helper")
require 'limelight/data'

describe Limelight::Data do

  before(:each) do
    Limelight::Data.reset
    Limelight::Context.instance.stub!(:os).and_return("osx")
  end

  it "should have a root on osx" do
    Limelight::Context.instance.stub!(:os).and_return("osx")
    expected = File.expand_path(File.join("~/Library/Application Support/Limelight"))

    Limelight::Data.root.should == expected
  end

  it "should have a root on windows" do
    Limelight::Context.instance.stub!(:os).and_return("windows")
    expected = File.expand_path(File.join("~/Application Data/Limelight"))

    Limelight::Data.root.should == expected
  end

  it "should have a Downloads dir" do
    expected = File.join(Limelight::Data.root, "Downloads")

    Limelight::Data.downloads_dir.should == expected
  end

  it "should have a Productions dir" do
    expected = File.join(Limelight::Data.root, "Productions")

    Limelight::Data.productions_dir.should == expected
  end

  it "should establish all the dirs" do
    Limelight::Data.stub!(:root).and_return(TestDir.path("Limelight"))

    Limelight::Data.establish_data_dirs

    File.exists?(TestDir.path("Limelight")).should == true
    File.exists?(TestDir.path("Limelight/Downloads")).should == true
    File.exists?(TestDir.path("Limelight/Productions")).should == true
  end

end