package org.example.mementoexample;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * <b>short description</b>
 *
 * <p>detailed description</p>
 *
 * @since <version tag>
 */
@Route("")
public class MainLayout extends VerticalLayout {

  public MainLayout() {
    add(new CaesarSimulator());
  }
}
