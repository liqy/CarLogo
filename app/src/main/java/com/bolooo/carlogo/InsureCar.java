package com.bolooo.carlogo;

/**
 * Created by liqin on 2015/12/29.
 */
public class InsureCar {

    /**
     * 品牌
     */
    public String brand_id;
    public String brand_name;
    public String initial;
    public String brand_logo;

    @Override
    public String toString() {
        return "InsureCar{" +
                "brand_id='" + brand_id + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", initial='" + initial + '\'' +
                ", brand_logo='" + brand_logo + '\'' +
                '}';
    }
}
