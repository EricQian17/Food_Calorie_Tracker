package model;
public class _id
{
    private String $oid;

    public String get$oid ()
    {
        return $oid;
    }

    public void set$oid (String $oid)
    {
        this.$oid = $oid;
    }

    @Override
    public String toString()
    {
        return $oid;
    }
}