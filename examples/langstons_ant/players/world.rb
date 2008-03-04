load File.expand_path(File.dirname(__FILE__) + "/ant.rb")
  
module World
  
  def self.extended(prop)
create_time = 0
add_time = 0
    
    prop.cell_index = {}
    100.times do |y|
      100.times do |x|
        id = "#{x},#{y}"
start = Time.now
        cell = Limelight::Prop.new(:class_name => "cell", :id => id)
create_time += Time.now - start
start = Time.now
        prop.add(cell)
add_time += Time.now - start        
        prop.cell_index[id] = cell
      end
    end
puts "create_time: #{create_time}"    
puts "add_time: #{add_time}"
# create_time: 3.2059999999998325
# add_time: 0.5010000000000002

  end
  
  attr_accessor :cell_index
  
end