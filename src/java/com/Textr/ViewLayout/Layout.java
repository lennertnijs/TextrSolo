package com.Textr.ViewLayout;

import com.Textr.DefaultLineSeparator;
import com.Textr.View.View;
import com.sun.source.doctree.ThrowsTree;

import java.util.*;

public class Layout implements ILayout {

    private final List<Layout> children = new ArrayList<>();
    private final Optional<View> view;
    private Layout parent;


    public Layout(View view){
        this.view = Optional.ofNullable(view);
    }

    public Layout(){
        this.view = Optional.empty();
    }



    public void addSubLayout(Layout layout){
        this.children.add(layout);
        layout.setParent(this);
    }

    public void addSubLayout(Layout layout, int placement){
        this.children.add(placement, layout);
        layout.setParent(this);
    }

    public void removeSubLayout(Layout layout){
        children.remove(layout);
        if(children.size()==1){
//            Layout toMove = children.getFirst();
//            if(getParent()!= null)
//                toMove.moveLeafTo(this.parent);

        }
    }
    public void setParent(Layout parent){
        this.parent = parent;
    }


    public  Layout getParent(){
        return parent;
    }

    public List<Layout> getChildren(){
        return children;
    }


    public void moveLeafTo(Layout newParent){
        parent.removeSubLayout(this);
        newParent.addSubLayout(this);
    }

    public void moveLeafTo(Layout newParent, int placement){
        parent.removeSubLayout(this);
        newParent.addSubLayout(this, placement);
    }


    public View getView(){
        return view.orElse(null);
    }

    public boolean containsView(int id){
        if(view.isPresent() && getView().getId()==id){
            return true;
        }
        else{
            for (Layout layout : this.getChildren()){
                if(layout.containsView(id)){
                    return true;
                }
            }
            return false;
        }
    }

    public View getViewById(int id){
        if(view.isPresent() && getView().getId()==id){
            return this.getView();
        }

        for (Layout layout : this.getChildren()){
            View result= layout.getViewById(id);
            if(result!=null){
                return result;
            }
        }
        return null;

    }

    public View getViewByBufferId(int id){
        if(view.isPresent() && getView().getFileBufferId()==id){
            return this.getView();
        }
        for (Layout layout : this.getChildren()){
            View result= layout.getViewByBufferId(id);
            if(result!=null){
                return result;
            }
        }
        return null;

    }
    public Layout getViewLocation(int id){
        if(view.isPresent() && getView().getId()==id){
            return this;
        }

        for (Layout layout : this.getChildren()){
            Layout result= layout.getViewLocation(id);
            if(result!=null){
                return result;
            }
        }
        return null;

    }
    public List<View> getAllViews(){
        List<View> result = new ArrayList<>();
        if(view.isPresent()){
            result.add(getView());
            return result;
        }
        for (Layout layout : children){
            result.addAll(layout.getAllViews());
        }
        return result;
    }
    public int getSize(){
        int sum = 0;
        if(view.isPresent()){
            return 1;
        }
        else {
            for (Layout layout : children){
                sum+= layout.getSize();
            }
        }
        return sum;
    }

    public Layout getNext(){
        List<Layout> siblings = this.parent.getChildren();
        int position = siblings.indexOf(this);
        if(position+1==siblings.size()){
            if(parent!=null)
                return parent.getNext();
            else
                return null;
        }
        else
            return siblings.get(position+1);
    }
    public Layout getFirstLeaf(){
        if(view.isPresent())
            return this;
        else
            return this.children.getFirst().getFirstLeaf();
    }
    public void rotatewithnext(boolean clockwise){
        Layout nextnode = getNext();
        if(nextnode!= null){
            Layout nextleaf = nextnode.getFirstLeaf();
            if(parent.equals(nextleaf.getParent())){
                Layout newsubLayout = new Layout();
                if(clockwise){
                    moveLeafTo(newsubLayout);
                    nextleaf.moveLeafTo(newsubLayout);
                }
                else {
                    nextleaf.moveLeafTo(newsubLayout);
                    moveLeafTo(newsubLayout);
                }
            }
            else{
                if (clockwise){
                    nextleaf.moveLeafTo(parent);
                }
                else {
                    int placement = parent.getChildren().indexOf(this);
                    nextleaf.moveLeafTo(parent, placement);
                }
            }

        }
        else{
            String ding = "DING";
            //PING-sound
        }
    }

}
