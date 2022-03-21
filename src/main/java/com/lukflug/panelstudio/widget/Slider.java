package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.ISliderRenderer;
import hk.eric.ericLib.utils.classes.getters.Getter;

import java.awt.*;

/**
 * Base class for components that are sliders.
 * @author lukflug
 */
public abstract class Slider extends FocusableComponent {
	/**
	 * Whether slider was clicked and is sliding.
	 */
	protected boolean attached=false;
	/**
	 * The renderer to be used.
	 */
	protected final Getter<ISliderRenderer> rendererGetter;
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param rendererGetter renderer getter for the slider
	 */
	public Slider(ILabeled label, Getter<ISliderRenderer> rendererGetter) {
		super(label);
		this.rendererGetter = rendererGetter;
	}

	@Override
	public void render (Context context) {
		super.render(context);
		if (attached) {
			Rectangle rect= rendererGetter.get().getSlideArea(context,getTitle(),getDisplayState());
			double value=(context.getInterface().getMouse().x-rect.x)/(double)(rect.width-1);
			if (value<0) value=0;
			else if (value>1) value=1;
			setValue(value);
		}
		if (!context.getInterface().getButton(IInterface.LBUTTON)) {
			attached=false;
		}
		rendererGetter.get().renderSlider(context,getTitle(),getDisplayState(),hasFocus(context),getValue());
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked(button) && rendererGetter.get().getSlideArea(context,getTitle(),getDisplayState()).contains(context.getInterface().getMouse())) {
			attached=true;
		}
	}
	
	@Override
	public void exit() {
		super.exit();
		attached=false;
	}

	@Override
	protected int getHeight() {
		return rendererGetter.get().getDefaultHeight();
	}

	/**
	 * Abstract method to get the current slider value.
	 * @return the slider value between 0 (empty) and 1 (full)
	 */
	protected abstract double getValue();
	
	/**
	 * Abstract method to update the slider value.
	 * @param value the slider value between 0 (empty) and 1 (full)
	 */
	protected abstract void setValue (double value);
	
	/**
	 * Abstract method to get the displayed slider value.
	 * @return string to be displayed on slider
	 */
	protected abstract String getDisplayState();
}
