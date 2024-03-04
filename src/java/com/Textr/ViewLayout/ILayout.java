package com.Textr.ViewLayout;

public interface ILayout {

    void setParent(Layout layoutNode);

    void removeSubLayout(Layout layout);

    void addSubLayout(Layout layout);
}
