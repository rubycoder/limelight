__ :name => "sandbox"
__install "header.rb"
arena :vertical_alignment => :center do
  control_panel do
    setting do
      label :text => "Background Image Fill Strategy:"
      input :players => "combo_box", :choices => %w{repeat repeat_x repeat_y static scale scale_x scale_y}, :on_value_changed => "scene.find('area').style.background_image_fill_strategy = text"
    end
    setting do
      label :text => "Background Image X"
      input :players => "combo_box", :choices => %w{0 5 25 125 625 left center right 10% 20% 50% 80% 90%}, :on_value_changed => "scene.find('area').style.background_image_x = text"
    end
    setting do
      label :text => "Background Image Y"
      input :players => "combo_box", :choices => %w{0 5 25 125 625 top center bottom 10% 20% 50% 80% 90%}, :on_value_changed => "scene.find('area').style.background_image_y = text"
    end
  end
  image_area :id => "area", :text => "Area with Background Image"
end