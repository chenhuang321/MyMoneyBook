package com.dreamgallery.mymoneybook.helper;

import android.view.View;
import android.widget.ImageButton;

import com.dreamgallery.mymoneybook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author James
 */
public class ImageHelper {

    private List<Integer> _imageIDs;
    private int _size;
    private View _view;
    private List<ImageButton> _imageButtonList;
    private class ImageSource {
        int[] original_source = {
            R.drawable.tab1_chat,
            R.drawable.tab2_heads,
            R.drawable.tab3_address,
            R.drawable.tab4_setting
        }; // Original images without being clicked
        int[] clicked_source = {
            R.drawable.tab1_chat_clicked,
            R.drawable.tab2_heads_clicked,
            R.drawable.tab3_address_clicked,
            R.drawable.tab4_setting_clicked
        };
        int[] button_source = {
            R.id.btn_tab_bottom_message,
            R.id.btn_tab_bottom_friend,
            R.id.btn_tab_bottom_contact,
            R.id.btn_tab_bottom_setting
        };
    }

    /**
     * This is just a constructor function with size and view parameters
     * @param size The custom size of the integer array
     * @param view The view from the layout file
     */
    public ImageHelper(int size, View view) {
        _imageIDs = new ArrayList<>(size);
        ImageSource imageSource = new ImageSource();
        _size = size;
        _view = view;
        int count = 0;
        _imageButtonList = new ArrayList<>(5);
        for(int imageButtonId : imageSource.button_source) {
            add(count, imageButtonId);
            count++;
        }
    }

    /**
     * It's an add method for ImageButton group management
     * @param index          The index position for inserting the value
     * @param image_location The value waited to be inserted
     */
    private void add(int index, int image_location) {
        if(_imageIDs.size() == _size) {
            System.out.println("Error: Can't add image to this private list!");
        }
        _imageIDs.add(index, image_location);
        _imageButtonList.add(index, (ImageButton)_view.findViewById(image_location));
    }


    /**
     * This function changes the selected image to a new image
     * @param index     Index position of the image list
     */
    public void change(int index) {
        ImageButton tmp;
        ImageSource image_src = new ImageSource();
        reset();
        if(_imageButtonList.get(index) != null) {
            tmp = _imageButtonList.get(index);
            tmp.setImageResource(image_src.clicked_source[index]);
        }
    }

    /**
     * This function reset all image buttons to original colors
     */
    public void reset() {
        int count = 0;
        ImageSource imageSource = new ImageSource();
        if(_imageButtonList != null) {
            for(ImageButton imageButton : _imageButtonList) {
                imageButton.setImageResource(imageSource.original_source[count]);
                count++;
            }
        }
        else {
            System.out.println("This thing is wired but I still have to report you that you" +
                    " have to stop import anything from this null container!");
        }
    }


    /**
     * Find the image button for Main Activity
     * @param index Index position of the button list
     * @return The found image button
     */
    public ImageButton getImageButtonSource(int index) {
        ImageSource imageSource = new ImageSource();
        return _view.findViewById(imageSource.button_source[index]);
    }


}
