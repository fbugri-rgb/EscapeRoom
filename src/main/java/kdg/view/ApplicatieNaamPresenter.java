package kdg.view;

import kdg.model.Item;

/**
 * @author Borja
 * @version 1.0 04/02/2026 20:34
 *
 */
public class ApplicatieNaamPresenter {

    private Item model;
    private ApplicatieNaamView view;


    public ApplicatieNaamPresenter(Item model, ApplicatieNaamView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers() {
        // Koppelt event handlers (anon. inner klassen)
        // aan de controls uit de view.
        // Event handlers: roepen methodes aan uit het
        // model en zorgen voor een update van de view.
    }
    private void updateView() {
        // Vult de view met data uit model
    }
    public void addWindowEventHandlers () {
        // Window event handlers (anon. inner klassen)
        // Koppeling via view.getScene().getWindow()
    }
}
