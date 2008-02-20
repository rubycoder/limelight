module Log
  
  def self.extended(block)
  end
  
  def update_counter(steps)
    count.text = steps.to_s
    count.update
  end
  
  def update_location(coords)
    location.text = coords.to_s
    location.update
  end
  
  def count
    @count = self.find("count") if not @count
    return @count
  end
  
  def location
    @location = self.find("location") if not @location
    return @location
  end
  
end