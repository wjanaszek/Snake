import org.wjanaszek.controller.Controller;
import org.wjanaszek.model.Model;
import org.wjanaszek.view.View;

public class Main {

	public static void main(String[] args) {
		Model model = new Model();
		View view = new View(model);
		Controller controller = new Controller(model, view);
	}

}



