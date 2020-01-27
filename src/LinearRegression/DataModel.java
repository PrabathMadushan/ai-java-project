
package LinearRegression;

import javafx.beans.property.SimpleFloatProperty;



public class DataModel {
    private SimpleFloatProperty x;
    private SimpleFloatProperty y;
    
    public DataModel(float x,float y){
        this.x=new SimpleFloatProperty(x);
        this.y=new SimpleFloatProperty(y);
    }

   
    public float getX(){
        return x.get();
    }
    
    public float getY(){
        return y.get();
    }
    
    public void setX(int x){
        this.x=new SimpleFloatProperty(x);
    }
    
    public void setY(int y){
        this.y=new SimpleFloatProperty(y);
    }
    
}
