package model;
public class Food
{
    private double carbs;

    private String fdc_id;

    private double fat;

    private String description;

    private _id _id;

    private double calories;

    private double protein;

    public double getCarbs ()
    {
        return carbs;
    }

    public void setCarbs (double carbs)
    {
        this.carbs = carbs;
    }

    public String getFdc_id ()
    {
        return fdc_id;
    }

    public void setFdc_id (String fdc_id)
    {
        this.fdc_id = fdc_id;
    }

    public double getFat ()
    {
        return fat;
    }

    public void setFat (double fat)
    {
        this.fat = fat;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public _id get_id ()
    {
        return _id;
    }

    public void set_id (_id _id)
    {
        this._id = _id;
    }

    public double getCalories ()
    {
        return calories;
    }

    public void setCalories (double calories)
    {
        this.calories = calories;
    }

    public double getProtein ()
    {
        return protein;
    }

    public void setProtein (double protein)
    {
        this.protein = protein;
    }

    @Override
    public String toString()
    {
        return "[description = "+description+ ", calories = "+calories+", fat = "+fat+", carbs = "+carbs+", protein = "+protein+"]";
    }
}