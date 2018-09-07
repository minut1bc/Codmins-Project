package com.ibl.apps.myphotokeyboard.model;

/**
 * Created by iblinfotech on 02/05/17.
 */

public class Textual {
    private String id;

    private String thumb_image;

    private String title;

    private String image;
    private String isPaid;

    public String isPaid() {
        return isPaid;
    }

    public void setPaid(String paid) {
        isPaid = paid;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getThumb_image ()
    {
        return thumb_image;
    }

    public void setThumb_image (String thumb_image)
    {
        this.thumb_image = thumb_image;
    }


    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    private String imageBase64;

    public String getThumbImageBase64() {
        return thumbImageBase64;
    }

    public void setThumbImageBase64(String thumbImageBase64) {
        this.thumbImageBase64 = thumbImageBase64;
    }

    private String thumbImageBase64;

}
