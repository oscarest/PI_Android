package com.studium.xxracso40xx.pi_android;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecepcionDatos
{
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    /*@Expose(serialize = false)
    private String alpha2Code;
    Este código de arriba para que no convierta a JSON los elementos de java


    @Expose(desserialize = false )
    private String alpha2Code;
    Este código de arriba para que no convierta a java los elementos de JSON*/
}
