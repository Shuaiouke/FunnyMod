package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.IInterface;

import java.awt.*;

/**
 * Interface representing a GUI theme (i.e. skin).
 * @author lukflug
 */
public interface ITheme {
	/**
	 * The constant to indicate no symbol for small button renderer.
	 */
    int NONE=0;
	/**
	 * The constant for a small button with an "X".
	 */
    int CLOSE=1;
	/**
	 * The constant for a small button with a "minimize" symbol.
	 */
    int MINIMIZE=2;
	/**
	 * The constant for a small button with a "+".
	 */
    int ADD=3;
	/**
	 * The constant for a small button with a left arrow.
	 */
    int LEFT=4;
	/**
	 * The constant for a small button with a right arrow.
	 */
    int RIGHT=5;
	/**
	 * The constant for a small button with an up arrow.
	 */
    int UP=6;
	/**
	 * The constant for a small button with a down arrow.
	 */
    int DOWN=7;
	
	/**
	 * Function to be called in order to load images.
	 * @param inter the interface to use
	 */
    void loadAssets(IInterface inter);
	
	/**
	 * Returns the renderer for tooltip descriptions.
	 * @return the description renderer
	 */
    IDescriptionRenderer getDescriptionRenderer();
	
	/**
	 * Returns the renderer for the panel background.
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param horizontal whether the container is horizontal
	 * @return the container renderer
	 */
    IContainerRenderer getContainerRenderer(int logicalLevel, int graphicalLevel, boolean horizontal);
	
	/**
	 * Returns the renderer for the panel outline.
	 * @param <T> the state type
	 * @param type the state class
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @return the panel renderer
	 */
    <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int logicalLevel, int graphicalLevel);
	
	/**
	 * Returns the renderer for scroll bars.
	 * @param <T> the state type
	 * @param type the state class
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @return the scroll bar renderer
	 */
    <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int logicalLevel, int graphicalLevel);
	
	/**
	 * Returns the renderer for the scroll corner.
	 * @param <T> the state type
	 * @param type the state class
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container true, if empty space is due to a container, false, if scroll corner
	 * @return the empty space renderer
	 */
    <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Returns the renderer for buttons.
	 * @param <T> the state type
	 * @param type the state class
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the title renderer
	 */
    <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Render a small button that may have a symbol.
	 * @param symbol the symbol to be used
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the button renderer
	 * @see #NONE
	 * @see #CLOSE
	 * @see #MINIMIZE
	 * @see #ADD
	 * @see #LEFT
	 * @see #RIGHT
	 * @see #UP
	 * @see #DOWN
	 */
    IButtonRenderer<Void> getSmallButtonRenderer(int symbol, int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Returns the renderer for keybinds.
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the keybind renderer
	 */
    IButtonRenderer<String> getKeybindRenderer(int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Returns the renderer for sliders.
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the slider renderer
	 */
    ISliderRenderer getSliderRenderer(int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Returns the renderer for radio button lists.
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container whether this is part of a container defined by a layout
	 * @return the radio renderer
	 */
    IRadioRenderer getRadioRenderer(int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Returns the renderer for resize borders.
	 * @return the resize renderer
	 */
    IResizeBorderRenderer getResizeRenderer();
	
	/**
	 * Returns the renderer for text fields.
	 * @param embed whether this text field is embedded in another component
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the text renderer
	 */
    ITextFieldRenderer getTextRenderer(boolean embed, int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Returns the renderer for toggle switches.
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the switch renderer
	 */
    ISwitchRenderer<Boolean> getToggleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Returns the renderer for cycle switches.
	 * @param logicalLevel the logical nesting level
	 * @param graphicalLevel the panel nesting level
	 * @param container whether this is the title of a panel
	 * @return the switch renderer
	 */
    ISwitchRenderer<String> getCycleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container);
	
	/**
	 * Returns the renderer for color pickers.
	 * @return the color picker renderer
	 */
    IColorPickerRenderer getColorPickerRenderer();
	
	/**
	 * Get the common height of a component.
	 * @return the base height
	 */
    int getBaseHeight();
	
	/**
	 * Returns the main color of a title bar.
	 * @param focus the focus state for the component
	 * @param active whether the component is active or inactive
	 * @return the main color
	 */
    Color getMainColor(boolean focus, boolean active);
	
	/**
	 * Returns the standard background color.
	 * @param focus the focus state for the component
	 * @return the background color
	 */
    Color getBackgroundColor(boolean focus);
	
	/**
	 * Returns the font color.
	 * @param focus the focus state for the component
	 * @return the font color
	 */
    Color getFontColor(boolean focus);
	
	/**
	 * Override the main color.
	 * @param color the color to override
	 */
    void overrideMainColor(Color color);
	
	/**
	 * Restore the main color.
	 */
    void restoreMainColor();
	
	/**
	 * Override the alpha of one color with the alpha of another
	 * @param main the main color
	 * @param opacity the color determining the alpha value
	 * @return the main color with the alpha from the other color
	 */
	static Color combineColors(Color main, Color opacity) {
		return new Color(main.getRed(),main.getGreen(),main.getBlue(),opacity.getAlpha());
	}
	
	/**
	 * Utility function to draw rectangle outline without rounded borders.
	 * @param inter the current interface
	 * @param rect the rectangle
	 * @param color the color
	 */
	static void drawRect(IInterface inter, Rectangle rect, Color color) {
		inter.fillRect(new Rectangle(rect.x,rect.y,1,rect.height),color,color,color,color);
		inter.fillRect(new Rectangle(rect.x+1,rect.y,rect.width-2,1),color,color,color,color);
		inter.fillRect(new Rectangle(rect.x+rect.width-1,rect.y,1,rect.height),color,color,color,color);
		inter.fillRect(new Rectangle(rect.x+1,rect.y+rect.height-1,rect.width-2,1),color,color,color,color);
	}
}
