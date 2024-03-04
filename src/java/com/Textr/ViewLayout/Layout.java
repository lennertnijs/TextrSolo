package com.Textr.ViewLayout;

import com.Textr.Validator.Validator;
import com.Textr.View.View;

import java.util.*;

public class Layout  implements ILayout {

    private final List <Layout> sublayouts= new ArrayList<>();
    private final Optional<View> view;
    private Layout parent;
    public Layout( View view){
        this.view = Optional.ofNullable(view);
    }
    public Layout(){
        this.view = Optional.empty();
    }

    public void addsubLayout(Layout layout){
        this.sublayouts.add(layout);
        layout.setParent(this);
    }
    public void removesubLayout(Layout layout){
        sublayouts.remove(layout);
        if(sublayouts.size()==1){
            Layout tomove = sublayouts.getFirst();
            if(getParent()!= null)
                tomove.moveLeafto(this.parent);

        }
    }
    public void setParent(Layout parent){
        this.parent = parent;
    }
    public  Layout getParent(){
        return parent;
    }

    public List<Layout> getsubLayouts(){
        return sublayouts;
    }
    public void moveLeafto(Layout newparent){
        parent.removesubLayout(this);
        newparent.addsubLayout(this);
        parent = newparent;
    }
    public View getView(){
        return view.orElse(null);
    }

    public boolean containsview(int id){
        if(view.isPresent() && getView().getId()==id){
            return true;
        }
        else{
            for (Layout layout : this.getsubLayouts()){
                if(layout.containsview(id)){
                    return true;
                }
            }
            return false;
        }
    }

    public View lookforviewbyId(int id){
        if(view.isPresent() && getView().getId()==id){
            return this.getView();
        }

        for (Layout layout : this.getsubLayouts()){
            View result= layout.lookforviewbyId(id);
            if(result!=null){
                return result;
            }
        }
        return null;

    }

    public View lookforviewbybufferId(int id){
        if(view.isPresent() && getView().getFileBufferId()==id){
            return this.getView();
        }
        for (Layout layout : this.getsubLayouts()){
            View result= layout.lookforviewbybufferId(id);
            if(result!=null){
                return result;
            }
        }
        return null;

    }
    public Layout lookforviewLocation(int id){
        if(view.isPresent() && getView().getId()==id){
            return this;
        }

        for (Layout layout : this.getsubLayouts()){
            Layout result= layout.lookforviewLocation(id);
            if(result!=null){
                return result;
            }
        }
        return null;

    }
    public List<View> getallViews(){
        List<View> result = new ArrayList<>();
        if(view.isPresent()){
            result.add(getView());
            return result;
        }
        for (Layout layout : sublayouts){
            result.addAll(layout.getallViews());
        }
        return result;
    }
    public int getsize(){
        int sum = 0;
        if(view.isPresent()){
            return 1;
        }
        else {
            for (Layout layout : sublayouts){
                sum+= layout.getsize();
            }
        }
        return sum;
    }

}
