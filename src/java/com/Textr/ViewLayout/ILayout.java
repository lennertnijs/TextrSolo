package com.Textr.ViewLayout;

public interface ILayout {

    void setParent(Layout layoutNode);

    void removesubLayout(Layout layout);

    void addsubLayout(Layout layout);
}
