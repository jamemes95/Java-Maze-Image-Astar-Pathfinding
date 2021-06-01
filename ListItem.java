public class ListItem {
    public int X;
    public int Y;
    public int F;
    public int parentX;
    public int parentY;
    public ListItem parentInfo;

    public ListItem(int X, int Y, int F) {
        this.X = X;
        this.Y = Y;
        this.F = F;
    }

    public int getX(){return X;}
    public int getY(){return Y;}
    public int getF(){return F;}
    public void setParent(int parentX,int parentY, ListItem parentInfo){this.parentX = parentX;this.parentY = parentY; this.parentInfo = parentInfo;}
    public int getParentX(){return parentX;}
    public int getParentY(){return parentY;}
    public ListItem getParentInfo(){return parentInfo;}

}