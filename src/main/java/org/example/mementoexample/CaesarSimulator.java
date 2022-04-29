package org.example.mementoexample;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.time.Instant;
import java.util.ArrayList;

/**
 * <b>short description</b>
 *
 * <p>detailed description</p>
 *
 * @since <version tag>
 */
public class CaesarSimulator extends VerticalLayout {

  private final ArrayList<TimedScrollMemento> history = new ArrayList<>();
  private final Scroll scroll;
  private int currentVersion = 0;
  private VerticalLayout historyView;
  private TextField messageText;
  private Button newCipherButton;
  private TextField encryptedMessage;

  public CaesarSimulator() {
    this.scroll = new Scroll();
    buildLayout();
    handleUserInput();
  }

  private void undo() {
    if (currentVersion > 0 && currentVersion < history.size()) {
      currentVersion--;
      ScrollMemento lastSnapshot = history.get(currentVersion).scrollMemento;
      scroll.restore(lastSnapshot);
      refresh();
    }
  }

  private void redo() {
    if (currentVersion >= 0 && currentVersion < history.size() - 1) {
      currentVersion++;
      ScrollMemento lastSnapshot = history.get(currentVersion).scrollMemento;
      scroll.restore(lastSnapshot);
      refresh();
    }
  }

  private void handleUserInput() {
    messageText.setValueChangeMode(ValueChangeMode.ON_CHANGE);
    messageText.addValueChangeListener(this::setMessage);
    newCipherButton.addClickListener(it -> changeShift());
  }

  private void setMessage(ComponentValueChangeEvent<?, String> valueChangeEvent) {
    scroll.setMessage(valueChangeEvent.getValue());
    refresh();
    if (valueChangeEvent.isFromClient()) {
      expandScrollHistory();
    }
  }

  private void changeShift() {
    scroll.changeShift();
    refresh();
    expandScrollHistory();
  }

  private void expandScrollHistory() {
    ScrollMemento scrollSnapshot = scroll.createSnapshot();
    history.add(new TimedScrollMemento(Instant.now(), scrollSnapshot));
    currentVersion = history.size() - 1;
    updateHistoryView();
  }

  private void updateHistoryView() {
    historyView.removeAll();
    history.stream().map(this::createSnapshotButton).forEachOrdered(historyView::add);
  }

  private void refresh() {
    messageText.setValue(scroll.getMessage());
    encryptedMessage.setValue(scroll.getEncryptedMessage());
  }

  private Button createSnapshotButton(TimedScrollMemento timedScrollMemento) {
    Button button = new Button(timedScrollMemento.instant.toString());
    button.addClickListener(it -> {
      scroll.restore(timedScrollMemento.scrollMemento);
      refresh();
    });
    return button;
  }

  void buildLayout() {
    messageText = new TextField("Message");
    newCipherButton = new Button("New Cipher");
    encryptedMessage = new TextField();
    encryptedMessage.setEnabled(false);
    HorizontalLayout horizontalLayout = new HorizontalLayout(messageText, newCipherButton);
    horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    historyView = new VerticalLayout();
    add(horizontalLayout, encryptedMessage, historyView);
  }

  public class TimedScrollMemento {

    public final Instant instant;
    public final ScrollMemento scrollMemento;

    private TimedScrollMemento(Instant instant, ScrollMemento scrollMemento) {
      this.instant = instant;
      this.scrollMemento = scrollMemento;
    }
  }
}
