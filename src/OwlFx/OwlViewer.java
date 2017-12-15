package OwlFx;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.apache.jena.ontology.ComplementClass;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.util.List;

public class OwlViewer extends AnchorPane
{
    private final TreeView<String> treeView;
    private OntClass rootClass;

    public OwlViewer()
    {
        /*AnchorPane a = new AnchorPane();
        a.setPrefHeight(-1);
        a.setPrefWidth(-1);
        this.getChildren().add(a);*/

        this.treeView = new TreeView<String>();
        MaximizeInAnchorPane(this.treeView);

        this.getChildren().add(this.treeView);
    }

    private OntModel usedModel;

    public void setModel(OntModel m, String pizzaBase)
    {
        this.usedModel = m;
        for (OntClass cur: this.usedModel.listClasses().toList())
        {
            if (cur.getLocalName()!= null && cur.getLocalName().equals(pizzaBase))
            {
                this.rootClass = cur;
                break;
            }
        }

        this.Update();
    }

    private void Update()
    {
        if( Platform.isFxApplicationThread())
        {
            this.UpdateInternal();
        }
        else
        {
            Platform.runLater(this::UpdateInternal);
        }
    }


    private void UpdateInternal()
    {
        if (this.rootClass == null)
        {   return; }

        this.treeView.setRoot( new TreeItem<>(this.rootClass.getLocalName()));
        AddChildren(this.treeView.getRoot(), this.rootClass.listSubClasses().toList());
    }

    private static void AddChildren(TreeItem<String> root, List<OntClass> ontClassExtendedIterator)
    {
        for (OntClass c :                ontClassExtendedIterator)
        {
            String localName = c.getLocalName();

            if (localName == null)
            {   continue;   }
            TreeItem<String> n = new TreeItem<>(localName);
            root.getChildren().add(n);
            AddChildren(n, c.listSubClasses().toList());
        }
    }

    public static void MaximizeInAnchorPane(Node toMaximize)
    {
        AnchorPane.setTopAnchor(toMaximize, 0.0);
        AnchorPane.setRightAnchor(toMaximize, 0.0);
        AnchorPane.setLeftAnchor(toMaximize, 0.0);
        AnchorPane.setBottomAnchor(toMaximize, 0.0);
    }
}
