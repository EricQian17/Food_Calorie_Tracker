package model;

public class UserData
{
	private String date;

    private int quantity;

    private Double carbs;

    private Double protein;

    private String name;

    private Double fat;

    private _id _id;

    private Double calories;

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public int getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (int quantity)
    {
        this.quantity = quantity;
    }

    public Double getCarbs ()
    {
        return carbs;
    }

    public void setCarbs (Double carbs)
    {
        this.carbs = carbs;
    }

    public Double getProtein ()
    {
        return protein;
    }

    public void setProtein (Double protein)
    {
        this.protein = protein;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Double getFat ()
    {
        return fat;
    }

    public void setFat (Double fat)
    {
        this.fat = fat;
    }

    public _id get_id ()
    {
        return _id;
    }

    public void set_id (_id _id)
    {
        this._id = _id;
    }

    public Double getCalories ()
    {
        return calories;
    }

    public void setCalories (Double calories)
    {
        this.calories = calories;
    }

    @Override
    public String toString()
    {
        return "[date = "+ date + ", name = "+name+", calories = "+calories+", fat = "+fat+", carbs = "+carbs+", protein = "+protein+", quantity = "+quantity+"]";
    }
}