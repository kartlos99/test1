package diakonidze.kartlos.devises;

import java.io.Serializable;

/**
 * Created by k.diakonidze on 6/22/2015.
 */
public class Devises implements Serializable {

    private String deviceCategory, brand, model, info, imig, coment;
    private int sponsor, count = 0;
    private long id;

    public Devises(String device, String brand, String model, String info, String imig, int count) {
        this.deviceCategory = device;
        this.brand = brand;
        this.model = model;
        this.info = info;
        this.imig = imig;
        this.count = count;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSponsor() {
        return sponsor;
    }

    public void setSponsor(int sponsor) {
        this.sponsor = sponsor;
    }

    public String getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(String deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImig() {
        return imig;
    }

    public void setImig(String imig) {
        this.imig = imig;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
