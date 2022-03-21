package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.DraggableComponent;
import com.lukflug.panelstudio.component.IComponentProxy;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.widget.ClosableComponent;
import com.lukflug.panelstudio.widget.ToggleButton;
import hk.eric.ericLib.utils.classes.getters.Getter;

import java.awt.*;

/**
 * Panel containing an HUD component.
 * @author lukflug
 * @param <T> the component type
 */
public class HUDPanel<T extends IFixedComponent> extends DraggableComponent<HUDPanel<T>.HUDPanelComponent> {
	/**
	 * The component to be wrapped.
	 */
	protected final T component;
	/**
	 * The panel containing the HUD component.
	 */
	protected final HUDPanelComponent panel;
	/**
	 * Whether to display the panel outline.
	 */
	protected final IBoolean renderState;
	
	/**
	 * Constructor.
	 * @param component the component to be wrapped
	 * @param state the boolean state to be passed to the theme
	 * @param animation the animation for opening and closing
	 * @param theme the theme to be used
	 * @param renderState whether to render the panel title and outline
	 * @param border the component border
	 */
	public HUDPanel (T component, IToggleable state, Animation animation, ITheme theme, IBoolean renderState, int border) {
		this.component=component;
		panel=new HUDPanelComponent(state,animation,theme,renderState,border);
		this.renderState=renderState;
	}
	
	@Override
	public HUDPanelComponent getComponent() {
		return panel;
	}
	
	@Override
	public void handleButton (Context context, int button) {
		if (renderState.isOn()) super.handleButton(context,button);
		else super.getHeight(context);
	}
	
	@Override
	public void handleScroll (Context context, int diff) {
		if (renderState.isOn()) super.handleScroll(context,diff);
		else super.getHeight(context);
	}
	
	
	/**
	 * Panel wrapper that conditionally renders the panel itself, but not its content.
	 * @author lukflug
	 */
	protected class HUDPanelComponent implements IFixedComponent,IComponentProxy<ComponentProxy<ClosableComponent<ToggleButton,ComponentProxy<T>>>> {
		/**
		 * The panel to be wrapped.
		 */
		protected final ComponentProxy<ClosableComponent<ToggleButton,ComponentProxy<T>>> closable;
		/**
		 * The renderer for the panel title bar.
		 */
		protected final IButtonRenderer<Boolean> titleRenderer;
		/**
		 * The renderer for the panel outline and background.
		 */
		protected final IPanelRenderer<Boolean> panelRenderer;
		/**
		 * The border size.
		 */
		protected final int border;
		
		/**
		 * Constructor.
		 * @param state the boolean state to be passed to the theme
		 * @param animation the animation for opening and closing
		 * @param theme the theme to be used
		 * @param renderState whether to render the panel title and outline
		 * @param border the component border
		 */
		public HUDPanelComponent (IToggleable state, Animation animation, ITheme theme, IBoolean renderState, int border) {
			this.border=border;
			panelRenderer=theme.getPanelRenderer(Boolean.class,0,0);
			titleRenderer=theme.getButtonRenderer(Boolean.class,0,0,true);
			closable=getWrappedDragComponent(new ClosableComponent<>(new ToggleButton(new Labeled(component.getTitle(), null, component::isVisible), new IToggleable() {
				@Override
				public boolean isOn() {
					return state.isOn();
				}

				@Override
				public void toggle() {
				}
			}, Getter.fixed(
					new IButtonRendererProxy<>() {
						@Override
						public void renderButton(Context context, String title, boolean focus, Boolean state) {
							if (renderState.isOn()) IButtonRendererProxy.super.renderButton(context, title, focus, state);
						}

						@Override
						public IButtonRenderer<Boolean> getRenderer() {
							return titleRenderer;
						}
					})
			), new ComponentProxy<>(component) {
				@Override
				public int getHeight(int height) {
					return height + 2 * border;
				}

				@Override
				public Context getContext(Context context) {
					return new Context(context, context.getSize().width - 2 * border, new Point(border, border), context.hasFocus(), context.onTop());
				}
			}, state::isOn, new AnimatedToggleable(state, animation), new IPanelRendererProxy<>() {
				@Override
				public void renderBackground(Context context, boolean focus) {
					if (renderState.isOn()) IPanelRendererProxy.super.renderBackground(context, focus);
				}

				@Override
				public void renderPanelOverlay(Context context, boolean focus, Boolean state, boolean open) {
					if (renderState.isOn()) IPanelRendererProxy.super.renderPanelOverlay(context, focus, state, open);
				}

				@Override
				public void renderTitleOverlay(Context context, boolean focus, Boolean state, boolean open) {
					if (renderState.isOn()) IPanelRendererProxy.super.renderTitleOverlay(context, focus, state, open);
				}

				@Override
				public IPanelRenderer<Boolean> getRenderer() {
					return panelRenderer;
				}
			}, false));
		}

		@Override
		public ComponentProxy<ClosableComponent<ToggleButton,ComponentProxy<T>>> getComponent() {
			return closable;
		}

		@Override
		public Point getPosition (IInterface inter) {
			Point pos=component.getPosition(inter);
			pos.translate(-panelRenderer.getLeft()-border,-panelRenderer.getTop()-titleRenderer.getDefaultHeight()-panelRenderer.getBorder()-border);
			return pos;
		}

		@Override
		public void setPosition (IInterface inter, Point position) {
			position.translate(panelRenderer.getLeft()+border,panelRenderer.getTop()+titleRenderer.getDefaultHeight()+panelRenderer.getBorder()+border);
			component.setPosition(inter,position);
		}

		@Override
		public int getWidth (IInterface inter) {
			return component.getWidth(inter)+panelRenderer.getLeft()+panelRenderer.getRight()+2*border;
		}

		@Override
		public boolean savesState() {
			return component.savesState();
		}

		@Override
		public void saveConfig (IInterface inter, IPanelConfig config) {
			component.saveConfig(inter,config);
		}

		@Override
		public void loadConfig (IInterface inter, IPanelConfig config) {
			component.loadConfig(inter,config);
		}

		@Override
		public String getConfigName() {
			return component.getConfigName();
		}
	}
}
