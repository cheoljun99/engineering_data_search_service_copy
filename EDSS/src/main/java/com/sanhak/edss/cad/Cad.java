package com.sanhak.edss.cad;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.ByteArrayOutputStream;

@Data
@Document(collection = "cad")
public class Cad {
    @Id
    private String id;
    private String mainCategory;
    private String subCategory;
    private String title;
    private String index;

    //private ByteArrayOutputStream JpegImage;




    public Cad(String mainCategory, String subCategory, String title, String index ) {
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.title = title;
        this.index = index;
        //this.JpegImage = Image;
    }

    @Override
    public String toString() {
        return "Cad File[" +
                "\n\tid=" + id +
                "\n\tmainCategory=" + mainCategory +
                "\n\tsubCategory=" + subCategory +
                "\n\ttitle=" + title +
                "\n\tindex=" + index +
                "]";
    }
}
