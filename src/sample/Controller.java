package sample;

import OwlFx.OwlViewer;
import javafx.fxml.FXML;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileReader;

public class Controller {

    @FXML
    OwlViewer viewer;

    @FXML
    protected void initialize()
    {
        try
        {
            FileReader f = new FileReader(getClass().getResource("pizza.owl").getFile());

            OntModel m = ModelFactory.createOntologyModel();
            m.read(f, "http://lumii.lv/ontologies/pizza.owl");

            if (this.viewer != null)
            {
                this.viewer.setModel(m,"Pizza");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

