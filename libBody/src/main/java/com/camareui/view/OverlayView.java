package com.camareui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple View providing a render callback to other classes.
 */
public class OverlayView extends View {
  private final List<DrawCallback> callbacks = new LinkedList<>();
  private DrawCallback callback;
  public OverlayView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * Interface defining the callback for client classes.
   */
  public interface DrawCallback {
    void drawCallback(final Canvas canvas);
  }

  public void addCallback(final DrawCallback callback) {
//    callbacks.add(callback);
    this.callback = callback;
  }

  @Override
  public synchronized void draw(final Canvas canvas) {
    super.draw(canvas);
//    for (final DrawCallback callback : callbacks) {
    if(callback != null)
      callback.drawCallback(canvas);
//    }
  }
}
