package com.googlecode.rich2012cafe.controller;

import android.app.Activity;
import android.content.Context;

/**
 * User: cs16g08
 * Date: 27/02/2012
 * Time: 17:01
 */
public abstract class Controller<T> implements ControllerInterface {
    
    private T _view;
    
    protected Controller(T view) {
        this._view = view;
    }

    public T getView() {
        return _view;
    }

    public Activity getActivity() {
        return (Activity)_view;
    }

    public Context getContext() {
        return getActivity();
    }
    
}
